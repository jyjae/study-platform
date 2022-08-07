package com.example.studyplatform.domain.studyNotice;

import com.example.studyplatform.domain.BaseTimeEntity;
import com.example.studyplatform.domain.study.Study;
import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.studyNotice.PutStudyNoticeRequest;
import com.example.studyplatform.dto.studyNotice.StudyNoticeResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class StudyNotice extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studyNoticeTitle;

    private String studyNoticeContents;

    @ManyToOne(fetch = FetchType.LAZY)
    private Study study;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public StudyNotice(
            String studyNoticeTitle,
            String studyNoticeContents,
            Study study,
            User user
    ) {
        this.study = study;
        this.user = user;
        this.studyNoticeTitle = studyNoticeTitle;
        this.studyNoticeContents = studyNoticeContents;
    }

    public StudyNoticeResponse result() {
        return StudyNoticeResponse.of(
                this.id,
                this.study.getId(),
                this.user.getId(),
                this.studyNoticeTitle,
                this.studyNoticeContents,
                getCreatedAt(),
                getUpdatedAt()
        );
    }

    public StudyNoticeResponse update(PutStudyNoticeRequest req) {
        this.studyNoticeTitle = req.getStudyNoticeTitle();
        this.studyNoticeContents = req.getStudyNoticeContents();

        return StudyNoticeResponse.of(
                this.id,
                this.study.getId(),
                this.user.getId(),
                this.studyNoticeTitle,
                this.studyNoticeContents,
                getCreatedAt(),
                getUpdatedAt()
        );
    }
}
