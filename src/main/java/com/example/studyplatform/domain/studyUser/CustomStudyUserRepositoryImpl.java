package com.example.studyplatform.domain.studyUser;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class CustomStudyUserRepositoryImpl implements CustomStudyUserRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<StudyUser> findByStudyIdAndUserId(Long studyId, Long userId) {
        QStudyUser studyUser = QStudyUser.studyUser;

        StudyUser findStudyUser = jpaQueryFactory.selectFrom(studyUser)
                .where(studyUser.study.id.eq(studyId), studyUser.user.id.eq(userId))
                .fetchOne();

        return Optional.ofNullable(findStudyUser);
    }
}
