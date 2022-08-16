package com.example.studyplatform.service.chatRoom;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.chatRoom.ChatRoom;
import com.example.studyplatform.domain.chatRoom.ChatRoomRepository;
import com.example.studyplatform.domain.chatRoom.ChatRoomUser;
import com.example.studyplatform.domain.chatRoom.ChatRoomUserRepository;
import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.domain.user.UserRepository;
import com.example.studyplatform.dto.chatRoom.PostGroupChatRoomRequest;
import com.example.studyplatform.dto.chatRoom.PostOneToOneChatRoomRequest;
import com.example.studyplatform.exception.ChatRoomNotFoundException;
import com.example.studyplatform.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ChatRoomService {
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;

    public ChatRoomService(UserRepository userRepository, ChatRoomRepository chatRoomRepository, ChatRoomUserRepository chatRoomUserRepository) {
        this.userRepository = userRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.chatRoomUserRepository = chatRoomUserRepository;
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
        ChatRoomUser chatRoomUser = new ChatRoomUser(user, room);
        // 6. 상대방 유저 채팅 매핑 데이터 생성
        ChatRoomUser chatRoomAnotherUser = new ChatRoomUser(anotherUser, room);

        chatRoomUserRepository.save(chatRoomUser);
        chatRoomUserRepository.save(chatRoomAnotherUser);

        return room.getId();
    }

    @Transactional(rollbackOn = Exception.class)
    public void createGroupChatRoom(PostGroupChatRoomRequest req) {
        // 2. 상대방 존재들 체크
        List<User> groupUsers = new ArrayList<>();

        for(Long userId : req.getAnotherUserIds()) {
            groupUsers.add(userRepository.findByIdAndStatus(userId, Status.ACTIVE)
                    .orElseThrow(UserNotFoundException::new));
        }

        // 3. 방 존재 확인 함수
        if(req.getChatRoomId()!=null) {
            if (existGroupRoom(req.getChatRoomId(), groupUsers)) {
                ChatRoom existChatRoom = chatRoomRepository.findByIdAndStatus(req.getChatRoomId(), Status.ACTIVE)
                        .orElseThrow(ChatRoomNotFoundException::new);
                return;
            }
        }

        // 4. 존재하는 방 없다면 생성
        ChatRoom room = ChatRoom.builder().build();
        chatRoomRepository.save(room);

        for(User user : groupUsers) {
            ChatRoomUser chatRoomUser = new ChatRoomUser(user, room);
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
                    ChatRoomUser chatRoomUser = new ChatRoomUser(anotherUser, chatRoom);
                    chatRoomUserRepository.save(chatRoomUser);
                } else {
                    //상대방만 있을 때
                    ChatRoomUser chatRoomUser = new ChatRoomUser(user, chatRoom);
                    chatRoomUserRepository.save(chatRoomUser);
                }
            }
            return true;
        }
        return false;
    }

    private boolean existGroupRoom(long roomId, List<User> users) {
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
                    ChatRoomUser chatRoomUser = new ChatRoomUser(existsUser, chatRoom);
                    chatRoomUserRepository.save(chatRoomUser);
                }
            }

            return true;
        }
        return false;
    }



}
