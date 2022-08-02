package com.example.studyplatform.domain.project.projectResume;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.BaseTimeEntity;
import com.example.studyplatform.domain.project.projectOrganization.ProjectOrganization;
import com.example.studyplatform.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ProjectResume extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProjectOrganization organization;

    private Status status;

    @Builder
    public ProjectResume(User user, ProjectOrganization organization){
        this.user = user;
        this.organization = organization;
        this.status = Status.ACTIVE;
    }

    public void inActive() {
        this.status = Status.INACTIVE;
    }
}
