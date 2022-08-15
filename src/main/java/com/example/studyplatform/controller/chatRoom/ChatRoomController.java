package com.example.studyplatform.controller.chatRoom;

import com.example.studyplatform.dto.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat-rooms")
public class ChatRoomController {

    // 유저가 보유하고 있는 채팅방들만 조회
    @GetMapping("/{userId}")
    public Response getChatRoom(
            @PathVariable Long userId
    ) {
        // TODO : 로직 작성
        return Response.success();
    }

    //채팅방 생성
    @PostMapping
    public Response createChatRoom(
            //TODO : DTO 작성
    ) {
        //TODO : 로직 작성
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
