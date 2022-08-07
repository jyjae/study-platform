package com.example.studyplatform.dto.board;

import com.example.studyplatform.constant.CareerStatus;
import com.example.studyplatform.domain.studyBoard.StudyBoard;
import com.example.studyplatform.dto.studyTechStack.StudyTechStackDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BoardStudyDto extends BoardDto{
    private Integer userCnt;

    private CareerStatus careerStatus;

    private List<StudyTechStackDto> studyTechStacks;

    public BoardStudyDto(StudyBoard studyBoard) {
        this.id = studyBoard.getId();
        this.dtype = studyBoard.getDtype();
        this.nickname = studyBoard.getUser().getNickname();
        this.title = studyBoard.getTitle();
        this.isOnline = studyBoard.getIsOnline();
        this.isCamera = studyBoard.getIsCamera();
        this.isMike = studyBoard.getIsMike();
        this.isFinish = studyBoard.getIsFinish();
        this.recruitEndedAt = studyBoard.getRecruitEndedAt();
        this.recruitStartedAt = studyBoard.getRecruitStartedAt();
        this.startedAt = studyBoard.getStartedAt();
        this.endedAt = studyBoard.getEndedAt();
        this.userCnt = studyBoard.getUserCnt();
        this.careerStatus = studyBoard.getCareerStatus();
        this.studyTechStacks = studyBoard.getStudyTechStacks().stream().map(StudyTechStackDto::of)
                .collect(Collectors.toList());
    }


}
