package com.example.studyplatform.domain.studyBoard;

import com.example.studyplatform.config.TestConfig;
import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.domain.user.UserRepository;
import com.example.studyplatform.exception.StudyBoardNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static com.example.studyplatform.factory.entity.StudyBoardFactory.createStudyBoard;
import static com.example.studyplatform.factory.entity.UserFactory.createUser;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestConfig.class)
class StudyBoardRepositoryTest {
    @Autowired
    private StudyBoardRepository studyBoardRepository;
    @Autowired
    private UserRepository userRepository;
    @PersistenceContext
    private EntityManager em;

    @Test
    void saveAndReadTest() {
        // given
        User user = userRepository.save(createUser());
        StudyBoard studyBoard = studyBoardRepository.save(createStudyBoard(user));
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