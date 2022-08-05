package com.example.studyplatform.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private Long id;

    private String dtype;

    private String nickname;

    private String title;

    private Boolean isOnline;

    private Boolean isCamera;

    private Boolean isMike;

    private Boolean isFinish;

    private LocalDateTime recruitStartedAt;

    private LocalDateTime recruitEndedAt;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;
}
