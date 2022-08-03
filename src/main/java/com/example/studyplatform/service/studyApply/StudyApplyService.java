package com.example.studyplatform.service.studyApply;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.studyApply.StudyApply;
import com.example.studyplatform.domain.studyApply.StudyApplyRepository;
import com.example.studyplatform.domain.studyBoard.StudyBoard;
import com.example.studyplatform.domain.studyBoard.StudyBoardRepository;
import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.studyApply.PostStudyApplyRequest;
import com.example.studyplatform.dto.studyApply.PutStudyApplyRequest;
import com.example.studyplatform.dto.studyApply.StudyApplyResponse;
import com.example.studyplatform.dto.studyBoard.StudyBoardResponse;
import com.example.studyplatform.exception.StudyApplyNotFoundException;
import com.example.studyplatform.exception.StudyBoardNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class StudyApplyService {
    private final StudyApplyRepository studyApplyRepository;
    private final StudyBoardRepository studyBoardRepository;

    public StudyApplyResponse applyStudy(PostStudyApplyRequest req, User user) {
        // 1. 스터디 게시글 가져오기
        StudyBoard savedStudyBoard = getStudyBoard(req.getStudyBoardId());

        // 2. 스터디 신청 생성
        StudyApply studyApply = StudyApply.builder()
                .studyBoard(savedStudyBoard)
                .user(user)
                .build();

        // 3. 스터디 신청 저장
        StudyApply savedStudyApply = studyApplyRepository.save(studyApply);

        return StudyApplyResponse.of(
                savedStudyApply.getId(),
                savedStudyApply.getStudyBoard().getId(),
                savedStudyApply.getApplyStatus().name());
    }

    public StudyApplyResponse modifyApplyStatus(PutStudyApplyRequest req, User user) {
        // 1. 스터디 신청 가져오기
        StudyApply savedStudyApply = getStudyApplyById(req.getApplyId());

        // 2. 스터디 신청 상태(ApplyStatus) 변경 및 저장
        studyApplyRepository.save(savedStudyApply.updateEntity(req));

        return StudyApplyResponse.of(
                savedStudyApply.getId(),
                savedStudyApply.getStudyBoard().getId(),
                savedStudyApply.getApplyStatus().name());
    }

    public void deleteApply(Long applyId, User user) {
        // 1. 신청 아이디와 유저 정보로 스터디 신청 가져오기
        StudyApply savedStudyApply = getStudyApplyByIdAndUserId(applyId, user.getId());

        // 2. 스터디 신청 삭제
        studyApplyRepository.save(savedStudyApply.inActive());
    }

    private StudyBoard getStudyBoard(UUID studyBoardId) {
        return studyBoardRepository.findByIdAndStatus(studyBoardId, Status.ACTIVE)
                .orElseThrow(StudyBoardNotFoundException::new);
    }

    private StudyApply getStudyApplyById(Long applyId) {
        return studyApplyRepository.findByIdAndStatus(applyId, Status.ACTIVE)
                .orElseThrow(StudyApplyNotFoundException::new);
    }


    private StudyApply getStudyApplyByIdAndUserId(Long applyId, Long userId) {
        return studyApplyRepository.findByIdAndUserIdAndStatus(applyId, userId, Status.ACTIVE)
                .orElseThrow(StudyApplyNotFoundException::new);
    }


}
