package com.example.studyplatform.factory.entity;

import com.example.studyplatform.domain.techStack.Stack;
import com.example.studyplatform.domain.techStack.TechStack;

public class TechStackFactory {
    public static TechStack createTechStack() {
        return TechStack.builder().techName("techName").techImg("techImg").stack(Stack.BACKEND).build();
    }

    public static TechStack createTechStack(String techName) {
        return TechStack.builder().techName(techName).techImg("techImg").stack(Stack.BACKEND).build();
    }
}
