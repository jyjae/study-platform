package com.example.studyplatform.domain.board;

import com.example.studyplatform.dto.board.BoardDto;
import com.example.studyplatform.dto.board.BoardReadCondition;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface BoardRepositoryCustom {
    Slice<BoardDto> findAllByCondition(Long cursorId, BoardReadCondition boardReadCondition, Pageable pageable);

    List<BoardDto> findScrapBoardsByUserId(Long userId);
}
