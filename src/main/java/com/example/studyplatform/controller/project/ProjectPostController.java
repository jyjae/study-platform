package com.example.studyplatform.controller.project;

import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.project.ProjectPostCreateRequest;
import com.example.studyplatform.dto.project.ProjectPostResponse;
import com.example.studyplatform.dto.project.ProjectPostUpdateRequest;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.service.project.ProjectPostService;
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

@Tag(name = "project-post-controller", description = "프로젝트 게시물 API")
@RequiredArgsConstructor
@RequestMapping("/api/project-post")
@RestController
public class ProjectPostController {
    private final ProjectPostService projectPostService;

    @Operation(
            summary = "프로젝트 게시물 생성",
            description = "프로젝트 게시물을 생성합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "프로젝트게시물 생성 성공", content = @Content(schema = @Schema(implementation = Response.class)))
            },
            parameters = {
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER)
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response create(
            @RequestBody @Valid ProjectPostCreateRequest req,
            @AuthenticationPrincipal User user
    ) {
        projectPostService.create(req, user);
        return Response.success();
    }

    @Operation(
            summary = "프로젝트 게시물 상세 조회",
            description = "프로젝트 게시물을 상세 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "프로젝트 게시물 조회 성공", content = @Content(schema = @Schema(implementation = ProjectPostResponse.class))),
            },
            parameters = {
                    @Parameter(name = "id", description = "프로젝트 게시물 ID", in = ParameterIn.PATH),
            }
    )
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response read(@PathVariable Long id) {
        return Response.success(projectPostService.read(id));
    }

    @Operation(
            summary = "프로젝트 게시물 삭제",
            description = "프로젝트 게시물을 삭제 합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "프로젝트 게시물 삭제 성공", content = @Content(schema = @Schema(implementation = Response.class))),
            },
            parameters = {
                    @Parameter(name = "id", description = "프로젝트 게시물 ID", in = ParameterIn.PATH),
            }
    )
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response delete(@PathVariable Long id) {
        projectPostService.delete(id);
        return Response.success();
    }

    @Operation(
            summary = "프로젝트 게시물 수정",
            description = "프로젝트 게시물을 수정 합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "프로젝트 게시물 수정 성공", content = @Content(schema = @Schema(implementation = Response.class))),
            },
            parameters = {
                    @Parameter(name = "id", description = "프로젝트 게시물 ID", in = ParameterIn.PATH),
            }
    )
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response update(
            @PathVariable Long id,
            @RequestBody @Valid ProjectPostUpdateRequest req) {
        projectPostService.update(id, req);
        return Response.success();
    }
}
