package com.example.studyplatform.dto.comment;

import com.example.studyplatform.domain.comment.Comment;
import com.example.studyplatform.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private Long userId;
    private String nickName;
    private String profileImgUrl;
    private Long commentId;
    private String content;
    private LocalDateTime createAt;

    public static CommentResponse of(
            User user,
            Comment comment
    ) {
        return new CommentResponse(
                user.getId(),
                user.getNickname(),
                user.getProfileImg(),
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt());
    }
}
