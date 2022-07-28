package com.example.studyplatform.dto.sign;

import com.example.studyplatform.dto.career.CareerCreateDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SignUpRequest {
    private String username;

    private String nickname;

    private String email;

    private String password;

    private String profileImg;

    private List<CareerCreateDto> careers;

    private List<String> techNames;
}
