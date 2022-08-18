package com.example.studyplatform.service.scrap;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.board.Board;
import com.example.studyplatform.domain.board.BoardRepository;
import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.domain.user.UserRepository;
import com.example.studyplatform.dto.board.BoardDto;
import com.example.studyplatform.dto.scrap.AddScrapRequest;
import com.example.studyplatform.dto.scrap.DeleteScrapRequest;
import com.example.studyplatform.exception.BoardNotFoundException;
import com.example.studyplatform.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ScrapService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addScrap(AddScrapRequest req) {
        User user = userRepository.findByIdAndStatus(req.getUserId(), Status.ACTIVE).orElseThrow(UserNotFoundException::new);
        Board board = boardRepository.findByIdAndStatus(req.getBoardId(), Status.ACTIVE).orElseThrow(BoardNotFoundException::new);

        user.addScrapBoard(board);
    }

    @Transactional
    public void deleteScrap(DeleteScrapRequest req) {
        User user = userRepository.findByIdAndStatus(req.getUserId(), Status.ACTIVE).orElseThrow(UserNotFoundException::new);
        Board board = boardRepository.findByIdAndStatus(req.getBoardId(), Status.ACTIVE).orElseThrow(BoardNotFoundException::new);

        user.deleteScrapBoard(board);
    }

    public List<BoardDto> readScrapBoardsByUserId(Long userId) {
        return boardRepository.findScrapBoardsByUserId(userId);
    }
}
