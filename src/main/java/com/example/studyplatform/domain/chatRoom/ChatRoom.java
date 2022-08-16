package com.example.studyplatform.domain.chatRoom;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.chat.ChatMessage;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.REMOVE)
    private List<ChatMessage> chatMessages = new ArrayList<>();

    // 특정 다른 유저와 해당 유저의 채팅방 값
    private int roomHashCode;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.REMOVE)
    private List<ChatRoomUser> chatRoomUsers = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder
    public ChatRoom(int roomHashCode) {
        this.roomHashCode = roomHashCode;
        this.status = Status.ACTIVE;
    }

    public void addChatMessage(ChatMessage chatMessage) {
        this.chatMessages.add(chatMessage);
    }
}
