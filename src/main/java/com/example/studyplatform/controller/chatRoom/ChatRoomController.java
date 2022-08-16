package com.example.studyplatform.controller.chatRoom;

import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.chatRoom.PostGroupChatRoomRequest;
import com.example.studyplatform.dto.chatRoom.PostOneToOneChatRoomRequest;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.exception.ChatRoomCannotMakeRoomAloneException;
import com.example.studyplatform.exception.ChatRoomGroupNotContainsUserException;
import com.example.studyplatform.service.chat.RedisRepository;
import com.example.studyplatform.service.chatRoom.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat-rooms")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final RedisRepository redisRepository;

    // 유저가 보유하고 있는 채팅방들만 조회
    @GetMapping("/{userId}")
    public Response getChatRoom(
            @PathVariable Long userId
    ) {
        // TODO : 로직 작성
        return Response.success();
    }

    //일대일 채팅방 생성
    @PostMapping
    public Response createOneToOneChatRoom(
            @RequestBody PostOneToOneChatRoomRequest req,
            @AuthenticationPrincipal User user
    ) {
        // user, another user validation check
        if(req.getAnotherUserId() == user.getId()) throw new ChatRoomCannotMakeRoomAloneException();

        Long chatRoomId = chatRoomService.createChatRoom(req, user);
        Long chatPartnerUserId = req.getAnotherUserId();
        Long myUserId = user.getId();

        // 일대일 채팅은 안읽은 갯수 필요
        redisRepository.initChatRoomMessageInfo(chatRoomId, myUserId);
        redisRepository.initChatRoomMessageInfo(chatRoomId, chatPartnerUserId);

        return Response.success();
    }

    // 그룹 채팅방 생성
    @PostMapping
    public Response createGroupChatRoom(
            @RequestBody PostGroupChatRoomRequest req,
            @AuthenticationPrincipal User user) {

        // req에 방장(= user) 아이디도 존재해야함
        if(!req.getAnotherUserIds().contains(user.getId())) throw new ChatRoomGroupNotContainsUserException();

        chatRoomService.createGroupChatRoom(req);

        return Response.success();
    }

    //채팅방 삭제
    @DeleteMapping()
    public Response deleteChatRoom(
            //TODO : DTO 작성
    ) {
        // TODO : 로직 작성
        return Response.success();
    }
}
