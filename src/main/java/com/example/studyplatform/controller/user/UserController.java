package com.example.studyplatform.controller.user;

import com.example.studyplatform.dto.login.LoginRequest;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.dto.sign.SignUpRequest;
import com.example.studyplatform.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/api/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public Response signUp(@RequestBody SignUpRequest req){
        userService.signUp(req);
        return Response.success();
    }

    @PostMapping("/api/login")
    public Response login(@RequestBody LoginRequest req) {
        return Response.success(userService.login(req));
    }

}
