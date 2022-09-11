package com.example.studyplatform.controller.calender;

import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.calender.CalenderResponse;
import com.example.studyplatform.dto.calender.PostCalenderRequest;
import com.example.studyplatform.dto.calender.PutCalenderRequest;
import com.example.studyplatform.dto.calender.SimpleCalenderResponse;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.service.calender.CalenderService;
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

@Tag(name = "calender-controller", description = "캘린더 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/calender")
public class CalenderController {

    private final CalenderService calenderService;

    @Operation(
            summary = "캘린더 전체 조회",
            description = "하나의 스터디방에 캘린더를 전체조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "캘린더 전체 조회 성공",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = SimpleCalenderResponse.class)))
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
        return Response.success(calenderService.list(studyId));
    }

    @Operation(
            summary = "캘린더 한건 조회",
            description = "스터디방에 캘린더 한건 상세 조회",
            responses = {
                    @ApiResponse(responseCode = "200", description = "캘린더 조회 성공", content = @Content(schema = @Schema(implementation = CalenderResponse.class))),
            },
            parameters = {
                    @Parameter(name = "calenderId", description = "캘린더 ID", in = ParameterIn.PATH)
            }
    )
    @GetMapping("/{calenderId}")
    public Response get(
            @PathVariable Long calenderId
    ) {
        return Response.success(calenderService.get(calenderId));
    }

    @Operation(
            summary = "캘린더 생성",
            description = "캘린더를 생성합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "캘린더 생성 성공", content = @Content(schema = @Schema(implementation = CalenderResponse.class)))
            },
            parameters = {
                    @Parameter(name = "studyId", description = "스터디 ID", in = ParameterIn.PATH),
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER)
            }
    )
    @PostMapping("/{studyId}")
    public Response create(
            @PathVariable Long studyId,
            @RequestBody PostCalenderRequest req,
            @AuthenticationPrincipal User user
    ) {
        return Response.success(calenderService.create(studyId, user, req));
    }

    @Operation(
            summary = "캘린더 수정",
            description = "캘린더를 수정합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "캘린더 수정 성공", content = @Content(schema = @Schema(implementation = CalenderResponse.class)))
            },
            parameters = {
                    @Parameter(name = "calenderId", description = "캘린더 ID", in = ParameterIn.PATH),
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER)
            }
    )
    @PutMapping("/{calenderId}")
    public Response update(
            @PathVariable Long calenderId,
            @RequestBody PutCalenderRequest req,
            @AuthenticationPrincipal User user
    ) {
        return Response.success(calenderService.update(calenderId, user, req));
    }

    @Operation(
            summary = "캘린더 삭제",
            description = "캘린더를 삭제합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "캘린더 삭제 성공", content = @Content(schema = @Schema(implementation = Response.class)))
            },
            parameters = {
                    @Parameter(name = "calenderId", description = "캘린더 ID", in = ParameterIn.PATH)
            }
    )
    @DeleteMapping("/{calenderId}")
    public Response delete(
            @PathVariable Long calenderId
    ) {
        return calenderService.delete(calenderId);
    }
}
