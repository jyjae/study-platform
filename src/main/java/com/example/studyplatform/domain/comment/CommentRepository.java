package com.example.studyplatform.domain.comment;

import com.example.studyplatform.constant.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends CustomCommentRepository, JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndStatus(Long commentId, Status active);

    Optional<Comment> findByIdAndUserIdAndStatus(Long commentId, Long userId, Status active);

    Optional<List<Comment>> findAllByStudyBoardIdAndStatus(Long boardId, Status active);
}
