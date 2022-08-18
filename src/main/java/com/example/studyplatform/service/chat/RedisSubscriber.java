package com.example.studyplatform.service.chat;

import com.example.studyplatform.dto.alarm.AlarmRequest;
import com.example.studyplatform.dto.alarm.AlarmResponse;
import com.example.studyplatform.dto.chat.ChatMessageRequest;
import com.example.studyplatform.exception.ChatMessageNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {
    private final RedisRepository redisRepository;
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

            // 만약 ChatMessageRequest 클래스로 넘어왔다면
            if (objectMapper.canSerialize(ChatMessageRequest.class)) {
                // ChatMessage 객체로 맵핑
                ChatMessageRequest roomMessage = objectMapper.readValue(publishMessage, ChatMessageRequest.class);

                if (roomMessage.getType().equals(ChatMessageRequest.MessageType.TALK)){
                    // Websocket 구독자에게 채팅 메시지 전송
                    messagingTemplate.convertAndSend("/sub/chat/room/" + roomMessage.getRoomId(), roomMessage);
                }

            }else{   // 만약 AlarmRequest 클래스로 넘어왔다면
                AlarmRequest alarmRequest = objectMapper.readValue(publishMessage, AlarmRequest.class);
                messagingTemplate.convertAndSend("/sub/chat/room/" + alarmRequest.getOtherUserId(), AlarmResponse.toDto(alarmRequest));
            }

        } catch (Exception e) {
            throw new ChatMessageNotFoundException();
        }
    }
}
