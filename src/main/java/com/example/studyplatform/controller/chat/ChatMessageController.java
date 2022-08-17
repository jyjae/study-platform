package com.example.studyplatform.controller.chat;

import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.chat.ChatMessageRequest;
import com.example.studyplatform.service.chat.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    /**
     * websocket "/pub/chat/enter"로 들어오는 메시징을 처리한다.
     * 채팅방에 입장했을 경우
     */
    @MessageMapping("/chat/enter")
    public void enter(
            ChatMessageRequest chatMessageRequest,
            @AuthenticationPrincipal User user
    ) {
        chatMessageService.enter(user.getId(), chatMessageRequest.getRoomId());
    }

    /**
     * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
     */
    @MessageMapping("/chat/message")
    public void message(
            ChatMessageRequest chatMessageRequest,
            @AuthenticationPrincipal User user
    ) {
        chatMessageService.sendMessage(chatMessageRequest, user);
    }
}
