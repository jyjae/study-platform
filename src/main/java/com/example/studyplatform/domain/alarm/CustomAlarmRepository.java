package com.example.studyplatform.domain.alarm;

import com.example.studyplatform.dto.alarm.AlarmResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface CustomAlarmRepository {
    Slice<AlarmResponse> findAllByCursorId(Long cursorId, Pageable pageable);

    List<AlarmResponse> findAllUnReadAlarms(Integer size);
}
