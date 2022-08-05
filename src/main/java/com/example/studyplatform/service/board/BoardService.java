package com.example.studyplatform.service.board;

import com.example.studyplatform.domain.board.BoardRepository;
import com.example.studyplatform.dto.board.BoardDto;
import com.example.studyplatform.dto.board.BoardReadCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public Slice<BoardDto> readAllByCondition(Long cursorId, BoardReadCondition condition, Pageable pageable) {
        return boardRepository.findAllByCondition(cursorId, condition, pageable);
    }
}
