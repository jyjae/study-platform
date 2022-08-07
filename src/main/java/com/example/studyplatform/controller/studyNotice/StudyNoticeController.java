package com.example.studyplatform.controller.studyNotice;

import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.dto.studyNotice.PostStudyNoticeRequest;
import com.example.studyplatform.dto.studyNotice.PutStudyNoticeRequest;
import com.example.studyplatform.service.studyNotice.StudyNoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/study-notice")
public class StudyNoticeController {

    private final StudyNoticeService studyNoticeService;

    @GetMapping()
    public Response list(){
        return Response.success(studyNoticeService.list());
    }

    @PostMapping("/{studyId}")
    public Response create(
            @PathVariable Long studyId,
            @AuthenticationPrincipal User user,
            @RequestBody PostStudyNoticeRequest req
    ) {
        return Response.success(studyNoticeService.create(studyId, user, req));
    }

    @PutMapping("/{studyNoticeId}")
    public Response update(
            @PathVariable Long studyNoticeId,
            @AuthenticationPrincipal User user,
            @RequestBody PutStudyNoticeRequest req
    ) {
        return Response.success(studyNoticeService.update(studyNoticeId, req, user));
    }

    @DeleteMapping("/{studyNoticeId}")
    public Response delete(
            @PathVariable Long studyNoticeId,
            @AuthenticationPrincipal User user
    ){
        studyNoticeService.delete(studyNoticeId, user);
        return Response.success();
    }

}
