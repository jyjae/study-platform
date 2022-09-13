package com.example.studyplatform.dto.studyUser;

import com.example.studyplatform.constant.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "스터디와 유저 관계 응답 DTO")
public class StudyUserResponse {
    private Long id;
    private Long userId;
    private Long studyId;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static StudyUserResponse of(
            Long id,
            Long userId,
            Long studyId,
            Status status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        return new StudyUserResponse(
                id,
                userId,
                studyId,
                status,
                createdAt,
                updatedAt
        );
    }

}
