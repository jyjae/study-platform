package com.example.studyplatform.dto.chatRoom;

import com.example.studyplatform.domain.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "다른 유저 정보 조회 DTO")
public class GetRoomOtherUserInfoResponse {

    private Long otherUserId;
    private String otherUserNickname;

    public GetRoomOtherUserInfoResponse(User otherUser) {
        this.otherUserId = otherUser.getId();
        this.otherUserNickname = otherUser.getNickname();
    }
}
