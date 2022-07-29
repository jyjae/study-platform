package com.example.studyplatform.factory.dto;

import com.example.studyplatform.dto.career.CareerCreateDto;

public class CareerCreateDtoFactory {
    public static CareerCreateDto createCareerCreateDto() {
        return new CareerCreateDto(1, 1L);
    }
}
