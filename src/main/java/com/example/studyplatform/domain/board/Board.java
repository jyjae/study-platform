package com.example.studyplatform.domain.board;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.BaseTimeEntity;
import com.example.studyplatform.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

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

    @Enumerated(EnumType.STRING)
    protected Status status;

    public Board(User user, String title, String content, Boolean isOnline, Boolean isCamara, Boolean isMic, Boolean isFinish,
                 LocalDateTime recruitStartedAt, LocalDateTime recruitEndedAt, LocalDateTime startedAt, LocalDateTime endedAt) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.isOnline = isOnline;
        this.isCamera = isCamara;
        this.isMike = isMic;
        this.isFinish = isFinish;
        this.recruitStartedAt = recruitStartedAt;
        this.recruitEndedAt = recruitEndedAt;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.status = Status.ACTIVE;
    }
}
