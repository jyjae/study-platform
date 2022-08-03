package com.example.studyplatform.dto.studyApply;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostStudyApplyRequest {
    private UUID studyBoardId;
}
