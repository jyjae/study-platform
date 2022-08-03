package com.example.studyplatform.domain.studyTechStack;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.BaseTimeEntity;
import com.example.studyplatform.domain.study.Study;
import com.example.studyplatform.domain.studyBoard.StudyBoard;
import com.example.studyplatform.domain.techStack.TechStack;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class StudyTechStack extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private StudyBoard studyBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    private TechStack techStack;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder
    public StudyTechStack(
            StudyBoard studyBoard,
            TechStack techStack
    ) {
        this.studyBoard = studyBoard;
        this.techStack = techStack;
        this.status = Status.ACTIVE;
    }

    public StudyTechStack inActive() {
        this.status = Status.INACTIVE;
        return this;
    }

    public static StudyTechStack of(
            StudyBoard studyBoard,
            TechStack techStack
    ) {
        return new StudyTechStack(studyBoard, techStack);
    }
}
