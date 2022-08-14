package com.example.studyplatform.controller.studyUser;

import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.service.studyUser.StudyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/study-user")
public class StudyUserController {

    private final StudyUserService studyUserService;

    @PostMapping("/{studyId}")
    public Response create(
            @PathVariable Long studyId,
            @AuthenticationPrincipal User user
    ){
        return studyUserService.create(studyId, user);
    }

    @PatchMapping("/{studyId}")
    public Response exitStudy(
            @PathVariable Long studyId,
            @AuthenticationPrincipal User user
    ){
        return studyUserService.exitStudy(studyId, user);
    }
}
