package com.example.studyplatform.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageResponse {

    private Long roomId;
    private String message;
    private String createdAt;
   // private Long otherUserId; 그륩채팅일 경우는?
    private Long userId;

    public ChatMessageResponse(ChatMessageRequest request) {
        this.roomId = request.getRoomId();
        this.message = request.getMessage();
        this.createdAt = request.getCreatedAt();
        this.userId = request.getUserId();
    }
}
