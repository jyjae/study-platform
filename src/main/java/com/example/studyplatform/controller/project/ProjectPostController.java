package com.example.studyplatform.controller.project;

import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.project.ProjectPostCreateRequest;
import com.example.studyplatform.dto.project.ProjectPostUpdateRequest;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.service.project.ProjectPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/project-post")
@RestController
public class ProjectPostController {
    private final ProjectPostService projectPostService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response create(
            @RequestBody ProjectPostCreateRequest req,
            @AuthenticationPrincipal User user
    ) {
        projectPostService.create(req, user);
        return Response.success();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response read(@PathVariable Long id) {
        return Response.success(projectPostService.read(id));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response delete(@PathVariable Long id) {
        projectPostService.delete(id);
        return Response.success();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response update(
            @PathVariable Long id,
            @RequestBody ProjectPostUpdateRequest req) {
        projectPostService.update(id, req);
        return Response.success();
    }
}
