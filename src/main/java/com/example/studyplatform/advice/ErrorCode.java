package com.example.studyplatform.advice;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    TECH_STACK_NOT_FOUND(-1000, "기술 스택을 찾지 못하였습니다."),
    USER_EMAIL_ALREADY_EXISTS(-1001, "이미 존재하는 이메일입니다."),
    USER_NICKNAME_ALREADY_EXISTS(-1002, "이미 존재하는 닉네임입니다.");

    private final int code;
    private final String message;
}
