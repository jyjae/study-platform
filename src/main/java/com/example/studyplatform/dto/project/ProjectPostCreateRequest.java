package com.example.studyplatform.dto.project;

import com.example.studyplatform.domain.project.projectPost.ProjectPost;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectPostCreateRequest {
    private String title;

    private String content;

    private Boolean isMike;

    private Boolean isCamera;

    private Boolean isDeadLine;

    private Boolean isOnline;

    private LocalDateTime recruitStartedAt;

    private LocalDateTime recruitEndedAt;

    private LocalDateTime projectStartedAt;

    private LocalDateTime projectEndedAt;

    private List<ProjectOrganizationCreateDto> organizations;

    public static ProjectPost toEntity(ProjectPostCreateRequest req) {
        return ProjectPost.builder().title(req.title)
                .content(req.content)
                .isCamera(req.isCamera)
                .isMike(req.isMike)
                .isOnline(req.isOnline)
                .projectEndedAt(req.projectEndedAt)
                .projectStartedAt(req.projectStartedAt)
                .recruitEndedAt(req.recruitEndedAt)
                .recruitStartedAt(req.recruitStartedAt)
                .build();
    }
}
