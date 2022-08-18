package com.example.studyplatform.controller.alarm;

import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.service.alarm.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/alarms")
@RestController
public class AlarmController {
    private final AlarmService alarmService;

    @GetMapping("/{cursorId}")
    public Response readAllByCursorId(
            @PathVariable Long cursorId,
            @PageableDefault(size = 8) Pageable pageable
    ) {
        return Response.success(alarmService.readAllByCursorId(cursorId, pageable));
    }

    @GetMapping("/{size}/unread")
    public Response readAllUnReadAlarms(
            @PathVariable Integer size
    ) {
        return Response.success(alarmService.readAllUnReadAlarms(size));
    }
}
