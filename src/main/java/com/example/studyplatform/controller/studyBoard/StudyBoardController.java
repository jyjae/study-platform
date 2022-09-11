package com.example.studyplatform.controller.studyBoard;

import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.board.BoardDto;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.dto.studyBoard.PostStudyBoardRequest;
import com.example.studyplatform.dto.studyBoard.PutStudyBoardRequest;
import com.example.studyplatform.dto.studyBoard.StudyBoardResponse;
import com.example.studyplatform.service.studyBoard.StudyBoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Tag(name = "study-board-controller", description = "스터디 게시글 API")
@RequiredArgsConstructor
@RequestMapping("/api/study-boards")
@RestController
public class StudyBoardController {
    private final StudyBoardService studyBoardService;

    @Operation(
            summary = "스터디 게시글 생성",
            description = "스터디 게시글 생성",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "스터디 게시글 생성",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = StudyBoardResponse.class)))
                    )
            },
            parameters = {
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER)
            }
    )
    @PostMapping
    public Response createStudyBoard(
            @RequestBody @Valid PostStudyBoardRequest req,
            @AuthenticationPrincipal User user
    ) {
        return Response
                .success(studyBoardService.createStudyBoard(req, user));
    }

    @Operation(
            summary = "스터디 게시글 수정",
            description = "스터디 게시글 수정",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "스터디 게시글 수정 성공",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = StudyBoardResponse.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "존재하지 않은 스터디 게시글",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = StudyBoardResponse.class)))
                    )
            },
            parameters = {
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER)
            }
    )
    @PutMapping
    Response modifyStudyBoard(
            @RequestBody @Valid PutStudyBoardRequest req,
            @AuthenticationPrincipal User user
    ) {
        return Response.success(studyBoardService.modifyStudyBoard(req, user));
    }

    @Operation(
            summary = "스터디 게시글 삭제",
            description = "스터디 게시글 삭제",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "스터디 게시글 삭제 성공",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = StudyBoardResponse.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "존재하지 않은 스터디 게시글",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = StudyBoardResponse.class)))
                    )
            },
            parameters = {
                    @Parameter(name = "id", description = "스터디 게시글 아이디", in = ParameterIn.PATH),
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER)
            }
    )
    @PutMapping("/{id}")
    Response deleteStudyBoard(
            @PathVariable("id") @NotBlank(message = "스터디 게시글 아이디를 입력해주세요.") Long studyBoardId,
            @AuthenticationPrincipal User user
    ) {
        studyBoardService.deleteStudyBoard(studyBoardId, user);
        return Response.success();
    }
}
