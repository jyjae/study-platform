package com.example.studyplatform.dto.comment;

import com.example.studyplatform.domain.comment.Comment;
import com.example.studyplatform.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCommentResponse {
    private Long userId;
    private Long parentCommentId;
    private Long parentCommentUserId;
    private String parentCommentUserNickName;
    private String nickName;
    private String profileImgUrl;
    private Long commentId;
    private String content;
    private Integer commentLevel;
    private Long commentGroup;
    private LocalDateTime createAt;

    public static GetCommentResponse of(
            Comment comment
    ) {
        return new GetCommentResponse(
                comment.getUser().getId(),
                comment.getParentComment().getId(),
                comment.getParentComment().getUser().getId(),
                comment.getParentComment().getUser().getNickname(),
                comment.getUser().getNickname(),
                comment.getUser().getProfileImg(),
                comment.getId(),
                comment.getContent(),
                comment.getCommentLevel(),
                comment.getCommentGroup(),
                comment.getCreatedAt());
    }


}
