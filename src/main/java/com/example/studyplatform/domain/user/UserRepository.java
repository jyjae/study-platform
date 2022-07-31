package com.example.studyplatform.domain.user;

import com.example.studyplatform.constant.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    User findByIdAndStatus(long parseLong, Status active);

    User findByEmailAndStatus(String email, Status active);
}
