package com.example.studyplatform.controller.sign;

import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.dto.sign.SignUpRequest;
import com.example.studyplatform.service.sign.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SignController {
    private SignService signService;

    @PostMapping("/api/signup")
    public Response signUp(@RequestBody SignUpRequest req){
        signService.signUp(req);
        return Response.success();
    }
}
