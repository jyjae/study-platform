package com.example.studyplatform.domain.board;

import com.example.studyplatform.constant.CareerStatus;
import com.example.studyplatform.domain.project.projectOrganization.QProjectOrganization;
import com.example.studyplatform.domain.project.projectPost.ProjectPost;
import com.example.studyplatform.domain.project.projectPost.QProjectPost;
import com.example.studyplatform.domain.studyBoard.QStudyBoard;
import com.example.studyplatform.domain.studyBoard.StudyBoard;
import com.example.studyplatform.domain.studyTechStack.QStudyTechStack;
import com.example.studyplatform.domain.techStack.QTechStack;
import com.example.studyplatform.dto.board.BoardDto;
import com.example.studyplatform.dto.board.BoardReadCondition;
import com.example.studyplatform.dto.board.BoardProjectDto;
import com.example.studyplatform.dto.board.BoardStudyDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.studyplatform.domain.board.QBoard.board;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<BoardDto> findAllByCondition(Long cursorId, BoardReadCondition condition, Pageable pageable) {

        QProjectPost projectPost = QProjectPost.projectPost;
        QStudyBoard studyBoard = QStudyBoard.studyBoard;
        QProjectOrganization organization = QProjectOrganization.projectOrganization;
        QStudyTechStack studyTechStack = QStudyTechStack.studyTechStack;

        List<Board> result = jpaQueryFactory
                .selectFrom(board)
                .leftJoin(projectPost).on(projectPost.eq(board))
                .leftJoin(studyBoard).on(studyBoard.eq(board))
                .leftJoin(studyBoard.studyTechStacks, studyTechStack)
                .leftJoin(projectPost.organizations, organization)
                .distinct()
                .where(eqType(condition.getDType()), eqCity(condition.getCity()), eqCursorEndDate(cursorId)
                ,eqTechIds(condition.getTechIds()), eqCareerStatus(condition.getCareerStatus()))
                .limit(pageable.getPageSize() + 1)
                .fetch();

        List<BoardDto> content = result.stream().map(i -> {
            if (i instanceof ProjectPost) {
                return new BoardProjectDto((ProjectPost) i);
            } else {
                return new BoardStudyDto((StudyBoard) i);
            }
        }).collect(Collectors.toList());

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    private BooleanExpression eqTechIds(List<Long> techIds) {
        if (techIds != null) {
            return Expressions.anyOf(techIds.stream().map(this::eqTechId).toArray(BooleanExpression[]::new));
        }

        return null;
    }

    private BooleanExpression eqTechId(Long techId) {
        //return QTechStack.techStack.id.eq(techId);
        return Expressions.anyOf(QProjectOrganization.projectOrganization.techStack.id.eq(techId),
                QStudyTechStack.studyTechStack.techStack.id.eq(techId));
    }

    private BooleanExpression eqCareerStatus(CareerStatus careerStatus) {
        if (careerStatus != null) {
            return Expressions.anyOf(QStudyBoard.studyBoard.careerStatus.eq(careerStatus),
                    QProjectOrganization.projectOrganization.careerStatus.eq(careerStatus));
        }

        return null;
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
