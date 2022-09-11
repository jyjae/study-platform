package com.example.studyplatform.controller.study;

import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.dto.study.PostStudyRequest;
import com.example.studyplatform.dto.study.PutStudyRequest;
import com.example.studyplatform.dto.study.StudyResponse;
import com.example.studyplatform.service.study.StudyService;
import io.openvidu.java.client.OpenVidu;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "study-controller", description = "스터디 API")
@RestController
@RequestMapping("/api/study")
public class StudyController {

    private final StudyService studyService;
    private OpenVidu openVidu;
    private String OPENVIDU_URL;
    private String SECRET;

    @Autowired
    public StudyController(
            StudyService studyService,
            @Value("${openvidu.secret}") String secret,
            @Value("${openvidu.url}") String openviduUrl
    ) {
        this.studyService = studyService;
        this.SECRET = secret;
        this.OPENVIDU_URL = openviduUrl;
        this.openVidu = new OpenVidu(OPENVIDU_URL, SECRET);
    }


    @Operation(
            summary = "스터디방 전체 조회",
            description = "유저가 보유하는 스터디 전체 조회",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "스터디 전체 조회 성공",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = StudyResponse.class))))
            }
    )
    @GetMapping()
    public Response list() {
        return Response.success(studyService.list());
    }

    @Operation(
            summary = "스터디방 한건 조회",
            description = "유저가 보유하는 스터디방 한건 조회",
            responses = {
                    @ApiResponse(responseCode = "200", description = "스터디 조회 성공", content = @Content(schema = @Schema(implementation = StudyResponse.class))),
                    @ApiResponse(responseCode = "404", description = "스터디를 찾을 수 없음", content = @Content(schema = @Schema(implementation = Response.class)))
            },
            parameters = {
                    @Parameter(name = "studyId", description = "스터디 ID", in = ParameterIn.PATH),
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER)
            }
    )
    @GetMapping("/{studyId}")
    public Response get(
            @PathVariable Long studyId
    ) {
        return Response.success(studyService.get(studyId));
    }

    @Operation(
            summary = "스터디방 생성",
            description = "방장이 스터디방 생성",
            responses = {
                    @ApiResponse(responseCode = "200", description = "스터디 생성 성공", content = @Content(schema = @Schema(implementation = StudyResponse.class)))
            },
            parameters = {
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER)
            }
    )
    @PostMapping()
    public Response create(
            @RequestBody @Valid PostStudyRequest req,
            @AuthenticationPrincipal User user
    ) {
        return Response.success(studyService.create(req, user));
    }

    @Operation(
            summary = "스터디방 수정",
            description = "방장이 스터디방을 수정",
            responses = {
                    @ApiResponse(responseCode = "200", description = "스터디 수정 성공", content = @Content(schema = @Schema(implementation = StudyResponse.class)))
            },
            parameters = {
                    @Parameter(name = "studyId", description = "스터디 ID", in = ParameterIn.PATH),
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER)
            }
    )
    @PatchMapping("/{studyId}")
    public Response update(
            @PathVariable Long studyId,
            @RequestBody PutStudyRequest req
    ) {
        return Response.success(studyService.update(req, studyId));
    }

    @Operation(
            summary = "스터디방 삭제",
            description = "스터디방 삭제",
            responses = {
                    @ApiResponse(responseCode = "200", description = "스터디 삭제 성공", content = @Content(schema = @Schema(implementation = Response.class)))
            },
            parameters = {
                    @Parameter(name = "studyId", description = "스터디 ID", in = ParameterIn.PATH),
            }
    )
    @DeleteMapping("/{studyId}")
    public Response delete(
            @PathVariable Long studyId
    ){
        return studyService.delete(studyId);
    }
}
