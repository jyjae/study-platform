package com.example.studyplatform.domain.project.projectPost;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.BaseTimeEntity;
import com.example.studyplatform.domain.project.projectOrganization.ProjectOrganization;
import lombok.AccessLevel;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ProjectPost extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String title;

    String content;

    // to do : 광역 시군구 Open API로 받을지?

    Status status;

    Boolean isMike;

    Boolean isCamera;

    Boolean isDeadLine;

    Boolean isOnline;

    LocalDateTime recruitStartedAt;

    LocalDateTime recruitEndedAt;

    LocalDateTime projectStartedAt;

    LocalDateTime projectEndedAt;

    @OneToMany(fetch = FetchType.LAZY)
    List<ProjectOrganization> organizations;

    public void addOrganization(ProjectOrganization organization) {
        this.organizations.add(organization);
    }

    public void deleteOrganization(ProjectOrganization organization) {
        this.organizations.remove(organization);
    }

    @Builder
    public ProjectPost(String title, String content, Boolean isCamera, Boolean isMike,
                       Boolean isDeadLine, Boolean isOnline, LocalDateTime recruitStartedAt,
                       LocalDateTime recruitEndedAt, LocalDateTime projectStartedAt, LocalDateTime projectEndedAt) {
        this.title = title;
        this.content = content;
        this.isCamera = isCamera;
        this.isMike = isMike;
        this.isDeadLine = isDeadLine;
        this.isOnline = isOnline;
        this.recruitStartedAt = recruitStartedAt;
        this.recruitEndedAt = recruitEndedAt;
        this.projectStartedAt = projectStartedAt;
        this.projectEndedAt = projectEndedAt;
        this.status = Status.ACTIVE;
    }
}

