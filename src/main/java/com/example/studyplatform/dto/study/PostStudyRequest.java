package com.example.studyplatform.dto.study;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.study.Study;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Schema(description = "스터디방 요청 DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostStudyRequest {

    @Schema(description = "스터디방 이름")
    @NotBlank(message = "스터디방 이름을 입력해주세요.")
    @Min(value = 2, message = "2글자 이상 가능합니다.")
    private String studyName;

    @Schema(description = "스터디방 종료 일자")
    private LocalDateTime endedAt;

    public Study toEntity() {
        return Study.builder().
                studyName(studyName).
                endedAt(endedAt).
                status(Status.ACTIVE).build();
    }
}
