package com.example.studyplatform.dto.studyNotice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "스터디 공지 수정 DTO")
public class PutStudyNoticeRequest {
    private String studyNoticeTitle;
    private String studyNoticeContents;
}
