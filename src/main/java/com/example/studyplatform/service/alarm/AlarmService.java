package com.example.studyplatform.service.alarm;

import com.example.studyplatform.domain.alarm.AlarmRepository;
import com.example.studyplatform.dto.alarm.AlarmResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AlarmService {
    private final AlarmRepository alarmRepository;

    public Slice<AlarmResponse> readAllByCursorId(Long cursorId, Pageable pageable) {
        return alarmRepository.findAllByCursorId(cursorId, pageable);
    }

    public List<AlarmResponse> readAllUnReadAlarms(Integer size) {
        return alarmRepository.findAllUnReadAlarms(size);
    }
}
