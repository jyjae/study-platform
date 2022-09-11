package com.example.studyplatform.controller.user;

import com.example.studyplatform.dto.login.LoginRequest;
import com.example.studyplatform.dto.login.LoginResponse;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.dto.sign.SignUpRequest;
import com.example.studyplatform.dto.studyBoard.StudyBoardResponse;
import com.example.studyplatform.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "user-controller", description = "유저 API")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;


    @Operation(
            summary = "일반 유저 회원가입",
            description = "일반 유저 회원가입",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "일반 유저 회원가입 성공",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = LoginResponse.class)))
                    )
            }
    )
    @PostMapping("/api/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public Response signUp(@RequestBody SignUpRequest req){
        userService.signUp(req);
        return Response.success();
    }


    @Operation(
            summary = "일반 유저 로그인",
            description = "일반 유저 로그인",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "일반 유저 로그인 성공",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = LoginResponse.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "존재하지 않은 유저",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Response.class)))
                    )
            }
    )
    @PostMapping("/api/login")
    public Response login(@RequestBody @Valid LoginRequest req) {
        return Response.success(userService.login(req));
    }

}
