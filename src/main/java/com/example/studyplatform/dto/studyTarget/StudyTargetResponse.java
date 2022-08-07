package com.example.studyplatform.dto.studyTarget;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyTargetResponse {
    private Long id;
    private String studyTargetTitle;
    private int studyTargetPriority;

    public static StudyTargetResponse of(
            Long id,
            String studyTargetTitle,
            int studyTargetPriority
    ) {
        return new StudyTargetResponse(
                id,
                studyTargetTitle,
                studyTargetPriority
        );
    }
}
