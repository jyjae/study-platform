package com.example.studyplatform.controller.studyBoard;

import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.dto.studyBoard.PostStudyBoardRequest;
import com.example.studyplatform.dto.studyBoard.PutStudyBoardRequest;
import com.example.studyplatform.service.studyBoard.StudyBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/study-boards")
@RestController
public class StudyBoardController {
    private final StudyBoardService studyBoardService;

    @PostMapping
    public Response createStudyBoard(
            @RequestBody PostStudyBoardRequest req,
            @AuthenticationPrincipal User user
    ) {
        return Response
                .success(studyBoardService.createStudyBoard(req, user));
    }

    @PutMapping
    Response modifyStudyBoard(
            @RequestBody PutStudyBoardRequest req,
            @AuthenticationPrincipal User user
    ) {
        return Response.success(studyBoardService.modifyStudyBoard(req, user));
    }

    @PutMapping("/{id}")
    Response deleteStudyBoard(
            @PathVariable("id") UUID studyBoardId,
            @AuthenticationPrincipal User user
    ) {
        studyBoardService.deleteStudyBoard(studyBoardId, user);
        return Response.success();
    }
}
