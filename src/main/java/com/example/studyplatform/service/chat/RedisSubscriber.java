package com.example.studyplatform.service.chat;

import com.example.studyplatform.dto.alarm.AlarmRequest;
import com.example.studyplatform.dto.alarm.AlarmResponse;
import com.example.studyplatform.dto.chat.ChatMessageRequest;
import com.example.studyplatform.dto.chat.GetChatMessageResponse;
import com.example.studyplatform.dto.chat.UnreadMessageCount;
import com.example.studyplatform.exception.ChatMessageNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static com.example.studyplatform.dto.chat.ChatMessageRequest.MessageType;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {
    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    /**
     * Redis에서 메시지가 발행되면 대기하고 있던 onMessage가 메시지를 받아 messagingTemplate를 이용하여 websocket 클라이언트들에게 메시지 전달
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            // redis에서 발행된 데이터를 받아 역직렬화
            String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());

            // ChatMessage 객체로 맵핑
            ChatMessageRequest roomMessage = objectMapper.readValue(publishMessage, ChatMessageRequest.class);

            // getType이 TALK, GROUP_TALK, UNREAD_MESSAGE_COUNT_ALARM 일 경우
            if (roomMessage.getType() != null) {
                if (roomMessage.getType().equals(MessageType.UNREAD_MESSAGE_COUNT_ALARM)) {
                    // 안 읽은 메세지일 경우
                    UnreadMessageCount messageCount = new UnreadMessageCount(roomMessage);
                    Long otherUserId = roomMessage.getOtherUserIds().stream().collect(Collectors.toList()).get(0);
                    // Websocket 구독자에게 안읽은 메세지 반환
                    messagingTemplate.convertAndSend("/sub/chat/unread/" + otherUserId, messageCount);
                } else {
                    // 그룹채팅이거나 일대일 채팅일 경우
                    GetChatMessageResponse chatMessageResponse = new GetChatMessageResponse(roomMessage);
                    // Websocket 구독자에게 채팅 메시지 전송
                    messagingTemplate.convertAndSend("/sub/chat/room/" + roomMessage.getRoomId(), chatMessageResponse);
                }
            } else {   // 만약 AlarmRequest 클래스로 넘어왔다면
                AlarmRequest alarmRequest = objectMapper.readValue(publishMessage, AlarmRequest.class);
                messagingTemplate.convertAndSend("/sub/user/" + alarmRequest.getOtherUserId(), AlarmResponse.toDto(alarmRequest));
            }
        } catch (Exception e) {
            throw new ChatMessageNotFoundException();
        }
    }
}
