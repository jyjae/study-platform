package com.example.studyplatform.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Response {
    private Boolean success;
    private int code;
    private Result result;

    public static Response success() {
        return new Response(true, 0, null);
    }

    public static <T> Response success(T data) {
        return new Response(true, 0, new Success<T>(data));
    }

    public static <T> Response failure(int code, String message) {
        return new Response(false, code, new Failure(message));
    }
}
