package com.example.studyplatform.dto.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResumeDeleteRequest {
    private UUID projectPostId;
    private Long resumeId;
}
