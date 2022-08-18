package com.example.studyplatform.domain.alarm;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.BaseTimeEntity;
import com.example.studyplatform.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@Entity
public class Alarm extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Boolean isRead;

    private String url;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder
    public Alarm(String title, String url, User user) {
        this.title = title;
        this.url = url;
        this.user = user;
        this.isRead = false;
    }
}
