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
    @ExceptionHandler(ChatRoomCannotMakeRoomAloneException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response chatRoomCannotMakeRoomAloneException(ChatRoomCannotMakeRoomAloneException e) {
        return Response.failure(CHATROOM_CANNOT_MAKE_ROOM_ALONE.getCode(), CHATROOM_CANNOT_MAKE_ROOM_ALONE.getMessage());
    }

    @ExceptionHandler(ChatRoomGroupNotContainsUserException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response chatRoomGroupNotContainsUserException(ChatRoomGroupNotContainsUserException e) {
        return Response.failure(CHATROOM_GROUP_NOT_CONTAINS_USER.getCode(), CHATROOM_GROUP_NOT_CONTAINS_USER.getMessage());
    }

    @ExceptionHandler(ChatRoomNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response chatRoomNotFoundException(ChatRoomNotFoundException e) {
        return Response.failure(CHATROOM_NOT_FOUND.getCode(), CHATROOM_NOT_FOUND.getMessage());
    }

    @ExceptionHandler(ChatMessageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response chatMessageNotFound(ChatMessageNotFoundException e) {
        return Response.failure(CHAT_MESSAGE_NOT_FOUND.getCode(), CHAT_MESSAGE_NOT_FOUND.getMessage());
    }

    @ExceptionHandler(CommentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response commentNotFoundException(CommentNotFoundException e) {
        return Response.failure(COMMENT_NOT_FOUND.getCode(), COMMENT_NOT_FOUND.getMessage());
    }


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

    @ExceptionHandler(StudyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response studyNotFoundException(StudyNotFoundException e) {
        return Response.failure(STUDY_NOT_FOUND.getCode(), STUDY_NOT_FOUND.getMessage());
    }

    @ExceptionHandler(StudyNoticeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response studyNoticeNotFound(StudyNoticeNotFoundException e) {
        return Response.failure(STUDY_NOTICE_NOT_FOUND.getCode(), STUDY_NOTICE_NOT_FOUND.getMessage());
    }

    @ExceptionHandler(CalenderNotFoundException.class)
    public Response calenderNotFoundException(CalenderNotFoundException e) {
        return Response.failure(CALENDER_NOT_FOUND.getCode(), CALENDER_NOT_FOUND.getMessage());
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

    @ExceptionHandler(ProjectOrganizationDecreaseZeroException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response projectOrganizationDecreaseZeroException(ProjectOrganizationDecreaseZeroException e) {
        return Response.failure(PROJECT_ORGANIZATION_DECREASE_ZERO.getCode(), PROJECT_ORGANIZATION_DECREASE_ZERO.getMessage());
    }

    @ExceptionHandler(ProjectPostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response projectPostNotFoundException(ProjectPostNotFoundException e) {
        return Response.failure(PROJECT_POST_NOT_FOUND.getCode(), PROJECT_POST_NOT_FOUND.getMessage());
    }

    @ExceptionHandler(ProjectOrganizationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response projectOrganizationNotFoundException(ProjectOrganizationNotFoundException e) {
        return Response.failure(PROJECT_ORGANIZATION_NOT_FOUND.getCode(), PROJECT_ORGANIZATION_NOT_FOUND.getMessage());
    }

    @ExceptionHandler(AlarmNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response alarmNotFoundException(AlarmNotFoundException e) {
        return Response.failure(ALARM_NOT_FOUND.getCode(), ALARM_NOT_FOUND.getMessage());
    }
}
