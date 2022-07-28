package com.example.studyplatform.domain.techStack;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TechStackRepository extends JpaRepository<TechStack, Long> {
    Optional<TechStack> findByTechName(String techName);
}
