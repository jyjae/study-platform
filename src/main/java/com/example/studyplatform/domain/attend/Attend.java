package com.example.studyplatform.domain.attend;

import com.example.studyplatform.domain.calender.Calender;
import com.example.studyplatform.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Attend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long calenderId;
    private Long userId;

    @Builder
    public Attend(Long calenderId, Long userId) {
        this.calenderId = calenderId;
        this.userId = userId;
    }
}
