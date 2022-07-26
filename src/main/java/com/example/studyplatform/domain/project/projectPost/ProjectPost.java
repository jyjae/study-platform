package com.example.studyplatform.domain.project.projectPost;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.board.Board;
import com.example.studyplatform.domain.project.projectOrganization.ProjectOrganization;
import com.example.studyplatform.domain.project.projectResume.ProjectResume;
import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.project.ProjectPostUpdateRequest;
import com.example.studyplatform.exception.ProjectOrganizationNotFoundException;
import lombok.AccessLevel;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("PROJECT")
@Entity
public class ProjectPost extends Board {

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ProjectOrganization> organizations = new ArrayList<>();

    // 확정된 신청서
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ProjectResume> projectResumes = new ArrayList<>();

    @Override
    public String toString() {
        return "ProjectPost{" +
                "organizations=" + organizations +
                ", projectResumes=" + projectResumes +
                '}';
    }

    public void update(ProjectPostUpdateRequest req) {
        this.title = req.getTitle();
        this.content = req.getContent();
        this.isMike = req.getIsMike();
        this.isCamera = req.getIsCamera();
        this.isFinish = req.getIsFinish();
        this.isOnline = req.getIsOnline();
        this.recruitStartedAt = req.getRecruitStartedAt();
        this.recruitEndedAt = req.getRecruitEndedAt();
        this.endedAt = req.getProjectEndedAt();
        this.startedAt = req.getProjectStartedAt();
    }

    public void inActive() {
        this.status = Status.INACTIVE;
    }

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
        if (this.organizations.contains(organization)) {
            this.organizations.remove(organization);
        }else{
            throw new ProjectOrganizationNotFoundException();
        }
    }

    @Builder
    public ProjectPost(User user, String title, String content, Boolean isCamera, Boolean isMike,
                       Boolean isOnline, LocalDateTime recruitStartedAt, String metropolitanCity, String city,
                       LocalDateTime recruitEndedAt, LocalDateTime projectStartedAt, LocalDateTime projectEndedAt) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.isCamera = isCamera;
        this.isMike = isMike;
        this.isOnline = isOnline;
        this.recruitStartedAt = recruitStartedAt;
        this.recruitEndedAt = recruitEndedAt;
        this.startedAt = projectStartedAt;
        this.endedAt = projectEndedAt;
        this.status = Status.ACTIVE;
        this.isFinish = false;
        this.metropolitanCity = metropolitanCity;
        this.city = city;
    }
}

