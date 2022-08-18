package com.example.studyplatform.service.chatRoom;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.chat.ChatMessage;
import com.example.studyplatform.domain.chat.ChatMessageRepository;
import com.example.studyplatform.domain.chatRoom.ChatRoom;
import com.example.studyplatform.domain.chatRoom.ChatRoomRepository;
import com.example.studyplatform.domain.chatRoom.ChatRoomUser;
import com.example.studyplatform.domain.chatRoom.ChatRoomUserRepository;
import com.example.studyplatform.domain.study.Study;
import com.example.studyplatform.domain.study.StudyRepository;
import com.example.studyplatform.domain.studyUser.StudyUser;
import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.domain.user.UserRepository;
import com.example.studyplatform.dto.chat.GetChatMessageResponse;
import com.example.studyplatform.dto.chatRoom.*;
import com.example.studyplatform.exception.*;
import com.example.studyplatform.service.chat.RedisRepository;
import com.example.studyplatform.util.Calculator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ChatRoomService {
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final StudyRepository studyRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final RedisRepository redisRepository;

    public ChatRoomService(UserRepository userRepository, ChatRoomRepository chatRoomRepository, ChatRoomUserRepository chatRoomUserRepository, StudyRepository studyRepository, ChatMessageRepository chatMessageRepository, RedisRepository redisRepository) {
        this.userRepository = userRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.chatRoomUserRepository = chatRoomUserRepository;
        this.studyRepository = studyRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.redisRepository = redisRepository;
    }
    @Transactional(rollbackOn = Exception.class)
    public Long createChatRoom(PostOneToOneChatRoomRequest req, User user) {
        // 1. 상대방 존재 체크
        User anotherUser = userRepository.findByIdAndStatus(user.getId(), Status.ACTIVE)
                .orElseThrow(UserNotFoundException::new);

        // 2. roomHashCode 만들기
        int roomHashCode = createRoomHashCode(user, anotherUser);

        // 3. 방 존재 확인
        if(existRoom(roomHashCode, user, anotherUser)) {
            ChatRoom savedChatRoom = chatRoomRepository.findByRoomHashCodeAndStatus(roomHashCode, Status.ACTIVE)
                    .orElseThrow(ChatRoomNotFoundException::new);
            return savedChatRoom.getId();
        }

        // 4. 존재하는 방 없다면 생성
        ChatRoom room = ChatRoom.builder()
                .roomHashCode(roomHashCode).build();
        chatRoomRepository.save(room);

        // 5. 해당 유저 채팅 매핑 데이터 생성
        ChatRoomUser chatRoomUser = new ChatRoomUser(user, anotherUser.getNickname(), room);
        // 6. 상대방 유저 채팅 매핑 데이터 생성
        ChatRoomUser chatRoomAnotherUser = new ChatRoomUser(anotherUser, user.getNickname(), room);

        chatRoomUserRepository.save(chatRoomUser);
        chatRoomUserRepository.save(chatRoomAnotherUser);

        return room.getId();
    }

    @Transactional(rollbackOn = Exception.class)
    public void createGroupChatRoom(PostGroupChatRoomRequest req) {
        // 1. 스터디 존재 유무 체크
        Study savedStudy = studyRepository.findByIdAndStatus(req.getStudyId(), Status.ACTIVE)
                .orElseThrow(StudyNotFoundException::new);

        // 2. 상대방 존재들 체크
        List<User> groupUsers = new ArrayList<>();

        for(Long userId : req.getAnotherUserIds()) {
            groupUsers.add(userRepository.findByIdAndStatus(userId, Status.ACTIVE)
                    .orElseThrow(UserNotFoundException::new));
        }

        // 3. 방 존재 확인 함수
        if(req.getChatRoomId()!=null) {
            if (existGroupRoom(req.getChatRoomId(), savedStudy, groupUsers)) {
                ChatRoom existChatRoom = chatRoomRepository.findByIdAndStatus(req.getChatRoomId(), Status.ACTIVE)
                        .orElseThrow(ChatRoomNotFoundException::new);
                return;
            }
        }

        // 4. 존재하는 방 없다면 생성
        ChatRoom room = ChatRoom.builder()
                .roomHashCode(0)
                .study(savedStudy)
                .build();
        chatRoomRepository.save(room);

        for(User user : groupUsers) {
            ChatRoomUser chatRoomUser = new ChatRoomUser(user, savedStudy.getStudyName(), room);
            chatRoomUserRepository.save(chatRoomUser);
        }

    }

    private int createRoomHashCode(User user, User anotherUser) {
        Long userId = user.getId();
        Long anotherId = anotherUser.getId();
        return userId > anotherId ? Objects.hash(userId, anotherId) : Objects.hash(anotherId, userId);
    }

    private boolean existRoom(int roomHashCode, User user, User anotherUser) {
        ChatRoom chatRoom = chatRoomRepository
                .findByRoomHashCodeAndStatus(roomHashCode, Status.ACTIVE).orElse(null);

        if(chatRoom != null) {
            List<ChatRoomUser> chatRoomUsers = chatRoom.getChatRoomUsers();

            if (chatRoomUsers.size() == 1) {
                //나만 있을 때
                if (chatRoomUsers.get(0).getUser().getId().equals(user.getId())) {
                    ChatRoomUser chatRoomUser = new ChatRoomUser(anotherUser, user.getNickname(), chatRoom);
                    chatRoomUserRepository.save(chatRoomUser);
                } else {
                    //상대방만 있을 때
                    ChatRoomUser chatRoomUser = new ChatRoomUser(user, anotherUser.getNickname(), chatRoom);
                    chatRoomUserRepository.save(chatRoomUser);
                }
            }
            return true;
        }
        return false;
    }

    private boolean existGroupRoom(long roomId, Study study, List<User> users) {
        ChatRoom chatRoom = chatRoomRepository
                .findByIdAndStatus(roomId, Status.ACTIVE).orElse(null);

        if(chatRoom != null) {
            List<ChatRoomUser> chatRoomUsers = chatRoom.getChatRoomUsers();

            for(User existsUser : users) {
                boolean check = false;
                for(ChatRoomUser chatRoomUser : chatRoomUsers ) {
                    if(chatRoomUser.getUser().getId() == existsUser.getId()) {
                        check = true;
                    }
                }
                if(check) {
                    ChatRoomUser chatRoomUser = new ChatRoomUser(existsUser, study.getStudyName(), chatRoom);
                    chatRoomUserRepository.save(chatRoomUser);
                }
            }

            return true;
        }
        return false;
    }


    public List<GetOneToOneChatRoomResponse> getOneToOneChatRooms(User user, Pageable pageable) {
        // 1. 유저의 채팅방 조회
        List<GetOneToOneChatRoomResponse> resDtos = new ArrayList<>();
        Page<ChatRoomUser> chatRoomUsers = chatRoomUserRepository
                .findAllByUserIdAndStatus(user.getId(),Status.ACTIVE, pageable);

        for(ChatRoomUser chatRoomUser : chatRoomUsers) {
            GetOneToOneChatRoomResponse resDto = createChatRoomDto(chatRoomUser);
            resDtos.add(resDto);
        }
        return resDtos;

    }

    private GetOneToOneChatRoomResponse createChatRoomDto(ChatRoomUser chatRoomUser) {
        String roomName = chatRoomUser.getName();
        String roomId = chatRoomUser.getChatRoom().getId()+"";

        String lastMessage;
        LocalDateTime lastTime;

        // 마지막 메시지
        List<ChatMessage> messages = chatMessageRepository
                .findAllByStatusAndChatRoomIdOrderByCreatedAtAsc(Status.ACTIVE, chatRoomUser.getChatRoom().getId());

        // 메시지가 없는 경우
        if(messages == null || messages.isEmpty()) {
            lastMessage = "채팅방이 생성 되었습니다.";
            lastTime = LocalDateTime.now();
        } else {
            lastMessage = messages.get(0).getMessage();
            lastTime = messages.get(0).getCreatedAt();
        }

        int unReadMessageCount = redisRepository.getChatRoomMessageCount(roomId, chatRoomUser.getUser().getId());
        long dayBeforeTime = ChronoUnit.MINUTES.between(lastTime, LocalDateTime.now());
        String dayBefore = Calculator.time(dayBeforeTime);

        return new GetOneToOneChatRoomResponse(roomName, roomId, lastMessage, lastTime, dayBefore, unReadMessageCount);
    }


    private GetGroupChatRoomResponse createGroupChatRoomDto(ChatRoom chatRoom) {
        String roomName = chatRoom.getStudy().getStudyName();
        String roomId = chatRoom.getId()+"";

        String lastMessage;
        LocalDateTime lastTime;

        List<ChatMessage> messages = chatMessageRepository
                .findAllByStatusAndChatRoomIdOrderByCreatedAtAsc(Status.ACTIVE, chatRoom.getId());

        if(messages == null || messages.isEmpty()) {
            lastMessage = "채팅방이 생성 되었습니다.";
            lastTime = LocalDateTime.now();
        } else {
            lastMessage = messages.get(0).getMessage();
            lastTime = messages.get(0).getCreatedAt();
        }

        long dayBeforeTime = ChronoUnit.MINUTES.between(lastTime, LocalDateTime.now());
        String dayBefore = Calculator.time(dayBeforeTime);

        return new GetGroupChatRoomResponse(roomName, roomId, lastMessage, lastTime, dayBefore);

    }

    public GetGroupChatRoomResponse getGroupChatRooms(User user, Long studyId) {
        // 1. 스터디 존재 유무 체크
        Study savedStudy = studyRepository.findByIdAndStatus(studyId, Status.ACTIVE)
                .orElseThrow(StudyNotFoundException::new);

        // 2. 해당 스터디에 유저가 포함되어있는지 체크
        boolean check = false;
        for(StudyUser studyUser : savedStudy.getStudyUsers()) {
            if(studyUser.getUser().getId()==user.getId()) {
                check = true;
            }
        }

        if(!check) throw new UserNotFoundInStudyException();

        ChatRoom chatRoom= chatRoomRepository.findByStudyIdAndStatus(savedStudy.getId(),Status.ACTIVE)
                .orElse(null);
        GetGroupChatRoomResponse resDto = createGroupChatRoomDto(chatRoom);

        return resDto;

    }

    public List<GetChatMessageResponse> getPreviousChatMessage(Long roomId, User user) {
        List<GetChatMessageResponse> resDtos = new ArrayList<>();

        ChatRoom chatRoom = chatRoomRepository.findByIdAndStatus(roomId, Status.ACTIVE)
                .orElseThrow(ChatRoomNotFoundException::new);

        List<ChatRoomUser> chatRoomUsers = chatRoom.getChatRoomUsers();
        //혹시 채팅방 이용자가 아닌데 들어온다면,
        for(ChatRoomUser chatroomUser:chatRoomUsers){
            if(chatroomUser.getUser().getId().equals(user.getId())) {
                List<ChatMessage> chatMessages = chatMessageRepository
                        .findAllByStatusAndChatRoomIdOrderByCreatedAtAsc(Status.ACTIVE, chatRoom.getId());
                for(ChatMessage chatMessage : chatMessages){
                    resDtos.add(new GetChatMessageResponse(chatMessage));
                }
                return resDtos;
            }
        }

        throw new ChatRoomForbidden();
    }

    public GetRoomOtherUserInfoResponse getOtherUserInfo(Long roomId, User myUser) {
        ChatRoom chatRoom = chatRoomRepository.findByIdAndStatus(roomId, Status.ACTIVE)
                .orElseThrow(ChatRoomNotFoundException::new);


        List<ChatRoomUser> users = chatRoom.getChatRoomUsers();

        for(ChatRoomUser user : users){
            if(!user.getUser().getId().equals(myUser.getId())) {
                User otherUser = user.getUser();
                return new GetRoomOtherUserInfoResponse(otherUser);
            }
        }

        User anotherUser = userRepository.findByNicknameAndStatus(users.get(0).getName(), Status.ACTIVE)
                .orElseThrow(UserNotFoundException::new);

        ChatRoomUser anotherChatRoomUser = new ChatRoomUser(anotherUser, myUser.getNickname(), chatRoom);
        chatRoomUserRepository.save(anotherChatRoomUser);

        return new GetRoomOtherUserInfoResponse(anotherUser);
    }

    public List<GetRoomOtherUserInfoResponse> getOtherUserInfos(Long studyId, User myUser) {
        ChatRoom chatRoom = chatRoomRepository.findByStudyIdAndStatus(studyId, Status.ACTIVE)
                .orElseThrow(ChatRoomNotFoundException::new);

        List<ChatRoomUser> users = chatRoom.getChatRoomUsers();
        List<GetRoomOtherUserInfoResponse> userInfos = new ArrayList<>();

        for(ChatRoomUser user : users){
            if(!user.getUser().getId().equals(myUser.getId())) {
                User otherUser = user.getUser();
                userInfos.add(new GetRoomOtherUserInfoResponse(otherUser));
            }
        }

        return userInfos;

    }

    // 채팅방 삭제
    public void deleteChatRoom(Long roomId, User user) {
        ChatRoom chatRoom = chatRoomRepository.findByIdAndStatus(roomId, Status.ACTIVE)
                .orElseThrow(ChatRoomNotFoundException::new);

        if(chatRoom.getChatRoomUsers().size() != 1) {
            chatRoomUserRepository.saveAll(chatRoom.getChatRoomUsers().stream()
                    .map(i -> i.inActive())
                    .collect(Collectors.toList()));
        } else if(chatRoom.getChatRoomUsers().size() == 1) {
            chatRoomRepository.save(chatRoom.inActive());
        }
    }
}
