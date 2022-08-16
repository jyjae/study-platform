package com.example.studyplatform.domain.chatRoom;

import com.example.studyplatform.constant.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByRoomHashCodeAndStatus(int roomHashCode, Status active);

    Optional<ChatRoom> findByIdAndStatus(Long chatRoomId, Status active);
}
