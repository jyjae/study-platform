package com.example.studyplatform.domain.project.projectPost;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.BaseTimeEntity;
import com.example.studyplatform.domain.project.projectOrganization.ProjectOrganization;
import com.example.studyplatform.domain.project.projectResume.ProjectResume;
import lombok.AccessLevel;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ProjectPost extends BaseTimeEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type="uuid-char")
    private UUID id;

    private String title;

    private String content;

    // to do : 광역 시군구 Open API로 받을지?

    private Status status;

    private Boolean isMike;

    private Boolean isCamera;

    private Boolean isFinish;

    private Boolean isOnline;

    private LocalDateTime recruitStartedAt;

    private LocalDateTime recruitEndedAt;

    private LocalDateTime projectStartedAt;

    private LocalDateTime projectEndedAt;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ProjectOrganization> organizations = new ArrayList<>();

    // 확정된 신청서
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ProjectResume> projectResumes = new ArrayList<>();

    // 신청서 추가
    public void addProjectResume(ProjectResume projectResume) {
        this.projectResumes.add(projectResume);
    }

    // 신청서 삭제
    public void deleteProjectResume(ProjectResume projectResume) {
        this.projectResumes.remove(projectResume);
    }

    public void addOrganization(ProjectOrganization organization) {
        this.organizations.add(organization);
    }

    public void deleteOrganization(ProjectOrganization organization) {
        this.organizations.remove(organization);
    }

    @Builder
    public ProjectPost(String title, String content, Boolean isCamera, Boolean isMike,
                       Boolean isOnline, LocalDateTime recruitStartedAt,
                       LocalDateTime recruitEndedAt, LocalDateTime projectStartedAt, LocalDateTime projectEndedAt) {
        this.title = title;
        this.content = content;
        this.isCamera = isCamera;
        this.isMike = isMike;
        this.isOnline = isOnline;
        this.recruitStartedAt = recruitStartedAt;
        this.recruitEndedAt = recruitEndedAt;
        this.projectStartedAt = projectStartedAt;
        this.projectEndedAt = projectEndedAt;
        this.status = Status.ACTIVE;
        this.isFinish = false;
    }
}

