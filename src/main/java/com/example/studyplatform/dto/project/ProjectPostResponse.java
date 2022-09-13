package com.example.studyplatform.dto.project;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.project.projectPost.ProjectPost;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "프로젝트 게시물 응답 DTO")
public class ProjectPostResponse {
    private Long id;

    private Long userId;

    private String nickname;

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

    private List<ProjectOrganizationDto> organizations;

    public static ProjectPostResponse toDto(ProjectPost projectPost, List<ProjectOrganizationDto> dtos) {
        return new ProjectPostResponse(
                projectPost.getId(),
                projectPost.getUser().getId(),
                projectPost.getUser().getNickname(),
                projectPost.getTitle(),
                projectPost.getContent(),
                projectPost.getStatus(),
                projectPost.getIsMike(),
                projectPost.getIsCamera(),
                projectPost.getIsFinish(),
                projectPost.getIsOnline(),
                projectPost.getRecruitStartedAt(),
                projectPost.getRecruitEndedAt(),
                projectPost.getStartedAt(),
                projectPost.getEndedAt(),
                dtos
        );
    }
}
