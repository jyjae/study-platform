package com.example.studyplatform.factory.entity;

import com.example.studyplatform.domain.career.Career;
import com.example.studyplatform.domain.techStack.TechStack;

public class CareerFactory {
    public static Career createCareer(TechStack techStack) {
        return Career.builder().month(1).techStack(techStack).build();
    }
}
