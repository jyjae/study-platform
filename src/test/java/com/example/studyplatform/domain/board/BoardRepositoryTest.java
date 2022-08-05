package com.example.studyplatform.domain.board;

import com.example.studyplatform.domain.project.projectPost.ProjectPost;
import com.example.studyplatform.domain.project.projectPost.ProjectPostRepository;
import com.example.studyplatform.domain.studyBoard.StudyBoard;
import com.example.studyplatform.domain.studyBoard.StudyBoardRepository;
import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static com.example.studyplatform.factory.entity.ProjectPostFactory.createProjectPost;
import static com.example.studyplatform.factory.entity.StudyBoardFactory.createStudyBoard;
import static com.example.studyplatform.factory.entity.UserFactory.createUser;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private StudyBoardRepository studyBoardRepository;
    @Autowired
    private ProjectPostRepository projectPostRepository;
    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    void saveAndReadTest() {
        // given
        User user = userRepository.save(createUser());
        StudyBoard studyBoard = studyBoardRepository.save(createStudyBoard(user));
        ProjectPost projectPost = projectPostRepository.save(createProjectPost(user));
        emClear();

        // when, then
        assertThat(boardRepository.count()).isEqualTo(2);
    }

    @Test
    void dTypeTest() {
        // given
        User user = userRepository.save(createUser());
        StudyBoard studyBoard = studyBoardRepository.save(createStudyBoard(user));
        ProjectPost projectPost = projectPostRepository.save(createProjectPost(user));
        emClear();

        // when, then
        assertThat(studyBoard.getDtype()).isEqualTo("STUDY");
        assertThat(projectPost.getDtype()).isEqualTo("PROJECT");
    }

    void emClear(){
        em.flush();
        em.clear();
    }
}