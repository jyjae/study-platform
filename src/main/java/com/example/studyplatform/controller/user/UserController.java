package com.example.studyplatform.controller.user;

import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.dto.sign.SignUpRequest;
import com.example.studyplatform.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {
    private UserService userService;

    @PostMapping("/api/signup")
    public Response signUp(@RequestBody SignUpRequest req){
        userService.signUp(req);
        return Response.success();
    }
}
