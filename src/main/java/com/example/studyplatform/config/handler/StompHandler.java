package com.example.studyplatform.config.handler;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.domain.user.UserRepository;
import com.example.studyplatform.exception.UserNotFoundException;
import com.example.studyplatform.service.chat.RedisRepository;
import com.example.studyplatform.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {
    private final RedisRepository redisRepository;
    private JwtService jwtService;
    private UserRepository userRepository;

    // WebSocket을 통해 들어온 요청이 처리 되기 전에 실행
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String jwtToken = "";

        if (StompCommand.CONNECT == accessor.getCommand()) {
            String jwt = accessor.getFirstNativeHeader("Authorization");
            if (StringUtils.hasText(jwt) && jwt.startsWith("Bearer")) {
                jwtToken = Objects.requireNonNull(accessor.getFirstNativeHeader("token")).substring(7);

                long userIdx = jwtService.getUserIdx(jwtToken);

                User user = userRepository.findByIdAndStatus(userIdx, Status.ACTIVE)
                        .orElseThrow(UserNotFoundException::new);

                String sessionId = (String) message.getHeaders().get("simpSessionId");
                redisRepository.saveMyInfo(sessionId, userIdx);
            }
        } else if (StompCommand.DISCONNECT == accessor.getCommand()) {
            String sessionId = (String) message.getHeaders().get("simpSessionId");

            // 채팅방에서 나가는 것이 맞는지 확인
            if(redisRepository.existMyInfo(sessionId)) {
                Long userIdx = redisRepository.getMyInfo(sessionId);

                // 채팅방 퇴장 정보 저장
                if(redisRepository.existChatRoomUserInfo(userIdx)) {
                    redisRepository.exitUserEnterRoomId(userIdx);
                }

                redisRepository.deleteMyInfo(sessionId);
            }
        }
        return message;
    }
}
