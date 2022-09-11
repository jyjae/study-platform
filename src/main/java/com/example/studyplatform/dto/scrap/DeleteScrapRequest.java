package com.example.studyplatform.dto.scrap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "스크랩 삭제 Request")
public class DeleteScrapRequest {
    @NotBlank(message = "유저 아이디를 입력해주세요")
    @Schema(nullable = false, description = "유저 아이디")
    private Long userId;

    @NotBlank(message = "게시글 아이디를 입력해주세요")
    @Schema(nullable = false, description = "게시글 아이디")
    private Long boardId;
}
