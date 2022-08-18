package com.example.studyplatform.domain.chatRoom;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Long> {

    Page<ChatRoomUser> findAllByUserIdAndStatus(Long id, Status active, Pageable pageable);
}
