package com.example.studyplatform.domain.user;

import com.example.studyplatform.config.TestConfig;
import com.example.studyplatform.domain.career.Career;
import com.example.studyplatform.domain.career.CareerRepository;
import com.example.studyplatform.domain.techStack.TechStack;
import com.example.studyplatform.domain.techStack.TechStackRepository;
import com.example.studyplatform.exception.CareerNotFoundException;
import com.example.studyplatform.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;

import static com.example.studyplatform.factory.entity.CareerFactory.createCareer;
import static com.example.studyplatform.factory.entity.TechStackFactory.createTechStack;
import static com.example.studyplatform.factory.entity.UserFactory.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(TestConfig.class)
class UserRepositoryTest {
    @Autowired
    private TechStackRepository techStackRepository;

    @Autowired
    private CareerRepository careerRepository;

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager em;

    private TechStack techStack;
    private Career career;

    @BeforeEach
    void beforeEach() {
        techStack = techStackRepository.save(createTechStack());
        career = careerRepository.save(createCareer(techStack));
    }

    @Test
    void saveAndReadTest() {
        // given
        User user = userRepository.save(createUser(career, techStack));
        emClear();

        // when
        User foundUser = userRepository.findById(user.getId()).orElseThrow(UserNotFoundException::new);

        // then
        assertThat(foundUser.getId()).isEqualTo(user.getId());
        assertThat(foundUser.getCareers().get(0).getId()).isEqualTo(career.getId());
        assertThat(foundUser.getTechStacks().get(0).getId()).isEqualTo(techStack.getId());
    }

    @Test
    void jpaAuditingTest() {
        // given
        LocalDateTime now = LocalDateTime.now();
        User user = userRepository.save(createUser(career, techStack));
        emClear();

        // when
        userRepository.save(user);
        emClear();

        // then
        User foundUser = userRepository.findById(user.getId()).orElseThrow(UserNotFoundException::new);
        assertThat(foundUser.getCreatedAt()).isNotNull();
        assertThat(foundUser.getUpdatedAt()).isNotNull();
        assertThat(foundUser.getCreatedAt()).isAfter(now);
        assertThat(foundUser.getUpdatedAt()).isAfter(now);
    }

    @Test
    void userOrphanRemovalTest() {
        // given
        User user = userRepository.save(createUser(career, techStack));
        emClear();

        // when
        userRepository.delete(user);
        emClear();

        // then
        assertThatThrownBy(() -> careerRepository.findById(career.getId()).orElseThrow(CareerNotFoundException::new))
                .isInstanceOf(CareerNotFoundException.class);
        assertThat(careerRepository.findById(career.getId())).isEmpty();
        assertThat(techStackRepository.findById(techStack.getId())).isNotEmpty();
    }

    @Test
    void deleteTest() {
        // given
        User user = userRepository.save(createUser(career, techStack));
        emClear();

        // when
        userRepository.delete(user);
        emClear();

        // then
        assertThatThrownBy(() -> userRepository.findById(user.getId()).orElseThrow(UserNotFoundException::new))
                .isInstanceOf(UserNotFoundException.class);
    }


    @Test
    void existsByEmailTest() {
        // given
        User user = userRepository.save(createUser(career, techStack));
        emClear();

        // when, then
        assertThat(userRepository.existsByEmail(user.getEmail())).isTrue();
    }

    @Test
    void existsByNickname() {
        // given
        User user = userRepository.save(createUser(career, techStack));
        emClear();

        // when, then
        assertThat(userRepository.existsByNickname(user.getNickname())).isTrue();
    }

    void emClear(){
        em.flush();
        em.clear();
    }
}