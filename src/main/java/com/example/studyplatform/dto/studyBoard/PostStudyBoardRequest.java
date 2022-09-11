package com.example.studyplatform.dto.studyBoard;

import com.example.studyplatform.constant.CareerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostStudyBoardRequest {
    @NotBlank(message = "제목을 입력해주세요")
    private String title;

    @NotBlank(message = "내용을 입력해주세요")
    private String content;

    private Integer userCnt;
    private Boolean isOnline;
    private Boolean isCamara;
    private Boolean isMic;
    private Boolean isDead;
    private CareerStatus careerStatus;
    private LocalDateTime recruitStartAt;
    private LocalDateTime recruitEndAt;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private List<Long> techStackIds;

}
