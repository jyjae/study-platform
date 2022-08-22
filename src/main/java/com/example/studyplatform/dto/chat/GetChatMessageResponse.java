package com.example.studyplatform.dto.chat;

import com.example.studyplatform.domain.chat.ChatMessage;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class GetChatMessageResponse {
    private Long userId;
    private String nickname;
    private String message;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime createdAt;

    public GetChatMessageResponse(ChatMessage chatMessage) {
        this.userId = chatMessage.getUser().getId();
        this.nickname = chatMessage.getUser().getNickname();
        this.message = chatMessage.getMessage();
        this.createdAt = chatMessage.getCreatedAt();
    }

    public GetChatMessageResponse(ChatMessageRequest request) {
        this.userId = request.getUserId();
        this.nickname = request.getNickName();
        this.message = request.getMessage();
        this.createdAt = LocalDateTime.now(); // 현재시간 저장
    }
}
