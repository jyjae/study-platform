package com.example.studyplatform.domain.studyUser;

import java.util.Optional;

public interface CustomStudyUserRepository {

    Optional<StudyUser> findByStudyIdAndUserId(Long studyId, Long userId);

}
