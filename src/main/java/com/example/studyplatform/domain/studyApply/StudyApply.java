package com.example.studyplatform.domain.studyApply;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.constant.studyApply.ApplyStatus;
import com.example.studyplatform.domain.studyBoard.StudyBoard;
import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.studyApply.PutStudyApplyRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class StudyApply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private StudyBoard studyBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Enumerated(EnumType.STRING)
    private ApplyStatus applyStatus;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder
    public StudyApply(
            StudyBoard studyBoard,
            User user
    ) {
        this.studyBoard = studyBoard;
        this.user = user;
        this.applyStatus = ApplyStatus.WAITING;
        this.status = Status.ACTIVE;
    }

    public StudyApply inActive() {
        this.status = Status.INACTIVE;
        return this;
    }

    public StudyApply updateEntity(PutStudyApplyRequest req) {
        if(req.getApplyStatus() != null) this.applyStatus = req.getApplyStatus();
        return this;
    }
}
