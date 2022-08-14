package com.example.studyplatform.service.studyUser;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.study.Study;
import com.example.studyplatform.domain.study.StudyRepository;
import com.example.studyplatform.domain.studyUser.StudyUser;
import com.example.studyplatform.domain.studyUser.StudyUserRepository;
import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.exception.StudyNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyUserService {

    private final StudyUserRepository studyUserRepository;
    private final StudyRepository studyRepository;


    @Transactional
    public Response create(Long studyId, User user) {
        Study study = studyRepository.findById(studyId).orElseThrow(StudyNotFoundException::new);

        StudyUser studyUser = StudyUser.builder()
                .studyLeader(false)
                .study(study)
                .user(user)
                .status(Status.ACTIVE)
                .build();

        studyUserRepository.save(studyUser);

        return Response.success();
    }

    @Transactional
    public Response exitStudy(Long studyUserId, User user){
        StudyUser studyUser = studyUserRepository.findByStudyIdAndUserId(studyUserId, user.getId()).get();

        studyUser.inActive();

        return Response.success();
    }
}
