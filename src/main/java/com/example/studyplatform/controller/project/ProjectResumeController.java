package com.example.studyplatform.controller.project;

import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.project.ProjectResumeApprovalRequest;
import com.example.studyplatform.dto.project.ProjectResumeCreateRequest;
import com.example.studyplatform.dto.project.ProjectResumeDeleteRequest;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.service.project.ProjectResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/project-resume")
@RestController
public class ProjectResumeController {
    private final ProjectResumeService projectResumeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response create(
            ProjectResumeCreateRequest req,
            @AuthenticationPrincipal User user
    ) {
        projectResumeService.create(req, user);
        return Response.success();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response read(@PathVariable("id") Long resumeId) {
        return Response.success(projectResumeService.read(resumeId));
    }

    @PatchMapping("/{id}/approval")
    @ResponseStatus(HttpStatus.OK)
    public Response approvalResume(
            @PathVariable("id") Long resumeId,
            @RequestBody ProjectResumeApprovalRequest req
    ) {
        projectResumeService.approvalResume(resumeId, req);
        return Response.success();
    }

    @PatchMapping("/{id}/denied")
    @ResponseStatus(HttpStatus.OK)
    public Response deniedResume(
            @PathVariable("id") Long resumeId
    ) {
        projectResumeService.deniedResume(resumeId);
        return Response.success();
    }

    @PatchMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    public Response deleteAppliedResume(
            @PathVariable("id") Long resumeId,
            @RequestBody ProjectResumeDeleteRequest req
    ) {
        projectResumeService.deleteAppliedResume(resumeId, req);
        return Response.success();
    }
}
