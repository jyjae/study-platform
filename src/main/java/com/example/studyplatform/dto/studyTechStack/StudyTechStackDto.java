package com.example.studyplatform.dto.studyTechStack;

import com.example.studyplatform.domain.studyTechStack.StudyTechStack;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyTechStackDto {
    private Long id;
    private String techName;
    private String techImgUrl;
    private String stack;

    public static StudyTechStackDto of(
            StudyTechStack entity
    ) {
        return new StudyTechStackDto(
                entity.getId(),
                entity.getTechStack().getTechName(),
                entity.getTechStack().getTechImg(),
                entity.getTechStack().getStack().name());
    }
}
