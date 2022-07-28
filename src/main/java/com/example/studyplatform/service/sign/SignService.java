package com.example.studyplatform.service.sign;

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
public class SignService {
    private final UserRepository userRepository;
    private final TechStackRepository techStackRepository;
    private final CareerRepository careerRepository;
    private final PasswordEncoder passwordEncoder; // securityConfig 파일 Bean 호출

    @Transactional
    public void signUp(SignUpRequest req) {
        // 1. 중복 검사
        validateSignUp(req);
        
        // 2. 경력 저장
        List<Career> careers = toCareerList(req);
        careerRepository.saveAll(careers);

        // 3. 관심 스택 불러오기
        List<TechStack> techStacks = toTechStackList(req);

        // 4. 비밀번호 암호화 
        String encodedPassword = passwordEncoder.encode(req.getPassword());

        // 5. 유저 저장
        userRepository.save(User.builder().username(req.getUsername())
                .nickname(req.getNickname())
                .password(encodedPassword)
                .email(req.getEmail())
                .profileImg(req.getProfileImg())
                .careers(careers)
                .techStacks(techStacks).build());
    }

    private List<Career> toCareerList(SignUpRequest req) {
        return req.getCareers().stream().map(dto ->
                CareerCreateDto.toEntity(dto.getMonth(), techStackRepository.findByTechName(dto.getTechName())
                        .orElseThrow(TechStackNotFoundException::new))).collect(Collectors.toList());
    }

    private List<TechStack> toTechStackList(SignUpRequest req) {
        return req.getTechNames().stream().map(i ->
                techStackRepository.findByTechName(i).orElseThrow(TechStackNotFoundException::new))
                .collect(Collectors.toList());
    }

    private void validateSignUp(SignUpRequest req) {
        if(userRepository.existsByEmail(req.getEmail()))
            throw new UserEmailAlreadyExistsException();
        
        if(userRepository.existsByNickname(req.getNickname()))
            throw new UserNicknameAlreadyExistsException();
    }
}
