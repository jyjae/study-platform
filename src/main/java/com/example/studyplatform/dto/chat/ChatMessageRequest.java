package com.example.studyplatform.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageRequest implements Serializable {

    // 메시지 타입 : 채팅
    public enum MessageType {
        TALK,
        GROUP_TALK,
        UNREAD_MESSAGE_COUNT_ALARM
    }

    private Long messageId;
    private String roomTitle; // 그룹채팅일 경우 스터디방 제목
    private MessageType type; // 메시지 타입
    private Long roomId; // 공통으로 만들어진 방 번호
    private Set<Long> otherUserIds; // 상대방
    private String message; // 메시지
    private String createdAt;
    private Long userId;
    private int count;

    // 알림용 메세지 생성자 (근데 여기에 꼭 필요할까..?)
    public ChatMessageRequest(ChatMessageRequest chatMessageDto, int count) {
        this.type = MessageType.UNREAD_MESSAGE_COUNT_ALARM; // 메시지 타입
        this.roomId = chatMessageDto.roomId; // 방 이름
        this.otherUserIds = chatMessageDto.otherUserIds; // 상대방 prvateKey
        this.count = count; //안읽은 메세지 개수
    }
}