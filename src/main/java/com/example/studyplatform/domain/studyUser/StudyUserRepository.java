package com.example.studyplatform.domain.studyUser;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyUserRepository extends CustomStudyUserRepository, JpaRepository<StudyUser, Long> {
}
