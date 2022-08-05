package com.example.studyplatform.domain.board;

import com.example.studyplatform.domain.studyBoard.QStudyBoard;
import com.example.studyplatform.dto.board.BoardDto;
import com.example.studyplatform.dto.board.BoardReadCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.ArrayList;
import java.util.List;

import static com.example.studyplatform.domain.board.QBoard.board;
import static com.querydsl.core.types.Projections.constructor;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<BoardDto> findAllByCondition(Long cursorId, BoardReadCondition condition
    , Pageable pageable) {
        List<BoardDto> boardDtoList = jpaQueryFactory
                .select(constructor(BoardDto.class,
                        board.id,
                        board.dtype,
                        board.user.nickname,
                        board.title,
                        board.isOnline,
                        board.isCamera,
                        board.isMike,
                        board.isFinish,
                        board.recruitStartedAt,
                        board.recruitEndedAt,
                        board.startedAt,
                        board.endedAt))
                .from(board)
                .where(eqType(condition.getDType()), eqCursorEndDate(cursorId), eqCity(condition.getCity()))
                .limit(pageable.getPageSize() + 1)
                .fetch();

        List<BoardDto> content = new ArrayList<>();

        for (BoardDto boardDto : boardDtoList) {
            content.add(boardDto);
        }

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    private BooleanExpression eqCursorEndDate(Long cursorId) {
        if (cursorId != null) {
            return board.id.gt(cursorId);
        }
        return null;
    }

    private BooleanExpression eqCity(String city) {
        if (city != null) {
            return board.city.eq(city);
        }
        return null;
    }

    private BooleanExpression eqType(String type) {
        if (type != null) {
            return board.dtype.eq(type);
        }
        return null;
    }
}
