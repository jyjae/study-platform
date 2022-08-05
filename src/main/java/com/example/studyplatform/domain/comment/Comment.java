package com.example.studyplatform.domain.comment;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.BaseTimeEntity;
import com.example.studyplatform.domain.board.Board;
import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.comment.PutCommentRequest;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;


    /**
     * 댓글 레벨
     * 일반 댓글 :0
     * 대댓글 :1
     */
    private Integer commentLevel;

    private Long commentGroup;

    /**
     * 부모 댓글
     */
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment parentComment;
    /**
     *  자식 댓글
     */
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    private List<Comment> subComment = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder
    public Comment (
            String content,
            User user,
            Board board,
            Integer commentLevel,
            Long commentGroup,
            Comment parentComment
    ) {
        this.content = content;
        this.user = user;
        this.board = board;
        this.commentLevel = commentLevel;
        this.commentGroup = commentGroup;
        this.parentComment = parentComment;
        this.status = Status.ACTIVE;
    }

    public void addSubComment(Comment subComment) {
        this.subComment.add(subComment);
    }

    public Comment inActive() {
        this.status = Status.INACTIVE;
        return this;
    }

    public Comment updateEntity(PutCommentRequest req) {
        if(req.getContent()!=null) this.content = req.getContent();
        return this;
    }
}
