package com.example.studyplatform.dto.project;

import com.example.studyplatform.domain.project.projectPost.ProjectPost;
import com.example.studyplatform.domain.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "프로젝트 게시물 생성 DTO")
public class ProjectPostCreateRequest {

    @NotBlank(message = "프로젝트 제목을 입력해주세요")
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

    public static ProjectPost toEntity(ProjectPostCreateRequest req, User user) {
        return ProjectPost.builder()
                .user(user)
                .title(req.title)
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
