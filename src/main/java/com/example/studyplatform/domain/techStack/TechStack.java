package com.example.studyplatform.domain.techStack;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class TechStack extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String techName;

    private String techImg;

    private Stack stack;

    private Status status;

    public void inActive() {
        this.status = Status.INACTIVE;
    }

    public void active() {
        this.status = Status.ACTIVE;
    }

    @Builder
    public TechStack(String techName, String techImg, Stack stack) {
        this.techName = techName;
        this.techImg = techImg;
        this.stack = stack;
        this.status = Status.ACTIVE;
    }
}
