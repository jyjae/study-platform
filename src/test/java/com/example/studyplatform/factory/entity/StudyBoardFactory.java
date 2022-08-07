package com.example.studyplatform.factory.entity;

import com.example.studyplatform.domain.studyBoard.StudyBoard;
import com.example.studyplatform.domain.user.User;

import java.time.LocalDateTime;

import static com.example.studyplatform.factory.entity.UserFactory.createUser;

public class StudyBoardFactory {
    public static StudyBoard createStudyBoard(User user) {
        return StudyBoard.builder()
                .title("title")
                .content("content")
                .userCnt(1)
                .isCamara(true)
                .isDead(true)
                .isMic(true)
                .isOnline(true)
                .endAt(LocalDateTime.now())
                .recruitEndAt(LocalDateTime.now())
                .recruitStartAt(LocalDateTime.now())
                .startAt(LocalDateTime.now())
                .user(user)
                .build();
    }

    public static StudyBoard createStudyBoard(User user, String title) {
        return StudyBoard.builder()
                .title(title)
                .content("content")
                .userCnt(1)
                .isCamara(true)
                .isDead(true)
                .isMic(true)
                .isOnline(true)
                .endAt(LocalDateTime.now())
                .recruitEndAt(LocalDateTime.now())
                .recruitStartAt(LocalDateTime.now())
                .startAt(LocalDateTime.now())
                .user(user)
                .build();
    }
}
