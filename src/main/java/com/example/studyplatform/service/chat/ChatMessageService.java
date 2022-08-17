package com.example.studyplatform.service.chat;

import com.example.studyplatform.domain.chat.ChatMessage;
import com.example.studyplatform.domain.chat.ChatMessageRepository;
import com.example.studyplatform.domain.chatRoom.ChatRoom;
import com.example.studyplatform.domain.chatRoom.ChatRoomRepository;
import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.chat.ChatMessageRequest;
import com.example.studyplatform.exception.ChatRoomNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final RedisRepository redisRepository;
    private final RedisTemplate redisTemplate;
    private final ChannelTopic channelTopic;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

    // 채팅방 입장
    public void enter(Long userId, Long roomId) {
        // 채팅방에 들어온 정보를 Redis 저장
        redisRepository.userEnterRoomInfo(userId, roomId);

        // 그룹채팅은 해시코드가 존재하지 않고 일대일 채팅은 해시코드가 존재한다.
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(ChatRoomNotFoundException::new);
        if (chatRoom.getRoomHashCode() != 0) {
            redisRepository.initChatRoomMessageInfo(chatRoom.getId(), userId);
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
        String createdAt = getCurrentTime();
        chatMessageRequest.setCreatedAt(createdAt);
        chatMessageRequest.setUserId(user.getId());

        if (chatMessageRequest.getType() == ChatMessageRequest.MessageType.TALK) {
            // 일대일 채팅일 경우
            updateUnReadMessageCount(chatMessageRequest);
        } else {
            // 그륩 채팅일 경우
            redisTemplate.convertAndSend(topic, chatMessageRequest);
        }
    }

    //현재시간 추출 메소드
    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        return sdf.format(date);
    }

    //안읽은 메세지 업데이트
    private void updateUnReadMessageCount(ChatMessageRequest chatMessageRequest) {
        Long otherUserId = chatMessageRequest.getOtherUserId();
        Long roomId = chatMessageRequest.getRoomId();

        if (!redisRepository.existChatRoomUserInfo(otherUserId) || !redisRepository.getUserEnterRoomId(otherUserId).equals(roomId)) {

            redisRepository.addChatRoomMessageCount(roomId, otherUserId);
            int unReadMessageCount = redisRepository.getChatRoomMessageCount(roomId, otherUserId);

            String topic = channelTopic.getTopic();

            ChatMessageRequest messageRequest = new ChatMessageRequest(chatMessageRequest, unReadMessageCount);

            redisTemplate.convertAndSend(topic, messageRequest);
        }
    }
}
