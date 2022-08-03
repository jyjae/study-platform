package com.example.studyplatform.domain.studyApply;

import com.example.studyplatform.constant.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudyApplyRepository extends JpaRepository<StudyApply, Long> {
    Optional<StudyApply> findByIdAndUserIdAndStatus(Long applyId, Long userId, Status active);

    Optional<StudyApply> findByIdAndStatus(Long applyId, Status active);
}
