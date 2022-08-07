package com.example.studyplatform.dto.project;

import com.example.studyplatform.constant.CareerStatus;
import com.example.studyplatform.domain.project.projectOrganization.ProjectOrganization;
import com.example.studyplatform.domain.techStack.TechStack;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectOrganizationCreateDto {
    private Long techId;

    private CareerStatus careerStatus;

    private int personnel;

    public static ProjectOrganization toEntity(ProjectOrganizationCreateDto dto, TechStack techStack) {
        return ProjectOrganization.builder()
                .techStack(techStack)
                .careerStatus(dto.careerStatus)
                .personnel(dto.personnel).build();
    }
}
