package com.example.studyplatform.controller.chatRoom;

import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.chatRoom.GetOneToOneChatRoomResponse;
import com.example.studyplatform.dto.chatRoom.GetRoomOtherUserInfoResponse;
import com.example.studyplatform.dto.chatRoom.PostGroupChatRoomRequest;
import com.example.studyplatform.dto.chatRoom.PostOneToOneChatRoomRequest;
import com.example.studyplatform.dto.response.Response;
import com.example.studyplatform.exception.ChatRoomCannotMakeRoomAloneException;
import com.example.studyplatform.exception.ChatRoomGroupNotContainsUserException;
import com.example.studyplatform.service.chat.RedisRepository;
import com.example.studyplatform.service.chatRoom.ChatRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Tag(name = "chat-room-controller", description = "채팅방 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat-rooms")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final RedisRepository redisRepository;

    // 유저가 보유하고 있는 일대일 채팅방들만 조회
    @Operation(
            summary = "일대일 채팅방들 전체 조회",
            description = "유저가 보유하고 있는 일대일 채팅방들 전체 조회",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "일대일 채팅방 전체 조회 성공",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = GetOneToOneChatRoomResponse.class)))
                    )
            },
            parameters = {
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER)
            }
    )
    @GetMapping
    public Response getOneToOneChatRooms(
            @AuthenticationPrincipal User user,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) @Schema(nullable = true) Pageable pageable
    ) {
        return Response.success(chatRoomService.getOneToOneChatRooms(user, pageable));
    }


    @Operation(
            summary = "그룹 채팅방들 전체 조회",
            description = "유저가 보유하고 있는 그룹 채팅방들 전체 조회",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "그룹 채팅방 전체 조회 성공",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = GetOneToOneChatRoomResponse.class)))
                    )
            },
            parameters = {
                    @Parameter(name = "study-id", description = "스터디 ID", in = ParameterIn.PATH, required = true),
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER)
            }
    )
    @GetMapping("/group/{study-id}")
    public Response getGroupChatRooms(
            @AuthenticationPrincipal User user,
            @PathVariable(name = "study-id") @NotBlank(message = "스터디 아이디를 입력해주세요") Long studyId
    ) {
        return Response.success(chatRoomService.getGroupChatRooms(user, studyId));
    }

    //일대일 채팅방 생성
    @Operation(
            summary = "일대일 채팅방 생성",
            description = "일대일 채팅방 생성",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "일대일 채팅방 생성 성공",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Long.class)))
                    ),
                    @ApiResponse(
                            responseCode = "-1004",
                            description = "상대방 유저 존재하지 않음",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Response.class)))
                    ),
                    @ApiResponse(
                            responseCode = "-3000",
                            description = "상대방 유저 아이디 입력하지 않음",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Response.class)))
                    )
            },
            parameters = {
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER, required = true)
            }
    )
    @PostMapping
    public Response createOneToOneChatRoom(
            @Valid @RequestBody PostOneToOneChatRoomRequest req,
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
    @Operation(
            summary = "그룹 채팅방 생성",
            description = "그룹 채팅방 생성",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "그룹 채팅방 생성 성공",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Long.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "상대방 유저 존재하지 않음",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Response.class)))
                    ),
                    @ApiResponse(
                            responseCode = "-3000",
                            description = "Validation 실패",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Response.class)))
                    ),
                    @ApiResponse(
                            responseCode = "-1321",
                            description = "해당 유저 아이디도 그룹 유저 아이디에 입력해야함",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Response.class)))
                    )
            },
            parameters = {
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER, required = true)
            }
    )
    @PostMapping("/group")
    public Response createGroupChatRoom(
            @RequestBody @Valid PostGroupChatRoomRequest req,
            @AuthenticationPrincipal User user) {

        // req에 방장(= user) 아이디도 존재해야함
        if(!req.getAnotherUserIds().contains(user.getId())) throw new ChatRoomGroupNotContainsUserException();

        chatRoomService.createGroupChatRoom(req);

        return Response.success();
    }

    // 이전 채팅 메시지 불러오기
    @Operation(
            summary = "유저의 특정 채팅방의 메시지 조회",
            description = "유저가 보유하고 있는 유저의 특정 채팅방의 메시지 조회",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "채팅방 메시지 조회 성공",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = GetOneToOneChatRoomResponse.class)))
                    )
            },
            parameters = {
                    @Parameter(name = "roomId", description = "채팅방 ID", in = ParameterIn.PATH, required = true),
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER)
            }
    )
    @GetMapping("/{roomId}/messages")
    public Response getMessages(
            @PathVariable Long roomId,
            @AuthenticationPrincipal User user
    ) {
        return Response.success(chatRoomService.getPreviousChatMessage(roomId, user));
    }

    @Operation(
            summary = "일대일 채팅방의 다른 유저들 정보 조회",
            description = "일대일 채팅방의 다른 유저들 정보 조회",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "유저 정보 조회 성공",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = GetRoomOtherUserInfoResponse.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "존재하지 않은 유저",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = GetRoomOtherUserInfoResponse.class)))
                    )
            },
            parameters = {
                    @Parameter(name = "roomId", description = "채팅방 ID", in = ParameterIn.PATH, required = true),
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER)
            }
    )
    @GetMapping("/otherUserInfo/{roomId}")
    public Response getOtherUserInfo(
            @PathVariable Long roomId,
            @AuthenticationPrincipal User user
    ) {
        return Response.success(chatRoomService.getOtherUserInfo(roomId, user));
    }

    // 이전 그룹 채팅 메시지 불러오기
    @Operation(
            summary = "그룹 채팅방의 다른 유저들 정보 조회",
            description = "그룹 채팅방의 다른 유저들 정보 조회",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "유저 정보 조회 성공",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = GetRoomOtherUserInfoResponse.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "존재하지 않은 유저",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = GetRoomOtherUserInfoResponse.class)))
                    )
            },
            parameters = {
                    @Parameter(name = "studyId", description = "스터디 ID", in = ParameterIn.PATH, required = true),
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER)
            }
    )
    @GetMapping("/otherUserInfos/{studyId}")
    public Response getOtherUserInfos(
            @PathVariable Long studyId,
            @AuthenticationPrincipal User user
    ) {
        return Response.success(chatRoomService.getOtherUserInfos(studyId, user));
    }

    //채팅방 삭제
    @Operation(
            summary = "채팅방 삭제",
            description = "채팅방 삭제",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "채팅방 삭제 성공",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Response.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "존재하지 않은 채팅방",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Response.class)))
                    )
            },
            parameters = {
                    @Parameter(name = "roomId", description = "채팅방 ID", in = ParameterIn.PATH, required = true),
                    @Parameter(name = "X-ACCESS-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER)
            }
    )
    @PatchMapping("/{roomId}")
    public Response deleteChatRoom(
           @PathVariable Long roomId,
           @AuthenticationPrincipal User user
    ) {
        chatRoomService.deleteChatRoom(roomId, user);
        return Response.success();
    }
}
