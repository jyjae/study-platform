package com.example.studyplatform.service.user;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.career.Career;
import com.example.studyplatform.domain.career.CareerRepository;
import com.example.studyplatform.domain.techStack.TechStack;
import com.example.studyplatform.domain.techStack.TechStackRepository;
import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.domain.user.UserRepository;
import com.example.studyplatform.dto.career.CareerCreateDto;
import com.example.studyplatform.dto.sign.SignUpRequest;
import com.example.studyplatform.exception.TechStackNotFoundException;
import com.example.studyplatform.exception.UserEmailAlreadyExistsException;
import com.example.studyplatform.exception.UserNicknameAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final TechStackRepository techStackRepository;
    private final CareerRepository careerRepository;
    private final PasswordEncoder passwordEncoder; // securityConfig 파일 Bean 호출

    @Transactional
    public void signUp(SignUpRequest req) {
        // 1. 이름, 닉네임 중복 검사
        validateSignUp(req);

        // 2. 경력 저장
        List<Career> careers = toCareerList(req);
        careerRepository.saveAll(careers);

        // 3. 관심 스택 불러오기
        List<TechStack> techStacks = toTechStackList(req);

        // 4. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(req.getPassword());

        // 5. 유저 생성
        User user = User.builder().username(req.getUsername())
                .nickname(req.getNickname())
                .password(encodedPassword)
                .email(req.getEmail())
                .profileImg(req.getProfileImg()).build();

        // 6. 관심 스택 및 경력 유저 정보에 추가
        techStacks.forEach(user::addTechStack);
        careers.forEach(user::addCareer);

        // 7. 유저 저장
        userRepository.save(user);
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
