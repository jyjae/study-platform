package com.example.studyplatform.controller.scrap;

import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.dto.scrap.AddScrapRequest;
import com.example.studyplatform.dto.scrap.DeleteScrapRequest;
import com.example.studyplatform.service.scrap.ScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/scraps")
@RestController
public class ScrapController {
    private final ScrapService scrapService;

    @GetMapping
    public Response read(@AuthenticationPrincipal User user) {
        return Response.success(scrapService.readScrapBoardsByUserId(user.getId()));
    }

    @PatchMapping("/add")
    public Response add(@RequestBody AddScrapRequest req) {
        scrapService.addScrap(req);
        return Response.success();
    }

    @PatchMapping("/delete")
    public Response delete(@RequestBody DeleteScrapRequest req) {
        scrapService.deleteScrap(req);
        return Response.success();
    }
}
