package com.example.studyplatform.controller.studyApply;

import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.dto.studyApply.PostStudyApplyRequest;
import com.example.studyplatform.dto.studyApply.PutStudyApplyRequest;
import com.example.studyplatform.service.studyApply.StudyApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/study-applies")
@RestController
public class StudyApplyController {
    private final StudyApplyService studyApplyService;

    @PostMapping
    public Response applyStudy(
            @RequestBody PostStudyApplyRequest req,
            @AuthenticationPrincipal User user
    ) {
        return Response.success(studyApplyService.applyStudy(req, user));
    }

    @PutMapping("/{id}")
    public Response deleteApply(
            @PathVariable("id") Long applyId,
            @AuthenticationPrincipal User user
    ) {
        studyApplyService.deleteApply(applyId, user);
        return Response.success();
    }

    @PutMapping
    public Response acceptOrReject(
            @RequestBody PutStudyApplyRequest req,
            @AuthenticationPrincipal User user
    ) {
        return Response.success(studyApplyService.modifyApplyStatus(req, user));
    }

}
