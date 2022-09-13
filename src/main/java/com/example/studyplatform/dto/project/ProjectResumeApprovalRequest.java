package com.example.studyplatform.dto.project;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "프로젝트 신청 수락 DTO")
public class ProjectResumeApprovalRequest {
    private Long projectPostId;
}
