package com.example.studyplatform.dto.study;

import com.example.studyplatform.constant.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "스터디 응답 DTO")
public class StudyResponse {
    private Long id;

    private String studyName;

    private LocalDateTime endedAt;

    private Status status;

    public static StudyResponse of(
            Long id,
            String studyName,
            LocalDateTime endedAt,
            Status status) {
        return new StudyResponse(id, studyName, endedAt, status);
    }
}
