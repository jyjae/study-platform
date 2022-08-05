package com.example.studyplatform.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PutCommentRequest {
    private Long commentId;
    private String content;
}
