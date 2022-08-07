package com.example.studyplatform.dto.board;

import com.example.studyplatform.constant.CareerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardReadCondition {
    private Long cursorId;
    private String dType;
    private String city;
    private Boolean isOnline;
    private CareerStatus careerStatus;
    private List<Long> techIds;
}
