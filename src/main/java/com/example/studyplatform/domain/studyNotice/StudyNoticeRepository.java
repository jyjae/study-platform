package com.example.studyplatform.domain.studyNotice;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudyNoticeRepository extends JpaRepository<StudyNotice, Long> {

    List<StudyNotice> findAllByStudyId(Long studyId);
    Optional<StudyNotice> findByIdAndUserId(Long studyNoticeId, Long userId);

}
