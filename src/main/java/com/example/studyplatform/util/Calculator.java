package com.example.studyplatform.util;

import org.springframework.stereotype.Component;

@Component
public class Calculator {

    public static String time(long time) {
        // 게시글이 현재로 부터 몇 분전에 작성되었는지 보기 좋게 변경
        if (time < 60) {
            return String.format("%s분 전", time);
        }
        time = time / 60;  // 시간
        if (time < 24) {
            return String.format("%s시간 전", time);
        }
        time = time / 24;
        if (time < 7) {
            return String.format("%s일 전", time);
        }

        time = time / 30;
        if (time < 12) {
            return String.format("%s개월 전", time);
        }

        time = time / 12;
        return String.format("%s년 전", time);
    }
}
