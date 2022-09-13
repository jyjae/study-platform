package com.example.studyplatform.dto.techStack;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.techStack.Stack;
import com.example.studyplatform.domain.techStack.TechStack;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "기술스택 DTO")
public class TechStackDto {
    private Long id;

    private String techName;

    private String techImg;

    private Stack stack;

    private Status status;

    public static TechStackDto toDto(TechStack techStack) {
        return new TechStackDto(
                techStack.getId(),
                techStack.getTechName(),
                techStack.getTechImg(),
                techStack.getStack(),
                techStack.getStatus()
        );
    }
}
