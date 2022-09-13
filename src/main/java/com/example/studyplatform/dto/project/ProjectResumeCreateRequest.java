package com.example.studyplatform.dto.project;

import com.example.studyplatform.domain.project.projectOrganization.ProjectOrganization;
import com.example.studyplatform.domain.project.projectResume.ProjectResume;
import com.example.studyplatform.domain.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "프로젝트 신청 생성 DTO")
public class ProjectResumeCreateRequest {
    @Schema(description = "프로젝트 모집 ID")
    private Long organizationId;

    public static ProjectResume toEntity(User user, ProjectOrganization organization) {
        return ProjectResume.builder().user(user).organization(organization).build();
    }
}
