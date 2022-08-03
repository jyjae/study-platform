package com.example.studyplatform.domain.project.projectOrganization;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CareerStatus {
    LOW("LOW", "1년 미만"),
    MID("MID", "1년 이상"),
    HIGH("HIGH", "3년 이상");

    private final String key;
    private final String title;
}
