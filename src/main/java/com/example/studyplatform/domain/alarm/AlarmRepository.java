package com.example.studyplatform.domain.alarm;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlarmRepository extends JpaRepository<Alarm, Long>, CustomAlarmRepository {
    Optional<Alarm> findByIdAndStatus(Long Id, Status status);
}
