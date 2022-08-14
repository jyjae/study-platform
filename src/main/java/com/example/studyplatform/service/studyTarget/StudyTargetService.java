package com.example.studyplatform.service.studyTarget;

import com.example.studyplatform.domain.study.Study;
import com.example.studyplatform.domain.study.StudyRepository;
import com.example.studyplatform.domain.studyTarget.StudyTarget;
import com.example.studyplatform.domain.studyTarget.StudyTargetRepository;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.dto.studyTarget.PostStudyTargetRequest;
import com.example.studyplatform.dto.studyTarget.PutStudyTargetRequest;
import com.example.studyplatform.dto.studyTarget.StudyTargetResponse;
import com.example.studyplatform.exception.StudyNotFoundException;
import com.example.studyplatform.exception.StudyTargetNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class StudyTargetService {
    private final StudyRepository studyRepository;
    private final StudyTargetRepository studyTargetRepository;

    public List<StudyTargetResponse> list(Long studyId){
        List<StudyTarget> list = studyTargetRepository.findAllByStudyId(studyId);

        return list.stream().map(studyTarget -> studyTarget.result()).collect(Collectors.toList());
    }

    @Transactional
    public StudyTargetResponse create(Long studyId, PostStudyTargetRequest req) {
        Study study = studyRepository.findById(studyId).orElseThrow(StudyNotFoundException::new);

        StudyTarget studyTarget = StudyTarget.builder()
                .studyTargetTitle(req.getStudyTargetTitle())
                .studyTargetPriority(req.getStudyTargetPriority())
                .study(study)
                .build();

        return studyTargetRepository.save(studyTarget).result();
    }

    @Transactional
    public StudyTargetResponse update(Long studyTargetId, PutStudyTargetRequest req) {
        StudyTarget studyTarget = studyTargetRepository.findById(studyTargetId).orElseThrow(StudyTargetNotFoundException::new);

        return studyTarget.update(req);
    }

    @Transactional
    public Response delete(Long id) {
        StudyTarget studyTarget = studyTargetRepository.findById(id).orElseThrow(StudyTargetNotFoundException::new);
        studyTargetRepository.delete(studyTarget);
        return Response.success();
    }
}


