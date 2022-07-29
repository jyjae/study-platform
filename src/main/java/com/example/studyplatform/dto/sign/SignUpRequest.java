package com.example.studyplatform.dto.sign;

import com.example.studyplatform.dto.career.CareerCreateDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    private String username;

    private String nickname;

    private String email;

    private String password;

    private String profileImg;

    private List<CareerCreateDto> careers;

    // ID로 받아오는게 더 성능이 좋음
    private List<Long> techIds;
}
