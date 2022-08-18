package com.example.studyplatform.controller.board;

import com.example.studyplatform.dto.board.BoardReadCondition;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/board")
@RestController
public class BoardController {
    private final BoardService boardService;

    @GetMapping
    public Response readAllByCondition(
            BoardReadCondition condition,
            @PageableDefault(size = 10)Pageable pageable
            ) {
        System.out.println(condition);
        return Response.success(boardService.readAllByCondition(condition.getCursorId(), condition, pageable));
    }
}
