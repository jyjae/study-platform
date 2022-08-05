package com.example.studyplatform.domain.board;

import com.example.studyplatform.constant.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom{
    Optional<Board> findByIdAndStatus(Long boardId, Status active);
}
