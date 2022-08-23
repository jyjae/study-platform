package com.example.studyplatform.dto.alarm;


import com.example.studyplatform.domain.alarm.Alarm;
import com.example.studyplatform.dto.chat.ChatMessageRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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

    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
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