package com.example.studyplatform.dto.scrap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteScrapRequest {
    private Long userId;
    private Long boardId;
}
