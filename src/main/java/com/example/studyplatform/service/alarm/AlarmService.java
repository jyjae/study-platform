package com.example.studyplatform.service.alarm;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.alarm.Alarm;
import com.example.studyplatform.domain.alarm.AlarmRepository;
import com.example.studyplatform.dto.alarm.AlarmResponse;
import com.example.studyplatform.exception.AlarmNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void readAlarm(Long id) {
        Alarm alarm = alarmRepository.findByIdAndStatus(id, Status.ACTIVE).orElseThrow(AlarmNotFoundException::new);
        alarm.read();
    }
}
