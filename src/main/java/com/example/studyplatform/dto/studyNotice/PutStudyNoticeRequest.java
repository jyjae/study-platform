package com.example.studyplatform.dto.studyNotice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PutStudyNoticeRequest {
    private String studyNoticeTitle;
    private String studyNoticeContents;
}
