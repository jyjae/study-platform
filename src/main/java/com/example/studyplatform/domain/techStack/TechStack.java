package com.example.studyplatform.domain.techStack;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class TechStack extends BaseTimeEntity {
    @Id
    private Long id;

    private String techName;

    private String techImg;

    private Stack stack;

    private Status status;
}
