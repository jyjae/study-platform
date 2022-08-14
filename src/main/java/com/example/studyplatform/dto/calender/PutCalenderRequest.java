package com.example.studyplatform.dto.calender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PutCalenderRequest {
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
