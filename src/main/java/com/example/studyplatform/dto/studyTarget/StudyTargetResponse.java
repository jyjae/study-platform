package com.example.studyplatform.dto.studyTarget;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "스터디 목표 응답 DTO")
public class StudyTargetResponse {
    private Long id;
    private String studyTargetTitle;
    private int studyTargetPriority;
    private boolean isStudyTargetSuccess;

    public static StudyTargetResponse of(
            Long id,
            String studyTargetTitle,
            int studyTargetPriority,
            boolean isStudyTargetSuccess
    ) {
        return new StudyTargetResponse(
                id,
                studyTargetTitle,
                studyTargetPriority,
                isStudyTargetSuccess
        );
    }
}
