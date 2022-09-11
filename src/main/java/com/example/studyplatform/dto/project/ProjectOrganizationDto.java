package com.example.studyplatform.dto.project;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.constant.CareerStatus;
import com.example.studyplatform.domain.project.projectOrganization.ProjectOrganization;
import com.example.studyplatform.dto.techStack.TechStackDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "프로젝트 모집 응답 DTO")
public class ProjectOrganizationDto {
    private Long id;

    private TechStackDto techStackDto;

    private Boolean isFinish;

    private CareerStatus careerStatus;

    private Status status;

    private int personnel;

    public static ProjectOrganizationDto toDto(ProjectOrganization projectOrganization, TechStackDto techStackDto) {
        return new ProjectOrganizationDto(
                projectOrganization.getId(),
                techStackDto,
                projectOrganization.getIsFinish(),
                projectOrganization.getCareerStatus(),
                projectOrganization.getStatus(),
                projectOrganization.getPersonnel()
        );
    }
}
