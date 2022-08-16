package com.example.studyplatform.dto.chatRoom;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostOneToOneChatRoomRequest {
    // 상대방
    private Long anotherUserId;
}
