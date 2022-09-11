package com.example.studyplatform.dto.chatRoom;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor
@Schema(description = "그룹 채팅방 생성 Request")
public class PostGroupChatRoomRequest {
    @NotBlank(message = "스터디 아이디를 입력해주세요")
    @Schema(description = "스터디 아이디")
    private Long studyId;

    @Schema(description = "그룹 채팅방 아이디, 처음 생성 시 입력안해도 됩니다.", nullable = true)
    private Long chatRoomId;

    @NotBlank
    @Schema(description = "그룹 유저 아이디들")
    private List<Long> anotherUserIds;
}
