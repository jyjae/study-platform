package com.example.studyplatform.domain.calender;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.BaseTimeEntity;
import com.example.studyplatform.domain.study.Study;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Calender extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String calenderTitle;

    private String calenderContents;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private boolean alarm;

    private boolean online;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    private Study study;

    // TODO : 작성자아이디는 어떻게 가져올것인지?
    // TODO : 참석자 테이블 생성

    @Builder
    public Calender(String calenderTitle, String calenderContents,
                    LocalDateTime startDate, LocalDateTime endDate,
                    LocalDateTime startTime, LocalDateTime endTime,
                    boolean alarm, List<String> attendees, boolean online) {
        this.calenderTitle = calenderTitle;
        this.calenderContents = calenderContents;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.alarm = alarm;
        this.online= online;
        this.status = Status.ACTIVE;
    }
}
