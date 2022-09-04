package com.example.studyplatform.dto.chat;

import com.example.studyplatform.domain.chat.ChatMessage;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class GetChatMessageResponse {
    private Long userId;
    private String nickname;
    private String message; // type이 image일 경우 객체 URL이 담김
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    private Boolean isFile;

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
        this.isFile = request.getIsFile();
        this.createdAt = LocalDateTime.now(); // 현재시간 저장
    }
}
