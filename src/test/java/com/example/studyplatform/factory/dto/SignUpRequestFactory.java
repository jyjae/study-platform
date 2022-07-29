package com.example.studyplatform.factory.dto;

import com.example.studyplatform.dto.career.CareerCreateDto;
import com.example.studyplatform.dto.sign.SignUpRequest;

import java.util.ArrayList;
import java.util.List;

public class SignUpRequestFactory {
    public static SignUpRequest createSignUpRequest() {
        return new SignUpRequest("username", "nickname", "email", "password", "img", new ArrayList<>(), new ArrayList<>());
    }

    public static SignUpRequest createSignUpRequest(List<CareerCreateDto> careers, List<Long> techIds) {
        return new SignUpRequest("username", "nickname", "email", "password", "img", careers, techIds);
    }
}
