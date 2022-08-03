package com.example.studyplatform.dto.studyApply;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyApplyResponse {
    private Long studyApplyId;
    private UUID studyBoardId;
    private String applyStatus;

    public static StudyApplyResponse of(
            Long studyApplyId,
            UUID studyBoardId,
            String applyStatus
    ) {
        return new StudyApplyResponse(
                studyApplyId,
                studyBoardId,
                applyStatus
        );
    }
}
