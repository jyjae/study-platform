package com.example.studyplatform.domain.studyTarget;

import com.example.studyplatform.domain.BaseTimeEntity;
import com.example.studyplatform.domain.study.Study;
import com.example.studyplatform.dto.studyTarget.PutStudyTargetRequest;
import com.example.studyplatform.dto.studyTarget.StudyTargetResponse;
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

    private int studyTargetPriority;

    private boolean isStudyTargetSuccess;

    @ManyToOne(fetch = FetchType.LAZY)
    private Study study;

    @Builder
    public StudyTarget(String studyTargetTitle, int studyTargetPriority, Study study) {
        this.study = study;
        this.studyTargetTitle = studyTargetTitle;
        this.studyTargetPriority = studyTargetPriority;
        this.isStudyTargetSuccess = false;
    }

    public StudyTargetResponse result(){
        return StudyTargetResponse.of(id, studyTargetTitle, studyTargetPriority, isStudyTargetSuccess);
    }

    public StudyTargetResponse update(PutStudyTargetRequest req){
        this.studyTargetTitle = req.getStudyTargetTitle();
        this.studyTargetPriority = req.getStudyTargetPriority();
        return StudyTargetResponse.of(id, studyTargetTitle, studyTargetPriority, isStudyTargetSuccess);
    }

}
