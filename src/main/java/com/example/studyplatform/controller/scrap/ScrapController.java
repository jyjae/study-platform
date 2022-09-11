package com.example.studyplatform.controller.scrap;

import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.alarm.AlarmResponse;
import com.example.studyplatform.dto.board.BoardDto;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.dto.scrap.AddScrapRequest;
import com.example.studyplatform.dto.scrap.DeleteScrapRequest;
import com.example.studyplatform.service.scrap.ScrapService;
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

@Tag(name = "scrap-controller", description = "스크랩 API")
@RequiredArgsConstructor
@RequestMapping("/api/scraps")
@RestController
public class ScrapController {
    private final ScrapService scrapService;

    @Operation(
            summary = "스크랩한 게시글 정보 조회",
            description = "스크랩한 게시글 정보 조회",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "스크랩한 게시글 정보 조회 성공",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = BoardDto.class)))
                    )
            },
            parameters = {
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER)
            }
    )
    @GetMapping
    public Response read(@AuthenticationPrincipal User user) {
        return Response.success(scrapService.readScrapBoardsByUserId(user.getId()));
    }

    @Operation(
            summary = "스크랩 생성",
            description = "스크랩 생성",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "스크랩 생성 성공",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = BoardDto.class)))
                    )
            },
            parameters = {
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER)
            }
    )
    @PatchMapping("/add")
    public Response add(@RequestBody @Valid AddScrapRequest req) {
        scrapService.addScrap(req);
        return Response.success();
    }

    @Operation(
            summary = "스크랩 삭제",
            description = "스크랩 삭제",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "스크랩 삭제 성공",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = BoardDto.class)))
                    )
            },
            parameters = {
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER)
            }
    )
    @PatchMapping("/delete")
    public Response delete(@RequestBody @Valid DeleteScrapRequest req) {
        scrapService.deleteScrap(req);
        return Response.success();
    }
}
