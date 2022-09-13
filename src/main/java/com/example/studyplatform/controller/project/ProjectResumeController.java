package com.example.studyplatform.controller.project;

import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.project.*;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.service.project.ProjectResumeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "project-resume-controller", description = "프로젝트 신청서 API")
@RequiredArgsConstructor
@RequestMapping("/api/project-resume")
@RestController
public class ProjectResumeController {
    private final ProjectResumeService projectResumeService;

    @Operation(
            summary = "프로젝트 신청서 생성",
            description = "프로젝트 신청서를 생성합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "프로젝트 신청서 생성 성공", content = @Content(schema = @Schema(implementation = Response.class)))
            },
            parameters = {
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER)
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response create(
            @RequestBody @Valid ProjectResumeCreateRequest req,
            @AuthenticationPrincipal User user
    ) {
        projectResumeService.create(req, user);
        return Response.success();
    }

    @Operation(
            summary = "프로젝트 신청서 조회",
            description = "프로젝트 신청서를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "프로젝트 신청서 조회 성공", content = @Content(schema = @Schema(implementation = ProjectResumeResponse.class))),
            },
            parameters = {
                    @Parameter(name = "id", description = "프로젝트 신청서 ID", in = ParameterIn.PATH)
            }
    )
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response read(@PathVariable("id") Long resumeId) {
        return Response.success(projectResumeService.read(resumeId));
    }

    @Operation(
            summary = "프로젝트 신청서 승인",
            description = "프로젝트 신청서를 승인합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "프로젝트 신청서 승인 성공", content = @Content(schema = @Schema(implementation = Response.class))),
            },
            parameters = {
                    @Parameter(name = "id", description = "프로젝트 신청서 ID", in = ParameterIn.PATH)
            }
    )
    @PatchMapping("/{id}/approval")
    @ResponseStatus(HttpStatus.OK)
    public Response approvalResume(
            @PathVariable("id") Long resumeId,
            @RequestBody @Valid ProjectResumeApprovalRequest req
    ) {
        projectResumeService.approvalResume(resumeId, req);
        return Response.success();
    }

    @Operation(
            summary = "프로젝트 신청서 거절",
            description = "프로젝트 신청서를 거절합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "프로젝트 신청서 거절 성공", content = @Content(schema = @Schema(implementation = Response.class))),
            },
            parameters = {
                    @Parameter(name = "id", description = "프로젝트 신청서 ID", in = ParameterIn.PATH)
            }
    )
    @PatchMapping("/{id}/denied")
    @ResponseStatus(HttpStatus.OK)
    public Response deniedResume(
            @PathVariable("id") Long resumeId
    ) {
        projectResumeService.deniedResume(resumeId);
        return Response.success();
    }

    @Operation(
            summary = "확정된 프로젝트 신청서 삭제",
            description = "확정된 프로젝트 신청서를 삭제합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "확정된 프로젝트 신청서 삭제 성공", content = @Content(schema = @Schema(implementation = Response.class))),
            },
            parameters = {
                    @Parameter(name = "id", description = "프로젝트 신청서 ID", in = ParameterIn.PATH)
            }
    )
    @PatchMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    public Response deleteAppliedResume(
            @PathVariable("id") Long resumeId,
            @RequestBody @Valid ProjectResumeDeleteRequest req
    ) {
        projectResumeService.deleteAppliedResume(resumeId, req);
        return Response.success();
    }
}
