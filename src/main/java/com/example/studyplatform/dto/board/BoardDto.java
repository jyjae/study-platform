package com.example.studyplatform.dto.board;

import com.example.studyplatform.constant.CareerStatus;
import com.example.studyplatform.domain.board.Board;
import com.example.studyplatform.domain.project.projectPost.ProjectPost;
import com.example.studyplatform.dto.studyTechStack.StudyTechStackDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    protected Long id;

    protected String dtype;

    protected String nickname;

    protected String title;

    protected Boolean isOnline;

    protected Boolean isCamera;

    protected Boolean isMike;

    protected Boolean isFinish;

    protected LocalDateTime recruitStartedAt;

    protected LocalDateTime recruitEndedAt;

    protected LocalDateTime startedAt;

    protected LocalDateTime endedAt;
}
