package com.example.studyplatform.domain.comment;

import com.example.studyplatform.domain.board.Board;

import java.util.List;

public interface CustomCommentRepository {
    Long countCommentGroupByBoard(Board board);

    List<Comment> findAllComments(Long boardId);
}
