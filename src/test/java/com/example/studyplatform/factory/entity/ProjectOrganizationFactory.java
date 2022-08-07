package com.example.studyplatform.factory.entity;

import com.example.studyplatform.constant.CareerStatus;
import com.example.studyplatform.domain.project.projectOrganization.ProjectOrganization;
import com.example.studyplatform.domain.techStack.TechStack;

public class ProjectOrganizationFactory {
    public static ProjectOrganization createProjectOrganization(TechStack techStack, CareerStatus careerStatus, int personnel) {
        return ProjectOrganization.builder()
                .techStack(techStack)
                .careerStatus(careerStatus)
                .personnel(personnel)
                .build();
    }
}
