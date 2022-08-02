package com.example.studyplatform.domain.studyNotice;

import com.example.studyplatform.domain.BaseTimeEntity;
import com.example.studyplatform.domain.study.Study;
import com.example.studyplatform.domain.user.User;
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
    public StudyNotice(String studyNoticeTitle, String studyNoticeContents) {
        this.studyNoticeTitle = studyNoticeTitle;
        this.studyNoticeContents = studyNoticeContents;
    }
}
