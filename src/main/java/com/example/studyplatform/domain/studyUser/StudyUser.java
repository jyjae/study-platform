package com.example.studyplatform.domain.studyUser;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.BaseTimeEntity;
import com.example.studyplatform.domain.study.Study;
import com.example.studyplatform.domain.techStack.Stack;
import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.studyUser.StudyUserResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class StudyUser extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean studyLeader;

    @Enumerated(EnumType.STRING)
    private Stack stack;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    private Study study;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public StudyUser(Study study, User user, Stack stack, Status status, boolean studyLeader) {
        this.study = study;
        this.user = user;
        this.stack = stack;
        this.studyLeader = studyLeader;
        this.status = status;
    }

    public void inActive() {
        this.status = Status.INACTIVE;
    }

    public void active() {
        this.status = Status.ACTIVE;
    }

    public StudyUserResponse result(){
        return StudyUserResponse.of(
                this.id,
                this.user.getId(),
                this.study.getId(),
                this.stack,
                this.status,
                getCreatedAt(),
                getUpdatedAt()
        );
    }
}
