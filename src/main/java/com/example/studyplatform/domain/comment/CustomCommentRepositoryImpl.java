package com.example.studyplatform.domain.comment;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.board.Board;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class CustomCommentRepositoryImpl extends QuerydslRepositorySupport implements CustomCommentRepository {

    public CustomCommentRepositoryImpl() {
        super(Comment.class);
    }

    @Override
    public Long countCommentGroupByBoard(Board board) {
        QComment comment = QComment.comment;

        JPQLQuery<Long> query = from(comment)
                .select(comment.commentGroup)
                //.where(QComment.board.id.eq(board.getId()))
                .where(comment.status.eq(Status.ACTIVE))
                .orderBy(comment.commentGroup.desc());

        return query.fetchFirst();
    }

    @Override
    public List<Comment> findAllComments(Long boardId) {
        QComment comment = QComment.comment;

        JPQLQuery<Comment> query = from(comment)
                .where(comment.board.id.eq(boardId));


        query.orderBy(comment.commentGroup.asc())
                .orderBy(comment.createdAt.asc());

        return query.fetch();
    }
}
