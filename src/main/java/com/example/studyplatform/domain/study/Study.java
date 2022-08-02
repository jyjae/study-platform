package com.example.studyplatform.domain.study;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.BaseTimeEntity;
import com.example.studyplatform.domain.studyNotice.StudyNotice;
import com.example.studyplatform.domain.studyTarget.StudyTarget;
import com.example.studyplatform.domain.studyUser.StudyUser;
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
@Entity
public class Study extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studyName;

    private LocalDateTime endedAt;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<StudyTarget> studyTargets = new ArrayList<>();

    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<StudyNotice> studyNotices = new ArrayList<>();

    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<StudyUser> studyUsers = new ArrayList<>();

    @Builder
    public Study(String studyName, LocalDateTime endedAt, Status status) {
        this.studyName = studyName;
        this.endedAt = endedAt;
        this.status = status;
    }
}

