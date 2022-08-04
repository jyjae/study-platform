package com.example.studyplatform.domain.project.projectPost;

import com.example.studyplatform.constant.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProjectPostRepository extends JpaRepository<ProjectPost, Long> {
    Optional<ProjectPost> findByIdAndStatus(Long id, Status status);
}
