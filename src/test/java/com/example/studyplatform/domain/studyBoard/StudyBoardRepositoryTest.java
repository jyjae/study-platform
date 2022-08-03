package com.example.studyplatform.domain.studyBoard;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.exception.StudyBoardNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static com.example.studyplatform.factory.entity.StudyBoardFactory.createStudyBoard;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StudyBoardRepositoryTest {
    @Autowired
    private StudyBoardRepository studyBoardRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    void saveAndReadTest() {
        // given
        StudyBoard studyBoard = studyBoardRepository.save(createStudyBoard());
        emClear();

        // when
        StudyBoard foundStudyBoard = studyBoardRepository.findByIdAndStatus(studyBoard.getId(), Status.ACTIVE)
                .orElseThrow(StudyBoardNotFoundException::new);
        emClear();

        // then
        assertThat(foundStudyBoard.getId()).isEqualTo(studyBoard.getId());
    }

    void emClear(){
        em.flush();
        em.clear();
    }
}