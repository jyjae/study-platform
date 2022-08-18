package com.example.studyplatform.domain.chat;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.chatRoom.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    //List<ChatMessage> findAllByChatRoomOrderByCreatedAtDesc(ChatRoom chatRoom);

    List<ChatMessage> findAllByStatusAndChatRoomIdOrderByCreatedAtAsc(Status active, Long id);
}
