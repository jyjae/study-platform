package com.example.studyplatform.domain.user;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.constant.oauth.ProviderName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    Optional<User> findByIdAndStatus(long parseLong, Status active);

    Optional<User> findByEmailAndStatus(String email, Status active);

    Optional<User> findByNicknameAndStatus(String name, Status active);

    int existsByEmailAndProviderNameAndStatus(String email, ProviderName kakao, Status active);


    Optional<User> findByEmailAndProviderNameAndStatus(String email, ProviderName kakao, Status active);
}
