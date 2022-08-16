package com.example.studyplatform.dto.chatRoom;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostGroupChatRoomRequest {
    private Long chatRoomId;
    private List<Long> anotherUserIds;
}
