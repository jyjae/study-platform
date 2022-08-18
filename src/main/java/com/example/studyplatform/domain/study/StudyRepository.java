package com.example.studyplatform.domain.study;

import com.example.studyplatform.constant.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudyRepository extends JpaRepository<Study, Long> {
    Optional<Study> findByIdAndStatus(Long studyId, Status active);
}
