package com.example.studyplatform.domain.studyUser;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.BaseTimeEntity;
import com.example.studyplatform.domain.study.Study;
import com.example.studyplatform.domain.user.Role;
import com.example.studyplatform.domain.user.User;
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
    private Role role;

    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    private Study study;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public StudyUser(boolean studyLeader, Role role, Status status) {
        this.studyLeader = studyLeader;
        this.role = role;
        this.status = status;
    }
}
