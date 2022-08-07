package com.example.studyplatform.dto.project;

import com.example.studyplatform.domain.project.projectResume.ProjectResume;
import com.example.studyplatform.dto.techStack.TechStackDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResumeResponse {
    private Long id;

    private String nickname;

    private ProjectOrganizationDto organizationDto;

    public static ProjectResumeResponse toDto(ProjectResume projectResume) {
        return new ProjectResumeResponse(
                projectResume.getId(),
                projectResume.getUser().getNickname(),
                ProjectOrganizationDto.toDto(projectResume.getOrganization(),
                        TechStackDto.toDto(projectResume.getOrganization().getTechStack()))
        );
    }
}
