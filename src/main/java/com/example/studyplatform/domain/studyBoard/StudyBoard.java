package com.example.studyplatform.domain.studyBoard;

import com.example.studyplatform.constant.CareerStatus;
import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.BaseTimeEntity;
import com.example.studyplatform.domain.board.Board;
import com.example.studyplatform.domain.studyApply.StudyApply;
import com.example.studyplatform.domain.studyTechStack.StudyTechStack;
import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.studyBoard.PutStudyBoardRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("STUDY")
@Entity
public class StudyBoard extends Board {

    private Integer userCnt;

    private CareerStatus careerStatus;

    @OneToMany(mappedBy = "studyBoard", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StudyTechStack> studyTechStacks = new ArrayList<>();

    // 스터디 신청
    @OneToMany(mappedBy = "studyBoard", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StudyApply> studyApplies = new ArrayList<>();

    @Override
    public String toString() {
        return "StudyBoard{" +
                "userCnt=" + userCnt +
                ", careerStatus=" + careerStatus +
                ", studyTechStacks=" + studyTechStacks +
                ", studyApplies=" + studyApplies +
                '}';
    }

    @Builder
    public StudyBoard(
            String title,
            String content,
            Integer userCnt,
            Boolean isOnline,
            Boolean isCamara,
            Boolean isMic,
            Boolean isDead,
            LocalDateTime recruitStartAt,
            LocalDateTime recruitEndAt,
            LocalDateTime startAt,
            LocalDateTime endAt,
            User user,
            CareerStatus careerStatus,
            String metropolitanCity,
            String city
    ){
        this.title = title;
        this.content = content;
        this.userCnt = userCnt;
        this.isOnline = isOnline;
        this.isCamera = isCamara;
        this.isMike = isMic;
        this.isFinish = isDead;
        this.recruitStartedAt = recruitStartAt;
        this.recruitEndedAt = recruitEndAt;
        this.startedAt = startAt;
        this.endedAt = endAt;
        this.user = user;
        this.metropolitanCity = metropolitanCity;
        this.city =city;
        this.careerStatus = careerStatus;
        this.status = Status.ACTIVE;
    }

    public StudyBoard inActive() {
        this.status = Status.INACTIVE;
        return this;
    }

    public void addStudyTechStack (StudyTechStack studyTechStack) {
        this.studyTechStacks.add(studyTechStack);
    }

    public void removeStudyTechStack (StudyTechStack studyTechStack) {
        this.studyTechStacks.remove(studyTechStack);
    }

    public void addStudyApply(StudyApply studyApply) {
        this.studyApplies.add(studyApply);
    }


    @PrePersist
    public void prePersist() {
        this.isOnline = this.isOnline == null ? false : this.isOnline;
        this.isCamera = this.isCamera == null ? false : this.isCamera;
        this.isMike = this.isMike == null ? false : this.isMike;
        this.isFinish = this.isFinish == null ? false : this.isFinish;
    }

    public StudyBoard updateEntity(PutStudyBoardRequest req) {
        if(req.getTitle() != null) this.title = req.getTitle();
        if(req.getContent() != null) this.content = req.getContent();
        if(req.getUserCnt() != null) this.userCnt = req.getUserCnt();
        if(req.getIsOnline() != null) this.isOnline = req.getIsOnline();
        if(req.getIsCamara() != null) this.isCamera = req.getIsCamara();
        if(req.getIsMic() != null) this.isMike = req.getIsMic();
        if(req.getIsDead() != null) this.isFinish = req.getIsDead();
        if(req.getRecruitStartAt() != null) this.recruitStartedAt = req.getRecruitStartAt();
        if(req.getRecruitEndAt() != null) this.recruitEndedAt = req.getRecruitEndAt();
        if(req.getStartAt() != null) this.startedAt = req.getStartAt();
        if(req.getEndAt() != null) this.endedAt = req.getEndAt();

        return this;
    }
}
