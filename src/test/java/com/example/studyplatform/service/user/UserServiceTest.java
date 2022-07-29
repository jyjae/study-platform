package com.example.studyplatform.service.user;

import com.example.studyplatform.domain.career.CareerRepository;
import com.example.studyplatform.domain.techStack.TechStackRepository;
import com.example.studyplatform.domain.user.UserRepository;
import com.example.studyplatform.exception.TechStackNotFoundException;
import com.example.studyplatform.exception.UserEmailAlreadyExistsException;
import com.example.studyplatform.exception.UserNicknameAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static com.example.studyplatform.factory.dto.CareerCreateDtoFactory.createCareerCreateDto;
import static com.example.studyplatform.factory.dto.SignUpRequestFactory.createSignUpRequest;
import static com.example.studyplatform.factory.entity.TechStackFactory.createTechStack;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    UserService userService;
    @Mock
    UserRepository userRepository;
    @Mock
    TechStackRepository techStackRepository;
    @Mock
    CareerRepository careerRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    void signUpTest() {
        // given
        given(userRepository.existsByEmail(anyString())).willReturn(false);
        given(userRepository.existsByNickname(anyString())).willReturn(false);
        given(techStackRepository.findByIdAndStatus(anyLong(), any())).willReturn(Optional.of(createTechStack()));

        // when
        userService.signUp(createSignUpRequest(List.of(createCareerCreateDto()), List.of(1L)));

        // then
        verify(careerRepository).saveAll(any());
        verify(passwordEncoder).encode(any());
        verify(userRepository).save(any());
    }

    @Test
    void signUpTechStackNotFoundTest() {
        // given
        given(userRepository.existsByEmail(anyString())).willReturn(false);
        given(userRepository.existsByNickname(anyString())).willReturn(false);
        given(techStackRepository.findByIdAndStatus(anyLong(), any())).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> userService.signUp(createSignUpRequest(List.of(createCareerCreateDto()), List.of(1L))))
                .isInstanceOf(TechStackNotFoundException.class);
    }

    @Test
    void validateSignUpDuplicateEmailTest() {
        // given
        given(userRepository.existsByEmail(anyString())).willReturn(true);

        // when, then
        assertThatThrownBy(() -> userService.signUp(createSignUpRequest()))
                .isInstanceOf(UserEmailAlreadyExistsException.class);
    }

    @Test
    void validateSignUpDuplicateNicknameTest() {
        // given
        given(userRepository.existsByNickname(anyString())).willReturn(true);

        // when, then
        assertThatThrownBy(() -> userService.signUp(createSignUpRequest()))
                .isInstanceOf(UserNicknameAlreadyExistsException.class);
    }
}