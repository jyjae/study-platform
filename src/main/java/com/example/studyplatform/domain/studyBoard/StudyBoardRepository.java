package com.example.studyplatform.domain.studyBoard;

import com.example.studyplatform.constant.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudyBoardRepository extends JpaRepository<StudyBoard, Long> {
    Optional<StudyBoard> findByIdAndUserIdAndStatus(Long studyBoardId, Long id, Status active);

    Optional<StudyBoard> findByIdAndStatus(Long studyBoardId, Status active);
}
