package com.example.studyplatform.controller.oauth;

import com.example.studyplatform.dto.comment.GetCommentResponse;
import com.example.studyplatform.dto.oauth.PostOauthLoginRequest;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.service.oauth.GithubService;
import com.example.studyplatform.service.oauth.KakaoService;
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

import javax.validation.constraints.NotBlank;

@Tag(name = "oauth-controller", description = "소셜 로그인 API")
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
@RestController
@CrossOrigin("*")
public class OauthController {
    private final KakaoService kakaoService;
    private final GithubService githubService;

    @Operation(
            summary = "소셜 로그인",
            description = "소셜 로그인",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "소셜 로그인 성공",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = GetCommentResponse.class)))
                    )
            },
            parameters = {
                    @Parameter(name = "provider-name", description = "소셜 로그인 종류 (kakao / github)", in = ParameterIn.PATH),
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER)
            }
    )
    @PostMapping("/{provider-name}")
    public Response kakaoLogin(
            @PathVariable(name = "provider-name") @NotBlank(message = "소셜 로그인 종류를 입력해주세요") String providerName,
            PostOauthLoginRequest req) {
        if(providerName.equals("kakao")) {
            return Response.success(kakaoService.login(req.getCode()));
        }else {
            return Response.success(githubService.login(req.getCode()));
        }
    }


}
