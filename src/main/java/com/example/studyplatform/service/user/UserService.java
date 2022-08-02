package com.example.studyplatform.service.user;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.career.Career;
import com.example.studyplatform.domain.career.CareerRepository;
import com.example.studyplatform.domain.techStack.TechStack;
import com.example.studyplatform.domain.techStack.TechStackRepository;
import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.domain.user.UserRepository;
import com.example.studyplatform.dto.career.CareerCreateDto;
import com.example.studyplatform.dto.login.LoginRequest;
import com.example.studyplatform.dto.login.LoginResponse;
import com.example.studyplatform.dto.sign.SignUpRequest;
import com.example.studyplatform.exception.TechStackNotFoundException;
import com.example.studyplatform.exception.UserEmailAlreadyExistsException;
import com.example.studyplatform.exception.UserNicknameAlreadyExistsException;
import com.example.studyplatform.exception.UserNotFoundException;
import com.example.studyplatform.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final TechStackRepository techStackRepository;
    private final CareerRepository careerRepository;
    private final PasswordEncoder passwordEncoder; // securityConfig 파일 Bean 호출
    private final JwtService jwtService; // jwtService

    @Transactional
    public void signUp(SignUpRequest req) {
        // 1. 이름, 닉네임 중복 검사
        validateSignUp(req);

        // 2. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(req.getPassword());

        // 3. 유저 생성
        User user = User.builder().username(req.getUsername())
                .nickname(req.getNickname())
                .password(encodedPassword)
                .email(req.getEmail())
                .profileImg(req.getProfileImg())
                .roles(Collections.singletonList("ROLE_USER")).build();

        // 4. 경력 정보 있을 시 데이터 추가
        if(!req.getCareers().isEmpty()){
            List<Career> careers = toCareerList(req);
            careerRepository.saveAll(careers);
            careers.forEach(user::addCareer);
        }

        // 5. 스택 정보 있을 시 데이터 추가
        if (!req.getTechIds().isEmpty()) {
            List<TechStack> techStacks = toTechStackList(req);
            techStacks.forEach(user::addTechStack);
        }

        // 6. 유저 저장
        userRepository.save(user);
    }

    public LoginResponse login(LoginRequest req) {
        // 1. 유저 이메일 유효성 검사
        User user = userRepository.findByEmailAndStatus(req.getEmail(), Status.ACTIVE).orElseThrow(UserNotFoundException::new);
        validateEmail(user);

        // 2. 유저 비밀번호 유효성 검사
        validatePassword(user.getPassword(), req.getPassword());

        // 3. 토큰 생성
        Long userIdx = user.getId();

        return LoginResponse.toDto(userIdx, jwtService.createJwt( user.getId()));
    }

    private void validatePassword(String encodedPassword, String reqPassword) {
        if(!passwordEncoder.matches(reqPassword, encodedPassword)) throw new UserNotFoundException();
    }

    private void validateEmail(User user) {
        if(user == null) throw new UserNotFoundException();
    }

    // CareerCreateDto를 순회하여 List<Career>로 변환 후 반환해주는 메소드
    private List<Career> toCareerList(SignUpRequest req) {
        return req.getCareers().stream().map(dto ->
                CareerCreateDto.toEntity(dto.getMonth(), techStackRepository.findByIdAndStatus(dto.getTechId(), Status.ACTIVE)
                        .orElseThrow(TechStackNotFoundException::new))).collect(Collectors.toList());
    }

    private List<TechStack> toTechStackList(SignUpRequest req) {
        return req.getTechIds().stream().map(i ->
                        techStackRepository.findByIdAndStatus(i, Status.ACTIVE).orElseThrow(TechStackNotFoundException::new))
                .collect(Collectors.toList());
    }

    private void validateSignUp(SignUpRequest req) {
        if(userRepository.existsByEmail(req.getEmail()))
            throw new UserEmailAlreadyExistsException();

        if(userRepository.existsByNickname(req.getNickname()))
            throw new UserNicknameAlreadyExistsException();
    }


}
