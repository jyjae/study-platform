package com.example.studyplatform.dto.study;

import com.example.studyplatform.constant.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "스터디방 수정 DTO")
public class PutStudyRequest {
    @Min(value = 2, message = "제목은 2글자 이상이어야 합니다")
    private String studyName;
    private LocalDateTime endedAt;
    private Status status;
}
