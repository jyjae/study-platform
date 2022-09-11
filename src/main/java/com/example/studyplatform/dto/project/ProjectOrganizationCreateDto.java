package com.example.studyplatform.dto.project;

import com.example.studyplatform.constant.CareerStatus;
import com.example.studyplatform.domain.project.projectOrganization.ProjectOrganization;
import com.example.studyplatform.domain.techStack.TechStack;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "프로젝트 모집 생성 DTO")
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
