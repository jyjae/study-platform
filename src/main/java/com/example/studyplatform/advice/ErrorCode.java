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
    STUDY_APPLY_NOT_FOUND(-1007, "해당 스터디 신청은 존재하지 않습니다.");

    private final int code;
    private final String message;
}
