package com.example.studyplatform.advice;

import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.example.studyplatform.advice.ErrorCode.*;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(StudyApplyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response studyApplyNotFoundException(StudyApplyNotFoundException e) {
        return Response.failure(STUDY_APPLY_NOT_FOUND.getCode(), STUDY_APPLY_NOT_FOUND.getMessage());
    }


    @ExceptionHandler(StudyTechStackNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response studyTechStackNotFoundException(StudyTechStackNotFoundException e) {
        return Response.failure(STUDY_TECH_STACK_NOT_FOUND.getCode(), STUDY_TECH_STACK_NOT_FOUND.getMessage());
    }

    @ExceptionHandler(StudyBoardNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response studyBoardNotFoundException(StudyBoardNotFoundException e) {
        return Response.failure(STUDY_BOARD_NOT_FOUND.getCode(), STUDY_BOARD_NOT_FOUND.getMessage());
    }

    @ExceptionHandler(TechStackNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response techStackNotFoundException(TechStackNotFoundException e) {
        return Response.failure(TECH_STACK_NOT_FOUND.getCode(), TECH_STACK_NOT_FOUND.getMessage());
    }

    @ExceptionHandler(UserEmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response userEmailAlreadyExistsException(UserEmailAlreadyExistsException e) {
        return Response.failure(USER_EMAIL_ALREADY_EXISTS.getCode(), USER_EMAIL_ALREADY_EXISTS.getMessage());
    }

    @ExceptionHandler(UserNicknameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response userNicknameAlreadyExistsException(UserNicknameAlreadyExistsException e) {
        return Response.failure(USER_NICKNAME_ALREADY_EXISTS.getCode(), USER_NICKNAME_ALREADY_EXISTS.getMessage());
    }

    @ExceptionHandler(CareerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response careerNotFoundException(CareerNotFoundException e) {
        return Response.failure(CAREER_NOT_FOUND.getCode(), CAREER_NOT_FOUND.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response userNotFoundException(UserNotFoundException e) {
        return Response.failure(USER_NOT_FOUND.getCode(), USER_NOT_FOUND.getMessage());
    }
}
