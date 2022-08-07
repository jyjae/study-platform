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
public class PutStudyRequest {
    private String studyName;
    private LocalDateTime endedAt;
    private Status status;
}
