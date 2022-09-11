package com.example.studyplatform.dto.comment;

import com.example.studyplatform.domain.board.Board;
import com.example.studyplatform.domain.comment.Comment;
import com.example.studyplatform.domain.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "댓글 생성 Request")
public class PostCommentRequest {
    @NotBlank(message = "게시글 아이디를 입력해주세요.")
    @Schema(description = "게시글 아이디")
    private Long boardId;

    @Schema(description = "게시글 내용", nullable = true)
    private String content;

    @Schema(description = "부모 댓글 아이디", nullable = true)
    private Long parentCommentId;

    public static Comment toEntity(
        String content,
        User user,
        Long commentGroup,
        Board board,
        Comment parentComment
    ) {
        return new Comment(
                content,
                user,
                board,
                parentComment == null ? 0:
                        parentComment.getCommentLevel() <=1
                                ? parentComment.getCommentLevel()+1 :2,
                commentGroup,
                parentComment);
    }
}
