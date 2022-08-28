package com.example.studyplatform.controller.study;

import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.dto.study.PostStudyRequest;
import com.example.studyplatform.dto.study.PutStudyRequest;
import com.example.studyplatform.service.study.StudyService;
import io.openvidu.java.client.OpenVidu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/study")
public class StudyController {

    private final StudyService studyService;
    private OpenVidu openVidu;
    private String OPENVIDU_URL;
    private String SECRET;

    @Autowired
    public StudyController(
            StudyService studyService,
            @Value("${openvidu.secret}") String secret,
            @Value("${openvidu.url}") String openviduUrl
    ) {
        this.studyService = studyService;
        this.SECRET = secret;
        this.OPENVIDU_URL = openviduUrl;
        this.openVidu = new OpenVidu(OPENVIDU_URL, SECRET);
    }

    @GetMapping()
    public Response list() {
        return Response.success(studyService.list());
    }

    @GetMapping("/{studyId}")
    public Response get(
            @PathVariable Long studyId
    ) {
        return Response.success(studyService.get(studyId));
    }

    @PostMapping()
    public Response create(
            @RequestBody PostStudyRequest req,
            @AuthenticationPrincipal User user
    ) {
        return Response.success(studyService.create(req, user));
    }

    @PatchMapping("/{studyId}")
    public Response update(
            @PathVariable Long studyId,
            @RequestBody PutStudyRequest req
    ) {
        return Response.success(studyService.update(req, studyId));
    }


    @DeleteMapping("/{studyId}")
    public Response delete(
            @PathVariable Long studyId
    ){
        return studyService.delete(studyId);
    }
}
