package com.example.studyplatform.controller.comment;

import com.example.studyplatform.constant.comment.CommentType;
import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.comment.PostCommentRequest;
import com.example.studyplatform.dto.comment.PutCommentRequest;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    public Response getCommentAll(
            @RequestParam("board-id") Long boardId
    ) {
        return Response.success(commentService.getCommentAll(boardId));
    }

    @PostMapping
    public Response createComment(
            @RequestBody PostCommentRequest postCommentRequest,
            @AuthenticationPrincipal User user) {
        return Response.success(commentService.createComment(postCommentRequest, user));
    }

    @PutMapping
    public Response modifyComment(
            @RequestBody PutCommentRequest putCommentRequest,
            @AuthenticationPrincipal User user
    ) {
        return Response.success(commentService.modifyComment(putCommentRequest, user));
    }

    @PutMapping("/{id}")
    public Response deleteComment(
            @PathVariable("id") Long commentId,
            @AuthenticationPrincipal User user
    ) {
        commentService.deleteComment(commentId, user);
        return Response.success();
    }
}
