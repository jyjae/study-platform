package com.example.studyplatform.dto.chatRoom;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@Schema(description = "일대일 채팅방 생성 Request")
public class PostOneToOneChatRoomRequest {
    // 상대방
    @NotBlank(message = "상대방 유저 아이디를 입력해주세요")
    @Schema(description = "상대방 유저", nullable = false)
    private Long anotherUserId;
}
