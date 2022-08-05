package com.example.studyplatform.domain.comment;

import com.example.studyplatform.constant.comment.CommentType;
import com.example.studyplatform.domain.board.Board;
import com.example.studyplatform.domain.project.projectPost.ProjectPost;
import com.example.studyplatform.domain.studyBoard.StudyBoard;

import java.util.List;

public interface CustomCommentRepository {
    Long countCommentGroupByBoard(Board board);

    List<Comment> findAllComments(Long boardId);
}
