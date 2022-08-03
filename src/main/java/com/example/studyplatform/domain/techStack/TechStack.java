package com.example.studyplatform.domain.techStack;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.BaseTimeEntity;
import com.example.studyplatform.domain.studyTechStack.StudyTechStack;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    // 스터디 게시글 기술 스택
    @OneToMany(mappedBy = "techStack", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StudyTechStack> studyTechStacks = new ArrayList<>();

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
