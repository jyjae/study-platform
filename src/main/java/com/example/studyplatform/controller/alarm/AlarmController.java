package com.example.studyplatform.controller.alarm;

import com.example.studyplatform.dto.alarm.AlarmResponse;
import com.example.studyplatform.dto.chatRoom.GetOneToOneChatRoomResponse;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.service.alarm.AlarmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@Tag(name = "alarm-controller", description = "알람 API")
@RequiredArgsConstructor
@RequestMapping("/api/alarms")
@RestController
public class AlarmController {
    private final AlarmService alarmService;

    @Operation(
            summary = "알람 전체 조회",
            description = "유저가 보유하고 있는 알람 전체 조회",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "알람 조회 성공",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = AlarmResponse.class)))
                    )
            },
            parameters = {
                    @Parameter(name = "cursorId", description = "커서", in = ParameterIn.PATH),
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER)
            }
    )
    @GetMapping(value = {"/{cursorId}", ""})
    public Response readAllByCursorId(
            @PathVariable(required = false) Long cursorId,
            @PageableDefault(size = 8) Pageable pageable
    ) {
        return Response.success(alarmService.readAllByCursorId(cursorId, pageable));
    }

    @Operation(
            summary = "안읽은 알람 전체 조회",
            description = "유저가 보유하고 있는 안읽은 알람 전체 조회",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "안읽은 알람 조회 성공",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = AlarmResponse.class)))
                    )
            },
            parameters = {
                    @Parameter(name = "size", description = "사이즈", in = ParameterIn.PATH),
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER)
            }
    )
    @GetMapping("/{size}/unread")
    public Response readAllUnReadAlarms(
            @PathVariable Integer size
    ) {
        return Response.success(alarmService.readAllUnReadAlarms(size));
    }

    @Operation(
            summary = "알람 읽은 표시 설정",
            description = "알람 읽은 표시 설정",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "알람 읽은 표시 설정 성공",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = AlarmResponse.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "알람이 존재하지 않습니다.",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Response.class)))
                    )
            },
            parameters = {
                    @Parameter(name = "id", description = "알람 아이디", in = ParameterIn.PATH),
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER)
            }
    )
    @PatchMapping("/{id}/read")
    public Response readAlarm(
            @PathVariable Long id
    ) {
        alarmService.readAlarm(id);
        return Response.success();
    }
}
