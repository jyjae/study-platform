package com.example.studyplatform.service.chat;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.alarm.Alarm;
import com.example.studyplatform.domain.alarm.AlarmRepository;
import com.example.studyplatform.domain.chat.ChatMessage;
import com.example.studyplatform.domain.chat.ChatMessageRepository;
import com.example.studyplatform.domain.chatRoom.ChatRoom;
import com.example.studyplatform.domain.chatRoom.ChatRoomRepository;
import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.domain.user.UserRepository;
import com.example.studyplatform.dto.alarm.AlarmRequest;
import com.example.studyplatform.dto.chat.ChatMessageRequest;
import com.example.studyplatform.exception.ChatRoomNotFoundException;
import com.example.studyplatform.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final RedisRepository redisRepository;
    private final RedisTemplate redisTemplate;
    private final ChannelTopic channelTopic;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;

    // 채팅방 입장
    public void enter(Long userId, Long roomId) {

        // 그룹채팅은 해시코드가 존재하지 않고 일대일 채팅은 해시코드가 존재한다.
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(ChatRoomNotFoundException::new);

        // 채팅방에 들어온 정보를 Redis 저장
        redisRepository.userEnterRoomInfo(userId, roomId);

        // 그룹채팅은 해시코드가 존재하지 않고 일대일 채팅은 해시코드가 존재한다.
        if (chatRoom.getRoomHashCode() != 0) {
            redisRepository.initChatRoomMessageInfo(chatRoom.getId()+"", userId);
        }
    }

    //채팅
    @Transactional
    public void sendMessage(ChatMessageRequest chatMessageRequest, User user) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatMessageRequest.getRoomId()).orElseThrow(ChatRoomNotFoundException::new);

        //채팅 생성 및 저장
        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .user(user)
                .message(chatMessageRequest.getMessage())
                .build();

        chatMessageRepository.save(chatMessage);
        String topic = channelTopic.getTopic();

        // ChatMessageRequest에 유저정보, 현재시간 저장
        chatMessageRequest.setNickName(user.getNickname());
        chatMessageRequest.setUserId(user.getId());

        if (chatMessageRequest.getType() == ChatMessageRequest.MessageType.GROUP_TALK) {
            // 그륩 채팅일 경우
            redisTemplate.convertAndSend(topic, chatMessageRequest);
            redisTemplate.opsForHash();
        } else {
            // 일대일 채팅 이면서 안읽은 메세지 업데이트
            redisTemplate.convertAndSend(topic, chatMessageRequest);
            updateUnReadMessageCount(chatMessageRequest);
        }
    }

    //안읽은 메세지 업데이트
    private void updateUnReadMessageCount(ChatMessageRequest chatMessageRequest) {
        Long otherUserId = chatMessageRequest.getOtherUserIds().stream().collect(Collectors.toList()).get(0);
        String roomId = String.valueOf(chatMessageRequest.getRoomId());

        if (!redisRepository.existChatRoomUserInfo(otherUserId) || !redisRepository.getUserEnterRoomId(otherUserId).equals(chatMessageRequest.getRoomId())) {

            redisRepository.addChatRoomMessageCount(roomId, otherUserId);
            int unReadMessageCount = redisRepository.getChatRoomMessageCount(roomId+"", otherUserId);

            String topic = channelTopic.getTopic();

            ChatMessageRequest messageRequest = new ChatMessageRequest(chatMessageRequest, unReadMessageCount);

            redisTemplate.convertAndSend(topic, messageRequest);
        }
    }

    // 1:1 채팅, 그룹 채팅 알람 전송
    public void sendChatAlarm(ChatMessageRequest chatMessageRequest) {
        Set<Long> otherUserIds = chatMessageRequest.getOtherUserIds();
        User user = userRepository.findByIdAndStatus(chatMessageRequest.getUserId(), Status.ACTIVE).orElseThrow(UserNotFoundException::new);
        otherUserIds.forEach(otherUserId -> messageIfExistsOtherUser(chatMessageRequest, user, otherUserId));
    }

    private void messageIfExistsOtherUser(ChatMessageRequest req, User user, Long otherUserId) {
        // 채팅방에 받는 사람이 존재하지 않는다면
        if (!redisRepository.existChatRoomUserInfo(otherUserId) || !redisRepository.getUserEnterRoomId(otherUserId).equals(req.getRoomId())) {
            User otherUser = userRepository.findByIdAndStatus(otherUserId, Status.ACTIVE).orElseThrow(UserNotFoundException::new);
            String topic = channelTopic.getTopic();

            // 그룹, 1:1채팅에 따라 제목 변경
            System.out.println(user.getNickname());
            String title = (req.getType() == ChatMessageRequest.MessageType.GROUP_TALK
                    ? req.getRoomTitle() + "에서" : "") + user.getNickname() + "님이 메시지를 보냈습니다.";

            Alarm alarm = alarmRepository.save(Alarm.builder()
                    .title(title)
                    .url("chatURL")
                    .user(otherUser).build());

            redisTemplate.convertAndSend(topic, AlarmRequest.toDto(alarm, otherUserId));
        }
    }
}
