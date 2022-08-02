package com.example.studyplatform.domain.project.projectOrganization;

import com.example.studyplatform.constant.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectOrganizationRepository extends JpaRepository<ProjectOrganization, Long> {
    Optional<ProjectOrganization> findByIdAndStatus(Long id, Status active);
}
