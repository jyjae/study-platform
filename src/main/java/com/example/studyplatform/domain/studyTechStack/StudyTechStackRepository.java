package com.example.studyplatform.domain.studyTechStack;

import com.example.studyplatform.constant.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudyTechStackRepository extends JpaRepository<StudyTechStack, Long> {
    Optional<StudyTechStack> findByIdAndStatus(Long i, Status active);

    List<StudyTechStack> findAllByStudyBoardIdAndStatus(Long studyBoardId, Status active);
}
