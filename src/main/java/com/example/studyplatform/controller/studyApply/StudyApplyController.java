package com.example.studyplatform.controller.studyApply;

import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.board.BoardDto;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.dto.studyApply.PostStudyApplyRequest;
import com.example.studyplatform.dto.studyApply.PutStudyApplyRequest;
import com.example.studyplatform.dto.studyApply.StudyApplyResponse;
import com.example.studyplatform.service.studyApply.StudyApplyService;
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

@Tag(name = "study-apply-controller", description = "스터디 신청 API")
@RequiredArgsConstructor
@RequestMapping("/api/study-applies")
@RestController
public class StudyApplyController {
    private final StudyApplyService studyApplyService;

    @Operation(
            summary = "스터디 신청",
            description = "스터디 신청",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "스터디 신청 성공",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = StudyApplyResponse.class)))
                    )
            },
            parameters = {
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER, required = true)
            }
    )
    @PostMapping
    public Response applyStudy(
            @RequestBody @Valid PostStudyApplyRequest req,
            @AuthenticationPrincipal User user
    ) {
        return Response.success(studyApplyService.applyStudy(req, user));
    }


    @Operation(
            summary = "신청했던 스터디 신청 삭제",
            description = "신청했던 스터디 신청 삭제",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "신청했던 스터디 신청 삭제 성공",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Response.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "존재하지 않은 신청 아이디",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Response.class)))
                    )
            },
            parameters = {
                    @Parameter(name = "id", description = "신청 ID", in = ParameterIn.PATH, required = true),
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER, required = true)
            }
    )
    @PutMapping("/{id}")
    public Response deleteApply(
            @PathVariable("id") @NotBlank(message = "신청 아이디를 입력해주세요") Long applyId,
            @AuthenticationPrincipal User user
    ) {
        studyApplyService.deleteApply(applyId, user);
        return Response.success();
    }

    @Operation(
            summary = "유저가 생성한 스터디 신청 승인 및 거절",
            description = "유저가 생성한 스터디 신청 승인 및 거절",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "유저가 생성한 스터디 신청 승인 및 거절 성공",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Response.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "존재하지 않은 신청 아이디",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Response.class)))
                    )
            },
            parameters = {
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER, required = true)
            }
    )
    @PutMapping
    public Response acceptOrReject(
            @RequestBody @Valid PutStudyApplyRequest req,
            @AuthenticationPrincipal User user
    ) {
        return Response.success(studyApplyService.modifyApplyStatus(req, user));
    }

}
