package com.example.studyplatform.dto.studyApply;

import com.example.studyplatform.constant.studyApply.ApplyStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PutStudyApplyRequest {
    private Long applyId;
    private ApplyStatus applyStatus;
}
