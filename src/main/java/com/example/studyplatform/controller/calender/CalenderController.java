package com.example.studyplatform.controller.calender;

import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.calender.PostCalenderRequest;
import com.example.studyplatform.dto.calender.PutCalenderRequest;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.service.calender.CalenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/calender")
public class CalenderController {

    private final CalenderService calenderService;

    @PostMapping("/{studyId}")
    public Response create(
            @PathVariable Long studyId,
            @RequestBody PostCalenderRequest req,
            @AuthenticationPrincipal User user
    ) {
        return Response.success(calenderService.create(studyId, user, req));
    }

    @PutMapping("/{calenderId}")
    public Response update(
            @PathVariable Long calenderId,
            @RequestBody PutCalenderRequest req,
            @AuthenticationPrincipal User user
    ) {
        return Response.success(calenderService.update(calenderId, user, req));
    }

    @DeleteMapping("/{calenderId}")
    public Response delete(
            @PathVariable Long calenderId
    ){
        return calenderService.delete(calenderId);
    }


}
