package com.example.studyplatform.factory.entity;

import com.example.studyplatform.domain.studyBoard.StudyBoard;

import java.time.LocalDateTime;

public class StudyBoardFactory {
    public static StudyBoard createStudyBoard() {
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
                .build();
    }
}
