package com.example.studyplatform.domain.board;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.BaseTimeEntity;
import com.example.studyplatform.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Entity
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    protected User user;

    protected String title;

    protected String content;

    protected Boolean isOnline;

    protected Boolean isCamera;

    protected Boolean isMike;

    protected Boolean isFinish;

    protected LocalDateTime recruitStartedAt;

    protected LocalDateTime recruitEndedAt;

    protected LocalDateTime startedAt;

    protected LocalDateTime endedAt;

    // 광역시도
    protected String metropolitanCity;

    // 시군구
    protected String city;

    @Column(insertable = false, updatable = false)
    protected String dtype;

    @Enumerated(EnumType.STRING)
    protected Status status;
}
