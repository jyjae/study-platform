package com.example.studyplatform.dto.calender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
