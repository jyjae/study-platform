package com.example.studyplatform.domain.project.projectResume;

import com.example.studyplatform.constant.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectResumeRepository extends JpaRepository<ProjectResume, Long> {
    Optional<ProjectResume> findByIdAndStatus(Long id, Status status);
}
