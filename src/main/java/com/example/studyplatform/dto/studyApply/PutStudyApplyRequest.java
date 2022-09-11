package com.example.studyplatform.dto.studyApply;

import com.example.studyplatform.constant.studyApply.ApplyStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PutStudyApplyRequest {
    @NotBlank(message = "신청 아이디를 입력해주세요")
    private Long applyId;

    @NotBlank(message = "신청에 대한 처리를 입력해주세요")
    @Schema(description = "스터디 신청에 대한 처리 (ACCEPT / WAITING / REJECT) 중에서 선택해주세요")
    private ApplyStatus applyStatus;
}
