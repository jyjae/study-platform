package com.example.studyplatform.dto.studyTarget;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostStudyTargetRequest {
    private String studyTargetTitle;
    private int studyTargetPriority;
}
