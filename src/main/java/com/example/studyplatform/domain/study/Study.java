package com.example.studyplatform.domain.study;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.BaseTimeEntity;
import com.example.studyplatform.domain.studyNotice.StudyNotice;
import com.example.studyplatform.domain.studyTarget.StudyTarget;
import com.example.studyplatform.domain.studyUser.StudyUser;
import com.example.studyplatform.dto.study.PutStudyRequest;
import com.example.studyplatform.dto.study.StudyResponse;
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

    public StudyResponse result(){
        return StudyResponse.of(
                this.id,
                this.studyName,
                this.endedAt,
                this.status);
    }

    public StudyResponse update(PutStudyRequest req){
        this.studyName = req.getStudyName();
        this.endedAt = req.getEndedAt();
        this.status = req.getStatus();

        return StudyResponse.of(id, studyName, endedAt, status);
    }

    public void inActive() {
        this.status = Status.INACTIVE;
    }

    public void active() {
        this.status = Status.ACTIVE;
    }
}

