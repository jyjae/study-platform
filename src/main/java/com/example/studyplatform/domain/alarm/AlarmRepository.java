package com.example.studyplatform.domain.alarm;

import com.example.studyplatform.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long>, CustomAlarmRepository {
}
