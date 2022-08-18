package com.example.studyplatform.service.comment;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.alarm.Alarm;
import com.example.studyplatform.domain.alarm.AlarmRepository;
import com.example.studyplatform.domain.board.Board;
import com.example.studyplatform.domain.board.BoardRepository;
import com.example.studyplatform.domain.comment.Comment;
import com.example.studyplatform.domain.comment.CommentRepository;
import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.domain.user.UserRepository;
import com.example.studyplatform.dto.alarm.AlarmRequest;
import com.example.studyplatform.dto.comment.CommentResponse;
import com.example.studyplatform.dto.comment.GetCommentResponse;
import com.example.studyplatform.dto.comment.PostCommentRequest;
import com.example.studyplatform.dto.comment.PutCommentRequest;
import com.example.studyplatform.exception.CommentNotFoundException;
import com.example.studyplatform.exception.StudyBoardNotFoundException;
import com.example.studyplatform.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final AlarmRepository alarmRepository;
    private final RedisTemplate redisTemplate;
    private final ChannelTopic channelTopic;
    private final UserRepository userRepository;

    public CommentResponse createComment(
            PostCommentRequest req,
            User user
    ) {
        // 1. 게시글 가져오기
        Board savedBoard = getBoard(req.getBoardId());
        // 2. 부모 댓글 가져오기
        Comment parentComment = getComment(req.getParentCommentId());

        // 3. 댓글 그룹 값 구하기
        Long commentGroup = getCommentGroup(parentComment, savedBoard);

        // 4. 댓글 저장
        Comment savedComment = commentRepository.save(PostCommentRequest.toEntity(
                req.getContent(),
                user,
                commentGroup,
                savedBoard,
                parentComment));

        /*
            5. 부모댓글에 자식 댓글 추가
         */
        if(req.getParentCommentId() != null) {
            parentComment.addSubComment(savedComment);
        }


        return CommentResponse.of(user, savedComment);
    }

    private Long getCommentGroup(Comment parentComment,
                                 Board board
    ) {
        if(parentComment==null) {
            Long id = commentRepository.countCommentGroupByBoard(board);
            return id == null ? 0: id+1;
        }

        return parentComment.getCommentGroup();
    }


    public CommentResponse modifyComment(PutCommentRequest req, User user) {
        // 1. 댓글 가져오기
        Comment savedComment = getCommentByIdAndUser(req.getCommentId(), user.getId());

        // 2. 댓글 변경
        Comment updatedComment = commentRepository.save(savedComment.updateEntity(req));

        return CommentResponse.of(user, updatedComment);
    }

    public void deleteComment(Long commentId, User user) {
        // 1. 댓글 가져오기
        Comment savedComment = getCommentByIdAndUser(commentId, user.getId());

        // 2. 댓글 INACTIVE
        Comment deleteComment = commentRepository.save(savedComment.inActive());

    }


    public List<GetCommentResponse> getCommentAll(Long boardId) {
       return commentRepository.findAllComments(boardId).stream()
               .map(i -> GetCommentResponse.of(i)).collect(Collectors.toList());

    }


    private Comment getCommentByIdAndUser(Long commentId, Long userId) {
        return commentRepository.findByIdAndUserIdAndStatus(commentId, userId, Status.ACTIVE)
                .orElseThrow(CommentNotFoundException::new);
    }

    private Comment getComment(Long commentId) {
        if(commentId == null) return null;

        return commentRepository.findByIdAndStatus(commentId, Status.ACTIVE)
                .orElseThrow(CommentNotFoundException::new);
    }

    private Board getBoard(Long boardId) {
        return boardRepository.findByIdAndStatus(boardId, Status.ACTIVE)
                .orElseThrow(StudyBoardNotFoundException::new);
    }


    public void sendCommentAlarm(
            PostCommentRequest req,
            User user
    ) {
        // 1. 글 작성자 정보를 가져옴
        Long postUserId = getBoard(req.getBoardId()).getUser().getId();
        User postUser = userRepository.findByIdAndStatus(postUserId, Status.ACTIVE).orElseThrow(UserNotFoundException::new);
        String topic = channelTopic.getTopic();
        String title = user.getNickname() + "님이 댓글을 남겼습니다.";

        // 2. 알림 엔티티 정보 저장
        Alarm alarm = alarmRepository.save(Alarm.builder()
                .title(title)
                .url("boardURL")
                .user(postUser)
                .build());

        // 3. 레디스로 발행
        redisTemplate.convertAndSend(topic, AlarmRequest.toDto(alarm, postUserId));
    }
}
