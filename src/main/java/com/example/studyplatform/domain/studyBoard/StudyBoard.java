package com.example.studyplatform.domain.studyBoard;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.BaseTimeEntity;
import com.example.studyplatform.domain.studyApply.StudyApply;
import com.example.studyplatform.domain.studyTechStack.StudyTechStack;
import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.studyBoard.PutStudyBoardRequest;
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
public class StudyBoard extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private Integer userCnt;

    private Boolean isOnline;

    private Boolean isCamara;

    private Boolean isMic;

    private Boolean isDead;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime recruitStartAt;

    private LocalDateTime recruitEndAt;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "studyBoard", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StudyTechStack> studyTechStacks = new ArrayList<>();

    // 스터디 신청
    @OneToMany(mappedBy = "studyBoard", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StudyApply> studyApplies = new ArrayList<>();

    // 광역시도 추가
    // 지역 추가

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
            User user
    ){
        this.title = title;
        this.content = content;
        this.userCnt = userCnt;
        this.isOnline = isOnline;
        this.isCamara = isCamara;
        this.isMic = isMic;
        this.isDead = isDead;
        this.recruitStartAt = recruitStartAt;
        this.recruitEndAt = recruitEndAt;
        this.startAt = startAt;
        this.endAt = endAt;
        this.user = user;
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
        this.isCamara = this.isCamara == null ? false : this.isCamara;
        this.isMic = this.isMic == null ? false : this.isMic;
        this.isDead = this.isDead == null ? false : this.isDead;
    }

    public StudyBoard updateEntity(PutStudyBoardRequest req) {
        if(req.getTitle() != null) this.title = req.getTitle();
        if(req.getContent() != null) this.content = req.getContent();
        if(req.getUserCnt() != null) this.userCnt = req.getUserCnt();
        if(req.getIsOnline() != null) this.isOnline = req.getIsOnline();
        if(req.getIsCamara() != null) this.isCamara = req.getIsCamara();
        if(req.getIsMic() != null) this.isMic = req.getIsMic();
        if(req.getIsDead() != null) this.isDead = req.getIsDead();
        if(req.getRecruitStartAt() != null) this.recruitStartAt = req.getRecruitStartAt();
        if(req.getRecruitEndAt() != null) this.recruitEndAt = req.getRecruitEndAt();
        if(req.getStartAt() != null) this.startAt = req.getStartAt();
        if(req.getEndAt() != null) this.endAt = req.getEndAt();

        return this;
    }
}
