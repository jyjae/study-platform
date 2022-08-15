package com.example.studyplatform.domain.chatRoom;

import com.example.studyplatform.domain.BaseTimeEntity;
import com.example.studyplatform.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 유저와 채팅방은 N:M 관계이므로 중간테이블로 N:1 - M:1 매핑
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ChatRoomUser extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoom chatRoom;

    @Builder
    public ChatRoomUser(User user, ChatRoom chatRoom){
        this.user = user;
        this.chatRoom = chatRoom;
    }
}