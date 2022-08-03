package com.example.studyplatform.dto.project;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.project.projectOrganization.ProjectOrganization;
import com.example.studyplatform.domain.project.projectPost.ProjectPost;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectPostDto {
    private Long id;

    private String title;

    private String content;

    private Status status;

    private Boolean isMike;

    private Boolean isCamera;

    private Boolean isFinish;

    private Boolean isOnline;

    private LocalDateTime recruitStartedAt;

    private LocalDateTime recruitEndedAt;

    private LocalDateTime projectStartedAt;

    private LocalDateTime projectEndedAt;

    // 추후 DTO로 수정
    private List<ProjectOrganization> organizations;

    public static ProjectPostDto toDto(ProjectPost projectPost) {
        return new ProjectPostDto(
                projectPost.getId(),
                projectPost.getTitle(),
                projectPost.getContent(),
                projectPost.getStatus(),
                projectPost.getIsMike(),
                projectPost.getIsCamera(),
                projectPost.getIsFinish(),
                projectPost.getIsOnline(),
                projectPost.getRecruitStartedAt(),
                projectPost.getRecruitEndedAt(),
                projectPost.getProjectStartedAt(),
                projectPost.getProjectEndedAt(),
                projectPost.getOrganizations()
        );
    }
}
