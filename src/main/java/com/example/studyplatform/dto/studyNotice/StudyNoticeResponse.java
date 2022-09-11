package com.example.studyplatform.dto.studyNotice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "스터디 공지 응답 DTO")
public class StudyNoticeResponse {
    private Long id;
    private Long study_id;
    private Long user_id;
    private String studyNoticeTitle;
    private String studyNoticeContents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static StudyNoticeResponse of(
             Long id,
             Long study_id,
             Long user_id,
             String studyNoticeTitle,
             String studyNoticeContents,
             LocalDateTime createdAt,
             LocalDateTime updatedAt
    ){
        return new StudyNoticeResponse(
                id,
                study_id,
                user_id,
                studyNoticeTitle,
                studyNoticeContents,
                createdAt,
                updatedAt
        );
    }
}
