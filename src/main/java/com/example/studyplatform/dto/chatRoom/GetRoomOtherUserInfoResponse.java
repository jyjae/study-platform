package com.example.studyplatform.dto.chatRoom;

import com.example.studyplatform.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetRoomOtherUserInfoResponse {

    private Long otherUserId;
    private String otherUserNickname;

    public GetRoomOtherUserInfoResponse(User otherUser) {
        this.otherUserId = otherUser.getId();
        this.otherUserNickname = otherUser.getNickname();
    }
}
