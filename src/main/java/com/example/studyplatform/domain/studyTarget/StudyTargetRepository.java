package com.example.studyplatform.domain.studyTarget;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyTargetRepository extends JpaRepository<StudyTarget, Long> {

    List<StudyTarget> findAllByStudyId(Long studyId);

}
