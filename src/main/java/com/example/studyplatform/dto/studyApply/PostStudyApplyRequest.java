package com.example.studyplatform.dto.studyApply;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostStudyApplyRequest {
    @NotBlank(message = "스터디 게사글 아이디")
    private Long studyBoardId;
}
