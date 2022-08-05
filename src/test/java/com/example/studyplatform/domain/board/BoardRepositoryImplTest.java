package com.example.studyplatform.domain.board;

import com.example.studyplatform.domain.project.projectPost.ProjectPost;
import com.example.studyplatform.domain.project.projectPost.ProjectPostRepository;
import com.example.studyplatform.domain.studyBoard.StudyBoard;
import com.example.studyplatform.domain.studyBoard.StudyBoardRepository;
import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.domain.user.UserRepository;
import com.example.studyplatform.dto.board.BoardDto;
import com.example.studyplatform.dto.board.BoardReadCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static com.example.studyplatform.factory.entity.ProjectPostFactory.createProjectPost;
import static com.example.studyplatform.factory.entity.StudyBoardFactory.createStudyBoard;
import static com.example.studyplatform.factory.entity.UserFactory.createUser;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BoardRepositoryImplTest{
    @Autowired
    BoardRepositoryImpl boardRepository;
    @Autowired
    StudyBoardRepository studyBoardRepository;
    @Autowired
    ProjectPostRepository projectPostRepository;
    @Autowired
    UserRepository userRepository;
    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Test
    void findAllByConditionTest() {
        User user = userRepository.save(createUser());
        emClear();
        BoardReadCondition condition = new BoardReadCondition(null, null, null, null, null);
        Pageable pageable = Pageable.ofSize(1);
        StudyBoard studyBoard = studyBoardRepository.save(createStudyBoard(user));
        emClear();
        ProjectPost projectPost = projectPostRepository.save(createProjectPost(user));
        emClear();
        projectPostRepository.save(createProjectPost(user));
        emClear();

        // when
        Slice<BoardDto> result = boardRepository.findAllByCondition(projectPost.getId(), condition, pageable);

        // then
        System.out.println("--------------------------------");
        result.forEach(System.out::println);
        System.out.println(result.hasNext());
        System.out.println(result.getPageable());
        System.out.println("--------------------------------");

    }

    void emClear(){
        em.flush();
        em.clear();
    }
}