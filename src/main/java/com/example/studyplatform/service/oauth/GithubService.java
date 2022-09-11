package com.example.studyplatform.service.oauth;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.constant.oauth.ProviderName;
import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.domain.user.UserRepository;
import com.example.studyplatform.dto.oauth.PostOauthLoginResponse;
import com.example.studyplatform.properties.oauth.Oauth2ClientProperties;
import com.example.studyplatform.service.jwt.JwtService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class GithubService {
    private final RestTemplate restTemplate;
    private final Oauth2ClientProperties properties;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public GithubService(RestTemplate restTemplate, Oauth2ClientProperties properties, JwtService jwtService, UserRepository userRepository) {
        this.restTemplate = restTemplate;
        this.properties = properties;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public PostOauthLoginResponse login(String authorizedCode) {
        String accessToken =  getGithubAccessToken(authorizedCode);

        Map<String, Object> userInfo = getUserInfo(accessToken);
        Map<String, Object> kakaoAccount =
                (LinkedHashMap) userInfo.get("kakao_account");

        long userIdx = loginCheck(kakaoAccount);

        String jwt = jwtService.createJwt(userIdx);

        return PostOauthLoginResponse.toDto(userIdx,jwt);

    }

    public Map<String, Object> getUserInfo(String accessToken){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "token " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<HttpHeaders> githubRequest = new HttpEntity<>(headers);

        ResponseEntity<Map> response = null;

        response = restTemplate.postForEntity(
                "https://api.github.com/user",
                githubRequest,
                Map.class
        );


        return response.getBody();
    }

    private long loginCheck(Map<String, Object> githubAccount) {
        long userIdx = 0;

        String email = (String)githubAccount.get("email");
        String name = (String)githubAccount.get("name");

        Optional<User> savedUser = userRepository.findByEmailAndProviderNameAndStatus(
                email,
                ProviderName.KAKAO,
                Status.ACTIVE
        );

        if(!savedUser.isPresent()) {
            String nickname = getNickName(email);

            User user = User.builder()
                    .username(name)
                    .nickname(nickname)
                    .email(email)
                    .providerName(ProviderName.GITHUB)
                    .build();

            userIdx = userRepository.save(user).getId();

        }else {
            userIdx = savedUser.get().getId();
        }

        return userIdx;
    }

    private String getNickName(String email) {
        return email.split("@")[0]+"k"+ (int)(Math.random()*100);
    }


    public String getGithubAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", "399d0fa02a0200f5b814");
        params.add("client_secret", "76564538f4c5bf5fce57e00f0e99d100e32371b5");
        params.add("redirect_uri","http://54.180.148.170:8000/Redirect");
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> githubTokenRequest = new HttpEntity<>(params, headers);

        // 실제 요청
        ResponseEntity<Map> response =restTemplate.postForEntity(
                "https://github.com/login/oauth/access_token",
                githubTokenRequest,
                Map.class
        );


        return (String)response.getBody().get("access_token");
    }
}
