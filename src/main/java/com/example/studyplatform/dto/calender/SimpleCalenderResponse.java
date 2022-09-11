package com.example.studyplatform.dto.calender;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "캘린더 전체 조회 시 응답 DTO")
public class SimpleCalenderResponse {
    private Long id;
    private Long userId;
    private String calenderTitle;
    private String calenderContents;

    public static SimpleCalenderResponse of(
            Long id,
            Long userId,
            String calenderTitle,
            String calenderContents) {
        return new SimpleCalenderResponse(
                id,
                userId,
                calenderTitle,
                calenderContents);
    }
}
