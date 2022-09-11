package com.example.studyplatform.dto.studyNotice;

import com.example.studyplatform.domain.study.Study;
import com.example.studyplatform.domain.studyNotice.StudyNotice;
import com.example.studyplatform.domain.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "스터디 공지 생성 DTO")
public class PostStudyNoticeRequest {

    @NotBlank(message = "스터디 공지 제목을 입력해주세요")
    private String studyNoticeTitle;
    private String studyNoticeContents;

    public StudyNotice toEntity(
            Study study,
            User user
    ){
        return StudyNotice.builder()
                .studyNoticeTitle(studyNoticeTitle)
                .studyNoticeContents(studyNoticeContents)
                .study(study)
                .user(user)
                .build();
    }
}
