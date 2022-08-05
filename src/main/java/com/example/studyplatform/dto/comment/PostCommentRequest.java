package com.example.studyplatform.dto.comment;

import com.example.studyplatform.constant.comment.CommentType;
import com.example.studyplatform.domain.board.Board;
import com.example.studyplatform.domain.comment.Comment;
import com.example.studyplatform.domain.project.projectPost.ProjectPost;
import com.example.studyplatform.domain.studyBoard.StudyBoard;
import com.example.studyplatform.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentRequest {
    private Long boardId;
    private String content;
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
