package com.example.studyplatform.domain.career;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.BaseTimeEntity;
import com.example.studyplatform.domain.techStack.TechStack;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Career extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int month;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch=FetchType.EAGER)
    private TechStack techStack;

    public void inActive() {
        this.status = Status.INACTIVE;
    }

    public void active() {
        this.status = Status.ACTIVE;
    }

    @Builder
    public Career(int month, TechStack techStack) {
        this.month = month;
        this.techStack = techStack;
        this.status = Status.ACTIVE;
    }
}
