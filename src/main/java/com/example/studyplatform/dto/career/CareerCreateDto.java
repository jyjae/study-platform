package com.example.studyplatform.dto.career;

import com.example.studyplatform.domain.career.Career;
import com.example.studyplatform.domain.techStack.TechStack;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CareerCreateDto {
    private int month;

    private String techName;

    public static Career toEntity(int month, TechStack techStack) {
        return new Career(month, techStack);
    }
}
