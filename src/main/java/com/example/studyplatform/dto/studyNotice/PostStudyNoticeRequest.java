package com.example.studyplatform.dto.studyNotice;

import com.example.studyplatform.domain.study.Study;
import com.example.studyplatform.domain.studyNotice.StudyNotice;
import com.example.studyplatform.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostStudyNoticeRequest {
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
