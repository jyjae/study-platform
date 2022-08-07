package com.example.studyplatform.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CareerStatus {
    NONE("NONE", "무관"),
    LOW("LOW", "1년 미만"),
    MID("MID", "1년 이상"),
    HIGH("HIGH", "3년 이상");

    private final String key;
    private final String title;
}
