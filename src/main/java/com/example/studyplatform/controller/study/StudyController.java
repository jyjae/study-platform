package com.example.studyplatform.controller.study;

import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.dto.study.PostStudyRequest;
import com.example.studyplatform.dto.study.PutStudyRequest;
import com.example.studyplatform.service.study.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/study")
public class StudyController {

    private final StudyService studyService;

    @GetMapping()
    public Response list() {
        return Response.success(studyService.list());
    }

    @GetMapping("/{id}")
    public Response get(
            @PathVariable Long id
    ) {
        return Response.success(studyService.get(id));
    }

    @PostMapping()
    public Response create(
            @RequestBody PostStudyRequest req,
            @AuthenticationPrincipal User user
    ) {
        return Response.success(studyService.create(req, user));
    }

    @PutMapping("/{id}")
    public Response update(
            @PathVariable Long id,
            @RequestBody PutStudyRequest req
    ) {
        return Response.success(studyService.update(req, id));
    }


    @DeleteMapping("/{id}")
    public Response delete(
            @PathVariable Long id
    ){
        return studyService.delete(id);
    }
}
