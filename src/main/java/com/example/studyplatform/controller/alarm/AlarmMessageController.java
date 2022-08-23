package com.example.studyplatform.controller.alarm;

import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.chat.ChatMessageRequest;
import com.example.studyplatform.dto.comment.PostCommentRequest;
import com.example.studyplatform.service.chat.ChatMessageService;
import com.example.studyplatform.service.comment.CommentService;
import com.example.studyplatform.service.studyNotice.StudyNoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class AlarmMessageController {
    private final ChatMessageService chatMessageService;
    private final CommentService commentService;
    private final StudyNoticeService studyNoticeService;

    // 채팅 알람
    @MessageMapping("/chat/alarm")
    public void chatAlarm(ChatMessageRequest chatMessageRequest) {
        chatMessageService.sendChatAlarm(chatMessageRequest);
    }

    // 댓글 알림
    @MessageMapping("/comment/alarm")
    public void commentAlarm(PostCommentRequest postCommentRequest,
                             @AuthenticationPrincipal User user) {
        commentService.sendCommentAlarm(postCommentRequest, user);
    }

    // 스터디 공지 알림
    @MessageMapping("/study-notice/alarm/{studyId}")
    public void studyNoticeAlarm(
            @PathVariable Long studyId,
            @AuthenticationPrincipal User user
    ) {
        studyNoticeService.sendStudyNoticeAlarm(studyId, user);
    }
}
