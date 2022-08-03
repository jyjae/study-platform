package com.example.studyplatform.dto.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResumeRegisterRequest {
    private Long projectPostId;
    private Long resumeId;
}
