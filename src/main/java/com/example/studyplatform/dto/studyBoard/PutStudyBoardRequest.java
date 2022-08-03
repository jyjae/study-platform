package com.example.studyplatform.dto.studyBoard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PutStudyBoardRequest extends PostStudyBoardRequest{
    private Long studyBoardId;
    // 추가하는 기술 스택의 테이블 아이디
    private List<Long> addTechStackIds;
    // 삭제하는 스터디 기술 스택 테이블의 아이디
    private List<Long> deleteStudyTechStackIds;

}
