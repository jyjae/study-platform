package com.example.studyplatform.domain.board;

import com.example.studyplatform.domain.project.projectPost.ProjectPost;
import com.example.studyplatform.domain.project.projectPost.ProjectPostRepository;
import com.example.studyplatform.domain.studyBoard.StudyBoard;
import com.example.studyplatform.domain.studyBoard.StudyBoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static com.example.studyplatform.factory.entity.ProjectPostFactory.createProjectPost;
import static com.example.studyplatform.factory.entity.StudyBoardFactory.createStudyBoard;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private StudyBoardRepository studyBoardRepository;
    @Autowired
    private ProjectPostRepository projectPostRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    void saveAndReadTest() {
        // given
        StudyBoard studyBoard = studyBoardRepository.save(createStudyBoard());
        ProjectPost projectPost = projectPostRepository.save(createProjectPost());
        emClear();

        // when, then
        assertThat(boardRepository.count()).isEqualTo(2);
    }

    void emClear(){
        em.flush();
        em.clear();
    }
}