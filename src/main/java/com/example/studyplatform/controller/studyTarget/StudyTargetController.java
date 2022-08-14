package com.example.studyplatform.controller.studyTarget;

import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.dto.studyTarget.PostStudyTargetRequest;
import com.example.studyplatform.dto.studyTarget.PutStudyTargetRequest;
import com.example.studyplatform.service.studyTarget.StudyTargetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/study-target")
public class StudyTargetController {

    private final StudyTargetService studyTargetService;

    @GetMapping("/{studyId}")
    public Response list(
        @PathVariable Long studyId
    ) {
        return Response.success(studyTargetService.list(studyId));
    }

    @PostMapping("/{studyId}")
    public Response create(
            @PathVariable Long studyId,
            @RequestBody PostStudyTargetRequest req) {
        return Response.success(studyTargetService.create(studyId, req));
    }

    @PatchMapping("/{studyTargetId}")
    public Response update(
            @PathVariable Long studyTargetId,
            @RequestBody PutStudyTargetRequest req
    ) {
        return Response.success(studyTargetService.update(studyTargetId, req));
    }

    @DeleteMapping("/{id}")
    public Response delete(
            @PathVariable Long id
    ){
        return studyTargetService.delete(id);
    }
}
