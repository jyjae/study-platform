package com.example.studyplatform.controller.studyTarget;

import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.dto.studyTarget.PostStudyTargetRequest;
import com.example.studyplatform.dto.studyTarget.PutStudyTargetRequest;
import com.example.studyplatform.dto.studyTarget.StudyTargetResponse;
import com.example.studyplatform.service.studyTarget.StudyTargetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "study-target-controller", description = "스터디 목표 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/study-target")
public class StudyTargetController {

    private final StudyTargetService studyTargetService;

    @Operation(
            summary = "스터디 목표 전체 조회",
            description = "스터디 목표를 전체조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "스터디 목표 전체 조회 성공",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = StudyTargetResponse.class)))
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
        return Response.success(studyTargetService.list(studyId));
    }

    @Operation(
            summary = "스터디 목표 생성",
            description = "스터디 목표를 생성합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "스터디목표 생성 성공", content = @Content(schema = @Schema(implementation = StudyTargetResponse.class)))
            },
            parameters = {
                    @Parameter(name = "studyId", description = "스터디 ID", in = ParameterIn.PATH),
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER)
            }
    )
    @PostMapping("/{studyId}")
    public Response create(
            @PathVariable Long studyId,
            @RequestBody PostStudyTargetRequest req) {
        return Response.success(studyTargetService.create(studyId, req));
    }

    @Operation(
            summary = "스터디 목표 수정",
            description = "스터디 목표를 수정합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "스터디목표 수정 성공", content = @Content(schema = @Schema(implementation = StudyTargetResponse.class)))
            },
            parameters = {
                    @Parameter(name = "studyTargetId", description = "스터디목표 ID", in = ParameterIn.PATH)
            }
    )
    @PatchMapping("/{studyTargetId}")
    public Response update(
            @PathVariable Long studyTargetId,
            @RequestBody PutStudyTargetRequest req
    ) {
        return Response.success(studyTargetService.update(studyTargetId, req));
    }

    @Operation(
            summary = "스터디 목표 삭제",
            description = "스터디 목표를 삭제합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "스터디목표 삭제 성공", content = @Content(schema = @Schema(implementation = Response.class)))
            },
            parameters = {
                    @Parameter(name = "studyTargetId", description = "스터디목표 ID", in = ParameterIn.PATH)
            }
    )
    @DeleteMapping("/{studyTargetId}")
    public Response delete(
            @PathVariable Long studyTargetId
    ){
        return studyTargetService.delete(studyTargetId);
    }
}
