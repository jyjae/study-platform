package com.example.studyplatform.domain.chat;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.BaseTimeEntity;
import com.example.studyplatform.domain.chatRoom.ChatRoom;
import com.example.studyplatform.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ChatMessage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoom chatRoom;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder
    public ChatMessage(User user, ChatRoom chatRoom, String message) {
        this.user = user;
        this.chatRoom = chatRoom;
        this.message = message;
        this.status = Status.ACTIVE;
    }
}
