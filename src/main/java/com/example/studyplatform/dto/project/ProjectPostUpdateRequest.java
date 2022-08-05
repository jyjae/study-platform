package com.example.studyplatform.dto.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectPostUpdateRequest{
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

    private Boolean isFinish;

    private List<ProjectOrganizationCreateDto> addOrganizations;

    private List<Long> deleteOrganizationIds;
}
