package com.example.studyplatform.dto.chat;

import lombok.Getter;
import lombok.Setter;

import java.util.stream.Collectors;

@Getter
@Setter
public class UnreadMessageCount {
    private Long otherUserId;
    private int unreadCount;
    private Long roomId;
    private String type;

    public UnreadMessageCount(ChatMessageRequest roomMessage) {
        this.type = "UNREAD";
        this.otherUserId = roomMessage.getOtherUserIds().stream().collect(Collectors.toList()).get(0);
        this.roomId = roomMessage.getRoomId();
        this.unreadCount = roomMessage.getCount();
    }
}
