package com.example.studyplatform.dto.calender;

import com.example.studyplatform.constant.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "캘린더 응답 DTO")
public class CalenderResponse {
    private Long id;
    private Long studyId;
    private Long userId;
    private String calenderTitle;
    private String calenderContents;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Set<Long> attends; // 참석자 아이디 (중복제외)
    private boolean alarm;
    private boolean online;
    private Status status;

    public static CalenderResponse of(
            Long id,
            Long studyId,
            Long userId,
            String calenderTitle,
            String calenderContents,
            LocalDateTime startDate,
            LocalDateTime endDate,
            LocalDateTime startTime,
            LocalDateTime endTime,
            Set<Long> attends,
            boolean alarm,
            boolean online,
            Status status

    ) {
        return new CalenderResponse(
                id,
                studyId,
                userId,
                calenderTitle,
                calenderContents,
                startDate,
                endDate,
                startTime,
                endTime,
                attends,
                alarm,
                online,
                status
        );
    }
}
