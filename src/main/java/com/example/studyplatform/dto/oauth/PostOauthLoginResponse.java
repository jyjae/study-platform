package com.example.studyplatform.dto.oauth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostOauthLoginResponse {
    private Long userId;

    private String jwt;

    public static PostOauthLoginResponse toDto(
            Long userIdx,
            String jwt
    ) {
        return new PostOauthLoginResponse(userIdx, jwt);
    }
}
