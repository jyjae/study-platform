package com.example.studyplatform.dto.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private Long userId;

    private String jwt;

    public static LoginResponse toDto(
            Long userIdx,
            String jwt
    ) {
        return new LoginResponse(userIdx, jwt);
    }
}
