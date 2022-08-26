package com.example.studyplatform.controller.oauth;

import com.example.studyplatform.dto.oauth.PostOauthLoginRequest;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.service.oauth.GithubService;
import com.example.studyplatform.service.oauth.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/oauth")
@RestController
public class OauthController {
    private final KakaoService kakaoService;
    private final GithubService githubService;

    @PostMapping("/{provider-name}")
    public Response kakaoLogin(
            @PathVariable(name = "provider-name") String providerName,
            PostOauthLoginRequest req) {
        if(providerName.equals("kakao")) {
            return Response.success(kakaoService.login(req.getCode()));
        }else {
            return Response.success(githubService.login(req.getCode()));
        }
    }


}
