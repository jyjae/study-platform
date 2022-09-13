package com.example.studyplatform.controller.studyUser;

import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.dto.studyUser.StudyUserResponse;
import com.example.studyplatform.service.studyUser.StudyUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "study-user-controller", description = "스터디와 유저 관계 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/study-user")
public class StudyUserController {

    private final StudyUserService studyUserService;

    @Operation(
            summary = "스터디 유저 생성",
            description = "스터디와 유저에 중간테이블 역할을 합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "스터디유저 생성 성공", content = @Content(schema = @Schema(implementation = StudyUserResponse.class)))
            },
            parameters = {
                    @Parameter(name = "studyId", description = "스터디 ID", in = ParameterIn.PATH),
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER)
            }
    )
    @PostMapping("/{studyId}")
    public Response create(
            @PathVariable Long studyId,
            @AuthenticationPrincipal User user
    ){
        return studyUserService.create(studyId, user);
    }

    @Operation(
            summary = "스터디방 퇴장",
            description = "유저가 스터디방에서 퇴장합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "스터디방 유저 퇴장 성공", content = @Content(schema = @Schema(implementation = Response.class)))
            },
            parameters = {
                    @Parameter(name = "studyId", description = "스터디 ID", in = ParameterIn.PATH),
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER)
            }
    )
    @PatchMapping("/{studyId}")
    public Response exitStudy(
            @PathVariable Long studyId,
            @AuthenticationPrincipal User user
    ){
        return studyUserService.exitStudy(studyId, user);
    }
}
