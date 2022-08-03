package com.example.studyplatform.dto.project;

import com.example.studyplatform.domain.project.projectOrganization.ProjectOrganization;
import com.example.studyplatform.domain.project.projectResume.ProjectResume;
import com.example.studyplatform.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResumeCreateRequest {
    private Long organizationId;

    public static ProjectResume toEntity(User user, ProjectOrganization organization) {
        return ProjectResume.builder().user(user).organization(organization).build();
    }
}
