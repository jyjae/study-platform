package com.example.studyplatform.domain.alarm;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.dto.alarm.AlarmResponse;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import static com.querydsl.core.types.Projections.constructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CustomAlarmRepositoryImpl implements CustomAlarmRepository{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<AlarmResponse> findAllByCursorId(Long cursorId, Pageable pageable) {
        QAlarm alarm = QAlarm.alarm;

        JPQLQuery<Alarm> query = jpaQueryFactory
                .selectFrom(alarm)
                .where(alarm.status.eq(Status.ACTIVE))
                .orderBy(alarm.id.desc());

        List<Alarm> result = query
                .where(ltCursorId(cursorId))
                .limit(pageable.getPageSize() + 1)
                .fetch();

        List<AlarmResponse> content = result.stream().map(AlarmResponse::toDto).collect(Collectors.toList());

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    private BooleanExpression ltCursorId(Long cursorId) {
        QAlarm alarm = QAlarm.alarm;
        if (cursorId != null) {
            return alarm.id.lt(cursorId);
        }
        return null;
    }

    @Override
    public List<AlarmResponse> findAllUnReadAlarms(Integer size) {
        QAlarm alarm = QAlarm.alarm;

        return jpaQueryFactory.select(constructor(AlarmResponse.class,
                        alarm.id,
                        alarm.title,
                        alarm.isRead,
                        alarm.url,
                        alarm.createdAt))
                .from(alarm)
                .where(alarm.status.eq(Status.ACTIVE))
                .where(alarm.isRead.eq(false))
                .orderBy(alarm.id.desc())
                .limit(size)
                .fetch();
    }
}
