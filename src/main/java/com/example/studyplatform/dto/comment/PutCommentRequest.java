package com.example.studyplatform.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PutCommentRequest {
    @NotBlank(message = "댓글 아이디를 입력해주세요.")
    private Long commentId;
    private String content;
}
