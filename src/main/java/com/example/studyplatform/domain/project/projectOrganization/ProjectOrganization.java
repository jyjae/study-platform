package com.example.studyplatform.domain.project.projectOrganization;

import com.example.studyplatform.constant.CareerStatus;
import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.BaseTimeEntity;
import com.example.studyplatform.domain.techStack.TechStack;
import com.example.studyplatform.exception.ProjectOrganizationDecreaseZeroException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ProjectOrganization extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO : 경력 조건 유무?

    @ManyToOne(fetch = FetchType.EAGER)
    private TechStack techStack;

    // 모집 마감 유무
    private Boolean isFinish;

    private int personnel;

    @Enumerated(EnumType.STRING)
    private CareerStatus careerStatus;

    private Status status;

    public void inActive() {
        this.status = Status.INACTIVE;
    }

    public void finished() {
        this.isFinish = true;
    }

    public void unFinished() {
        this.isFinish = false;
    }

    public void increase() {
        this.personnel++;
        unFinished();
    }

    public void decrease() {
        if(this.personnel > 0){
            this.personnel--;

            if(this.personnel == 0)
                finished();
        }else{
            throw new ProjectOrganizationDecreaseZeroException();
        }
    }

    @Builder
    public ProjectOrganization(TechStack techStack, CareerStatus careerStatus, int personnel) {
        this.techStack = techStack;
        this.personnel = personnel;
        this.careerStatus = careerStatus;
        this.isFinish = false;
        this.status = Status.ACTIVE;
    }
}
