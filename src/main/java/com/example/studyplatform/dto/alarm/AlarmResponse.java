package com.example.studyplatform.dto.alarm;

import com.example.studyplatform.domain.alarm.Alarm;
import com.example.studyplatform.domain.alarm.AlarmRepository;
import com.example.studyplatform.dto.chat.ChatMessageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "알람 DTO")
public class AlarmResponse {
    private Long id;

    private String title;

    private Boolean isRead;

    private String url;

    private LocalDateTime createdAt;

    public static AlarmResponse toDto(AlarmRequest req) {
        return new AlarmResponse(
                req.getId(),
                req.getTitle(),
                req.getIsRead(),
                req.getUrl(),
                req.getCreatedAt()
        );
    }

    public static AlarmResponse toDto(Alarm alarm) {
        return new AlarmResponse(
                alarm.getId(),
                alarm.getTitle(),
                alarm.getIsRead(),
                alarm.getUrl(),
                alarm.getCreatedAt()
        );
    }
}
