package com.example.studyplatform.dto.alarm;


import com.example.studyplatform.domain.alarm.Alarm;
import com.example.studyplatform.dto.chat.ChatMessageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlarmRequest {
    private Long id;

    private String title;

    private Boolean isRead;

    private String url;

    private LocalDateTime createdAt;

    private Long otherUserId;

    public static AlarmRequest toDto(Alarm alarm, Long otherUserId) {
        return new AlarmRequest(
                alarm.getId(),
                alarm.getTitle(),
                alarm.getIsRead(),
                alarm.getUrl(),
                alarm.getCreatedAt(),
                otherUserId
        );
    }
}