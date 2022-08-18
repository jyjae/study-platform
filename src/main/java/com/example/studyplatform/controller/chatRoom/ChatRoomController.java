package com.example.studyplatform.controller.chatRoom;

import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.chatRoom.GetOneToOneChatRoomResponse;
import com.example.studyplatform.dto.chatRoom.PostGroupChatRoomRequest;
import com.example.studyplatform.dto.chatRoom.PostOneToOneChatRoomRequest;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.exception.ChatRoomCannotMakeRoomAloneException;
import com.example.studyplatform.exception.ChatRoomGroupNotContainsUserException;
import com.example.studyplatform.service.chat.RedisRepository;
import com.example.studyplatform.service.chatRoom.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat-rooms")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final RedisRepository redisRepository;

    // 유저가 보유하고 있는 일대일 채팅방들만 조회
    @GetMapping
    public Response getOneToOneChatRooms(
            @AuthenticationPrincipal User user,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
            ) {
        // TODO : 로직 작성
        return Response.success(chatRoomService.getOneToOneChatRooms(user, pageable));
    }

    @GetMapping("/group/{study-id}")
    public Response getGroupChatRooms(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "study-id") Long studyId
    ) {
        return Response.success(chatRoomService.getGroupChatRooms(user, studyId));
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
        redisRepository.initChatRoomMessageInfo(chatRoomId+"", myUserId);
        redisRepository.initChatRoomMessageInfo(chatRoomId+"", chatPartnerUserId);

        return Response.success();
    }

    // 그룹 채팅방 생성
    @PostMapping("/group")
    public Response createGroupChatRoom(
            @RequestBody PostGroupChatRoomRequest req,
            @AuthenticationPrincipal User user) {

        // req에 방장(= user) 아이디도 존재해야함
        if(!req.getAnotherUserIds().contains(user.getId())) throw new ChatRoomGroupNotContainsUserException();

        chatRoomService.createGroupChatRoom(req);

        return Response.success();
    }

    // 이전 채팅 메시지 불러오기
    @GetMapping("/{roomId}/messages")
    public Response getMessages(
            @PathVariable Long roomId,
            @AuthenticationPrincipal User user
    ) {
        return Response.success(chatRoomService.getPreviousChatMessage(roomId, user));
    }

    // 이전 일대일 채팅 메시지 불러오기
    @GetMapping("/otherUserInfo/{roomId}")
    public Response getOtherUserInfo(
            @PathVariable Long roomId,
            @AuthenticationPrincipal User user
    ) {
        return Response.success(chatRoomService.getOtherUserInfo(roomId, user));
    }

    // 이전 그룹 채팅 메시지 불러오기
    @GetMapping("/otherUserInfo/{studyId}")
    public Response getOtherUserInfos(
            @PathVariable Long studyId,
            @AuthenticationPrincipal User user
    ) {
        return Response.success(chatRoomService.getOtherUserInfos(studyId, user));
    }


    //채팅방 삭제
    @PatchMapping("/{roomId}")
    public Response deleteChatRoom(
           @RequestParam Long roomId,
           @AuthenticationPrincipal User user
    ) {
        chatRoomService.deleteChatRoom(roomId, user);
        return Response.success();
    }
}
