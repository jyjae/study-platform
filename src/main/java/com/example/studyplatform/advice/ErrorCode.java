package com.example.studyplatform.advice;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    TECH_STACK_NOT_FOUND(-1000, "해당 기술 스택은 존재하지 않습니다."),
    USER_EMAIL_ALREADY_EXISTS(-1001, "이미 존재하는 이메일입니다."),
    USER_NICKNAME_ALREADY_EXISTS(-1002, "이미 존재하는 닉네임입니다."),
    CAREER_NOT_FOUND(-1003, "해당 경력은 존재하지 않습니다."),
    USER_NOT_FOUND(-1004, "해당 유저는 존재하지 않습니다."),

    STUDY_BOARD_NOT_FOUND(-1005, "해당 스터디 게시글은 존재하지 않습니다."),
    STUDY_TECH_STACK_NOT_FOUND(-1006, "해당 스터디 게시글의 기술스택은 존재하지 않습니다."),
    STUDY_APPLY_NOT_FOUND(-1007, "해당 스터디 신청은 존재하지 않습니다."),

    STUDY_NOT_FOUND(-1020, "해당 스터디방이 존재하지 않습니다."),
    STUDY_TARGET_NOT_FOUND(-1021, "해당 스터디목표는 존재하지 않습니다"),
    STUDY_NOTICE_NOT_FOUND(-1022, "해당 스터디공지는 존재하지 않습니다"),

    CALENDER_NOT_FOUND(-1023, "해당 일정이 존재하지 않습니다."),

    PROJECT_ORGANIZATION_DECREASE_ZERO(-1008, "프로젝트 모집 인원이 0이하 입니다."),
    PROJECT_POST_NOT_FOUND(-1009, "해당 프로젝트 게시글은 존재하지 않습니다."),
    PROJECT_ORGANIZATION_NOT_FOUND(-1010, "해당 프로젝트 인원은 존재하지 않습니다."),


    COMMENT_NOT_FOUND(-1300, "해당 댓글은 존재하지 않습니다."),

    CHATROOM_CANNOT_MAKE_ROOM_ALONE(-1320, "일대일 방에는 두 명의 유저가 있어야합니다."),
    CHATROOM_GROUP_NOT_CONTAINS_USER(-1321, "그룹 채팅방 생성에는 유저 아이디도 필요합니다."),
    CHATROOM_NOT_FOUND(-1322, "채팅방을 찾을 수 없습니다");

    private final int code;
    private final String message;
}
