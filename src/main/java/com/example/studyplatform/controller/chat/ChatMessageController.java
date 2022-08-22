package com.example.studyplatform.controller.chat;

import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.domain.user.UserRepository;
import com.example.studyplatform.dto.chat.ChatMessageRequest;
import com.example.studyplatform.exception.UserNotFoundException;
import com.example.studyplatform.service.chat.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatMessageController {

    private final ChatMessageService chatMessageService;
    private final UserRepository userRepository;

    /**
     * websocket "/pub/chat/enter"로 들어오는 메시징을 처리한다.
     * 채팅방에 입장했을 경우
     */
    @MessageMapping("/chat/enter")
    public void enter(
            ChatMessageRequest chatMessageRequest
    ) {
        User user = userRepository.findById(chatMessageRequest.getUserId()).orElseThrow(UserNotFoundException::new);

        chatMessageService.enter(user.getId(), chatMessageRequest.getRoomId());
    }

    /**
     * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
     */
    @MessageMapping("/chat/message")
    public void message(
            ChatMessageRequest chatMessageRequest
    ) {
        User user = userRepository.findById(chatMessageRequest.getUserId()).orElseThrow(UserNotFoundException::new);

        chatMessageService.sendMessage(chatMessageRequest, user);
    }
}
