package com.example.studyplatform.dto.studyBoard;

import com.example.studyplatform.domain.studyBoard.StudyBoard;
import com.example.studyplatform.dto.studyTechStack.StudyTechStackDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyBoardResponse {
    private UUID studyBoardId;
    private Long userId;
    private String nickname;
    private String title;
    private String content;
    private Integer userCnt;
    private Boolean isOnline;
    private Boolean isCamara;
    private Boolean isMic;
    private Boolean isDead;
    private LocalDateTime recruitStartAt;
    private LocalDateTime recruitEndAt;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private List<StudyTechStackDto> stackDtos;

    public static StudyBoardResponse of(StudyBoard entity,
                                        List<StudyTechStackDto> dtos) {
        return new StudyBoardResponse(
                entity.getId(),
                entity.getUser().getId(),
                entity.getUser().getNickname(),
                entity.getTitle(),
                entity.getContent(),
                entity.getUserCnt(),
                entity.getIsOnline(),
                entity.getIsCamara(),
                entity.getIsMic(),
                entity.getIsDead(),
                entity.getRecruitStartAt(),
                entity.getRecruitEndAt(),
                entity.getStartAt(),
                entity.getEndAt(),
                dtos);
    }


}
