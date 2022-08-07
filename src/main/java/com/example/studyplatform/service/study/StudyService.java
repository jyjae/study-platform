package com.example.studyplatform.service.study;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.study.Study;
import com.example.studyplatform.domain.study.StudyRepository;
import com.example.studyplatform.domain.studyUser.StudyUser;
import com.example.studyplatform.domain.studyUser.StudyUserRepository;
import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.dto.study.PostStudyRequest;
import com.example.studyplatform.dto.study.PutStudyRequest;
import com.example.studyplatform.dto.study.StudyResponse;
import com.example.studyplatform.exception.StudyNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class StudyService {

    private final StudyRepository studyRepository;
    private final StudyUserRepository studyUserRepository;

    public List<StudyResponse> list(){
        List<StudyResponse> collect = studyRepository.findAll().stream().map(study -> study.result()).collect(Collectors.toList());
        return collect;
    }

    public StudyResponse get(Long id){
        Study study = studyRepository.findById(id).orElseThrow(StudyNotFoundException::new);
        return study.result();
    }

    @Transactional
    public StudyResponse create(PostStudyRequest req, User user) {
        Study study = studyRepository.save(req.toEntity());

        // TODO : 현재 Stack 값은 TeckStack에 존재함, 그렇다면 유저에 Stack 값을 어떻게 가져올것인지?
        StudyUser studyUser = StudyUser.builder()
                .study(study)
                .user(user)
                .studyLeader(true)
                .status(Status.ACTIVE)
                .build();

        studyUserRepository.save(studyUser);

        return study.result();
    }

    @Transactional
    public StudyResponse update(PutStudyRequest req, Long id){
        Study study = studyRepository.findById(id).orElseThrow(StudyNotFoundException::new);

        return study.update(req);
    }

    @Transactional
    public Response delete(Long id) {
        Study study = studyRepository.findById(id).orElseThrow(StudyNotFoundException::new);
        study.inActive();
        study.getStudyUsers().forEach(studyUser -> studyUser.inActive());

        return Response.success();
    }
}
