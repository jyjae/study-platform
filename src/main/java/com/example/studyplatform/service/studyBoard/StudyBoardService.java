package com.example.studyplatform.service.studyBoard;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.studyBoard.StudyBoard;
import com.example.studyplatform.domain.studyBoard.StudyBoardRepository;
import com.example.studyplatform.domain.studyTechStack.StudyTechStack;
import com.example.studyplatform.domain.studyTechStack.StudyTechStackRepository;
import com.example.studyplatform.domain.techStack.TechStack;
import com.example.studyplatform.domain.techStack.TechStackRepository;
import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.studyBoard.PostStudyBoardRequest;
import com.example.studyplatform.dto.studyBoard.PutStudyBoardRequest;
import com.example.studyplatform.dto.studyBoard.StudyBoardResponse;
import com.example.studyplatform.dto.studyTechStack.StudyTechStackDto;
import com.example.studyplatform.exception.StudyBoardNotFoundException;
import com.example.studyplatform.exception.StudyTechStackNotFoundException;
import com.example.studyplatform.exception.TechStackNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyBoardService {
    private final StudyBoardRepository studyBoardRepository;
    private final TechStackRepository techStackRepository;
    private final StudyTechStackRepository studyTechStackRepository;

    @Transactional(rollbackFor = Exception.class)
    public StudyBoardResponse createStudyBoard(PostStudyBoardRequest req, User user) {
        // 1. 스터디 게시글 생성
        StudyBoard studyBoard = StudyBoard.builder()
                .title(req.getTitle())
                .content(req.getContent())
                .userCnt(req.getUserCnt())
                .isOnline(req.getIsOnline())
                .isCamara(req.getIsCamara())
                .isMic(req.getIsMic())
                .isDead(req.getIsDead())
                .recruitStartAt(req.getRecruitStartAt())
                .recruitEndAt(req.getRecruitEndAt())
                .startAt(req.getStartAt())
                .endAt(req.getEndAt())
                .user(user)
                .build();

        // 2. 스터디 게시글 저장
        StudyBoard savedStudyBoard = studyBoardRepository.save(studyBoard);

        // 3. 스터디 기술 스택 추가 및 저장
        List<StudyTechStack> savedStudyTechStacks =  addStudyTechStack(req, savedStudyBoard);

        return StudyBoardResponse.of(savedStudyBoard, createStudyTechStackDto(savedStudyTechStacks));

    }

    @Transactional(rollbackFor = Exception.class)
    public StudyBoardResponse modifyStudyBoard(PutStudyBoardRequest req, User user) {
        // 1. 기존 스터디 게시글 가져오기 및 변경
        StudyBoard modifyStudyBoard = studyBoardRepository.save(getStudyBoardByIdAndUser(req.getStudyBoardId(), user).updateEntity(req));

        // 2. 삭제요청 들어온 스터디 기술 스택 삭제
        removeStudyTechStack(req, modifyStudyBoard);

        // 3. 스터디 기술 스택 추가 및 저장
        List<StudyTechStack> savedStudyTechStacks =  addStudyTechStack(req, modifyStudyBoard);

        return StudyBoardResponse.of(modifyStudyBoard, createStudyTechStackDto(savedStudyTechStacks));
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteStudyBoard(Long studyBoardId, User user) {
        // 1. 기존 스터디 게시글 가져오기 및 status 변경
        StudyBoard modifyStudyBoard = studyBoardRepository
                .save(getStudyBoardByIdAndUser(studyBoardId, user).inActive());

        // 2. 관련 스터디 기술 스택 가져오기 및 status 변경
        List<StudyTechStack> savedStudyTechStacks = getStudyTechStack(studyBoardId);

        if(savedStudyTechStacks != null && !savedStudyTechStacks.isEmpty()) {
            savedStudyTechStacks.stream().map(i -> i.inActive())
                    .collect(Collectors.toList());
        }

    }

    private List<StudyTechStack> getStudyTechStack(Long studyBoardId) {
        return studyTechStackRepository.findAllByStudyBoardIdAndStatus(studyBoardId, Status.ACTIVE);
    }

    private List<StudyTechStack> addStudyTechStack(PostStudyBoardRequest req, StudyBoard savedStudyBoard) {
        List<StudyTechStack> savedStudyTechStacks = null;

        if(req.getTechStackIds()!=null && !req.getTechStackIds().isEmpty()
        ) {
            // 4. 기술 스택 가져오기
            List<TechStack> techStacks = toTechStackList(req.getTechStackIds());

            // 5. 스터디 기술 스택 매핑 엔티티 저장
            savedStudyTechStacks = studyTechStackRepository
                    .saveAll(createStudyTechStacks(savedStudyBoard, techStacks));

            // 7. 스터디 게시글 기술 스택 추가
            savedStudyTechStacks.forEach(savedStudyBoard::addStudyTechStack);

        }
        return savedStudyTechStacks;
    }


    private void removeStudyTechStack(PutStudyBoardRequest req, StudyBoard savedStudyBoard) {
        if(req.getDeleteStudyTechStackIds() != null && !req.getDeleteStudyTechStackIds().isEmpty()) {
            List<StudyTechStack> techStacks = toStudyTechStackList(req.getDeleteStudyTechStackIds());

            // 4. studyTechStack status INACTIVE로 변경 및 studyBoard에서 제거
            studyTechStackRepository.saveAll(
                    techStacks.stream().map(StudyTechStack::inActive)
                            .collect(Collectors.toList())).forEach(savedStudyBoard::removeStudyTechStack);

        }
    }

    private List<StudyTechStack> toStudyTechStackList(List<Long> studyTechStackIds) {
        return studyTechStackIds.stream()
                .map(i -> studyTechStackRepository.findByIdAndStatus(i, Status.ACTIVE)
                        .orElseThrow(StudyTechStackNotFoundException::new))
                .collect(Collectors.toList());
    }

    private StudyBoard getStudyBoardByIdAndUser(Long id, User user) {
        return studyBoardRepository
                .findByIdAndUserIdAndStatus(id, user.getId(), Status.ACTIVE)
                .orElseThrow(StudyBoardNotFoundException::new);
    }

    private List<StudyTechStackDto> createStudyTechStackDto(List<StudyTechStack> savedStudyTechStacks) {
        if(savedStudyTechStacks == null) {
            return List.of();
        }

        return  savedStudyTechStacks.stream()
                .map(i -> StudyTechStackDto.of(i))
                .collect(Collectors.toList());
    }

    private List<StudyTechStack> createStudyTechStacks(
            StudyBoard studyBoard,
            List<TechStack> techStacks) {
        return techStacks.stream()
                .map(i -> StudyTechStack.of(studyBoard, i))
                .collect(Collectors.toList());
    }

    private List<TechStack> toTechStackList(List<Long> techStackIds) {
        return techStackIds.stream()
                .map(i -> techStackRepository.findByIdAndStatus(i, Status.ACTIVE)
                        .orElseThrow(TechStackNotFoundException::new))
                .collect(Collectors.toList());
    }



}
