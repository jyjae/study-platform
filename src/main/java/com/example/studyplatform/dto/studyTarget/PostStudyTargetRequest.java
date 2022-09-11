package com.example.studyplatform.dto.studyTarget;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "스터디 목표 생성 DTO")
public class PostStudyTargetRequest {

    @NotBlank(message = "제목을 입력해주세요")
    private String studyTargetTitle;
    private int studyTargetPriority;
}
