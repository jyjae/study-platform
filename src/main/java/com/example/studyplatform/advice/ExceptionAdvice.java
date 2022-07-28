package com.example.studyplatform.advice;

import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.exception.TechStackNotFoundException;
import com.example.studyplatform.exception.UserEmailAlreadyExistsException;
import com.example.studyplatform.exception.UserNicknameAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.example.studyplatform.advice.ErrorCode.*;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(TechStackNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response techStackNotFoundException(TechStackNotFoundException e) {
        return Response.failure(TECH_STACK_NOT_FOUND.getCode(), TECH_STACK_NOT_FOUND.getMessage());
    }

    @ExceptionHandler(UserEmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response userEmailAlreadyExistsException(UserEmailAlreadyExistsException e) {
        return Response.failure(USER_EMAIL_ALREADY_EXISTS.getCode(), USER_EMAIL_ALREADY_EXISTS.getMessage());
    }

    @ExceptionHandler(UserNicknameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response userNicknameAlreadyExistsException(UserNicknameAlreadyExistsException e) {
        return Response.failure(USER_NICKNAME_ALREADY_EXISTS.getCode(), USER_NICKNAME_ALREADY_EXISTS.getMessage());
    }
}
