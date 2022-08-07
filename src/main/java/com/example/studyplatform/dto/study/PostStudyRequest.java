package com.example.studyplatform.dto.study;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.study.Study;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostStudyRequest {
    private String studyName;
    private LocalDateTime endedAt;

    public Study toEntity() {
        return Study.builder().
                studyName(studyName).
                endedAt(endedAt).
                status(Status.ACTIVE).build();
    }
}
