package com.example.studyplatform.dto.chatRoom;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class GetGroupChatRoomResponse {
    private String roomName;
    private String chatRoomId;
    private String lastMessage;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createAt;
    private String dayBefore;

    public GetGroupChatRoomResponse(
            String roomName,
            String chatRoomId,
            String lastMessage,
            LocalDateTime createAt,
            String dayBefore
    ) {
        this.roomName = roomName;
        this.chatRoomId = chatRoomId;
        this.lastMessage = lastMessage;
        this.createAt = createAt;
        this.dayBefore = dayBefore;
    }
}
