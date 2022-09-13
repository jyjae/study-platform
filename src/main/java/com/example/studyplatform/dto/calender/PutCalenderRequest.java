package com.example.studyplatform.dto.calender;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "캘린더 수정 DTO")
public class PutCalenderRequest {
    @NotBlank(message = "캘린더 제목을 입력해주세요")
    private String calenderTitle;
    private String calenderContents;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isAlarm;
    private boolean isOnline;
    private Set<Long> attends;
}
