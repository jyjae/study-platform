package com.example.studyplatform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatMessageController {

    /**
     * websocket "/pub/chat/enter"로 들어오는 메시징을 처리한다.
     * 채팅방에 입장했을 경우
     */
    @MessageMapping("/chat/enter")
    public void enter() {
        // TODO : 로직 작성
    }

    /**
     * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
     */
    @MessageMapping("/chat/message")
    public void message() {
        // TODO : 로직 작성
    }
}
