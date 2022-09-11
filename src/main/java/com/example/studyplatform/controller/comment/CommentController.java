package com.example.studyplatform.controller.comment;

import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.board.BoardDto;
import com.example.studyplatform.dto.comment.CommentResponse;
import com.example.studyplatform.dto.comment.GetCommentResponse;
import com.example.studyplatform.dto.comment.PostCommentRequest;
import com.example.studyplatform.dto.comment.PutCommentRequest;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.service.comment.CommentService;
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

@Tag(name = "comment-controller", description = "댓글 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @Operation(
            summary = "특정 게시글 댓글 조회",
            description = "특정 게시글 댓글 전체 조회",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "조회 성공",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = GetCommentResponse.class)))
                    )
            },
            parameters = {
                    @Parameter(name = "board-id", description = "게시글 ID", in = ParameterIn.PATH),
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER)
            }
    )
    @GetMapping
    public Response getCommentAll(
            @RequestParam("board-id") Long boardId
    ) {
        return Response.success(commentService.getCommentAll(boardId));
    }

    @Operation(
            summary = "댓글 생성",
            description = "댓글 생성",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "댓글 생성 성공",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Long.class)))
                    )
            },
            parameters = {
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER)
            }
    )
    @PostMapping
    public Response createComment(
            @RequestBody @Valid PostCommentRequest postCommentRequest,
            @AuthenticationPrincipal User user) {
        return Response.success(commentService.createComment(postCommentRequest, user));
    }

    @Operation(
            summary = "댓글 수정",
            description = "댓글 수정",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "댓글 수정 성공",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = CommentResponse.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "존재하지 않은 댓글",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Response.class)))
                    )
            },
            parameters = {
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER)
            }
    )
    @PutMapping
    public Response modifyComment(
            @RequestBody @Valid PutCommentRequest putCommentRequest,
            @AuthenticationPrincipal User user
    ) {
        return Response.success(commentService.modifyComment(putCommentRequest, user));
    }

    @Operation(
            summary = "댓글 삭제",
            description = "댓글 삭제",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "댓글 삭제 성공",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Response.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "존재하지 않은 댓글",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Response.class)))
                    )
            },
            parameters = {
                    @Parameter(name = "id", description = "댓글 ID", in = ParameterIn.PATH),
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER)
            }
    )
    @PutMapping("/{id}")
    public Response deleteComment(
            @PathVariable("id") @NotBlank(message = "댓글 아이디를 입력해주세요.") Long commentId,
            @AuthenticationPrincipal User user
    ) {
        commentService.deleteComment(commentId, user);
        return Response.success();
    }
}
