package com.example.studyplatform.controller.studyNotice;

import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.dto.studyNotice.PostStudyNoticeRequest;
import com.example.studyplatform.dto.studyNotice.PutStudyNoticeRequest;
import com.example.studyplatform.dto.studyNotice.StudyNoticeResponse;
import com.example.studyplatform.service.studyNotice.StudyNoticeService;
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

@Tag(name = "study-notice-controller", description = "스터디 공지 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/study-notice")
public class StudyNoticeController {

    private final StudyNoticeService studyNoticeService;

    @Operation(
            summary = "스터디 공지 전체 조회",
            description = "스터디방에 전체공지를 보여드립니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "스터디 공지 전체 조회 성공",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = StudyNoticeResponse.class)))
                    )
            },
            parameters = {
                    @Parameter(name = "studyId", description = "스터디방 ID", in = ParameterIn.PATH)
            }
    )
    @GetMapping("/{studyId}")
    public Response list(
            @PathVariable Long studyId
    ) {
        return Response.success(studyNoticeService.list(studyId));
    }

    @Operation(
            summary = "스터디 공지 생성",
            description = "스터디공지를 생성합니다.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "스터디 공지 생성 성공",
                            content = @Content(schema = @Schema(implementation = StudyNoticeResponse.class)))
            },
            parameters = {
                    @Parameter(name = "studyId", description = "스터디방 ID", in = ParameterIn.PATH),
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER)
            }
    )
    @PostMapping("/{studyId}")
    public Response create(
            @PathVariable Long studyId,
            @AuthenticationPrincipal User user,
            @RequestBody @Valid PostStudyNoticeRequest req
    ) {
        return Response.success(studyNoticeService.create(studyId, user, req));
    }

    @Operation(
            summary = "스터디 공지 수정",
            description = "스터디공지를 생성합니다.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "스터디 공지 수정 성공",
                            content = @Content(schema = @Schema(implementation = StudyNoticeResponse.class)))
            },
            parameters = {
                    @Parameter(name = "studyNoticeId", description = "스터디공지 ID", in = ParameterIn.PATH),
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER)
            }
    )
    @PatchMapping("/{studyNoticeId}")
    public Response update(
            @PathVariable Long studyNoticeId,
            @AuthenticationPrincipal User user,
            @RequestBody @Valid PutStudyNoticeRequest req
    ) {
        return Response.success(studyNoticeService.update(studyNoticeId, req, user));
    }

    @Operation(
            summary = "스터디 공지 삭제",
            description = "스터디공지를 삭제합니다.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "스터디 공지 삭제 성공",
                            content = @Content(schema = @Schema(implementation = Response.class)))
            },
            parameters = {
                    @Parameter(name = "studyNoticeId", description = "스터디공지 ID", in = ParameterIn.PATH),
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER)
            }
    )
    @DeleteMapping("/{studyNoticeId}")
    public Response delete(
            @PathVariable Long studyNoticeId,
            @AuthenticationPrincipal User user
    ){
        studyNoticeService.delete(studyNoticeId, user);
        return Response.success();
    }
}
