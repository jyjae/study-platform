package com.example.studyplatform.domain.studyTarget;

import com.example.studyplatform.domain.BaseTimeEntity;
import com.example.studyplatform.domain.study.Study;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class StudyTarget extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studyTargetTitle;

    private int studyTargetOrder;

    private boolean studyTargetSuccess;

    @ManyToOne(fetch = FetchType.LAZY)
    private Study study;

    @Builder
    public StudyTarget(String studyTargetTitle, int studyTargetOrder) {
        this.studyTargetTitle = studyTargetTitle;
        this.studyTargetOrder = studyTargetOrder;
        this.studyTargetSuccess = false;
    }
}
