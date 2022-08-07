package com.example.studyplatform.domain.techStack;

import com.example.studyplatform.config.TestConfig;
import com.example.studyplatform.constant.Status;
import com.example.studyplatform.exception.TechStackNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static com.example.studyplatform.factory.entity.TechStackFactory.createTechStack;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Import(TestConfig.class)
class TechStackRepositoryTest {
    @Autowired
    private TechStackRepository techStackRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    void saveAndReadTest() {
        // given
        String name = "Spring";
        TechStack techStack = techStackRepository.save(createTechStack(name));
        emClear();

        // when
        TechStack foundTechStack = techStackRepository.findById(techStack.getId()).orElseThrow(TechStackNotFoundException::new);

        // then
        assertThat(techStackRepository.count()).isEqualTo(1);
        assertThat(foundTechStack.getTechName()).isEqualTo(name);
    }

    @Test
    void deleteTest() {
        // given
        TechStack techStack = techStackRepository.save(createTechStack());
        emClear();

        // when
        techStackRepository.delete(techStack);
        emClear();

        // then
        assertThatThrownBy(() -> techStackRepository.findById(techStack.getId()).orElseThrow(TechStackNotFoundException::new))
                .isInstanceOf(TechStackNotFoundException.class);
    }

    @Test
    void findByIdAndStatusActiveTest() {
        // given
        TechStack techStack = createTechStack();
        techStack.inActive();
        techStackRepository.save(techStack);
        emClear();

        // when, then
        assertThat(techStackRepository.findByIdAndStatus(techStack.getId(), Status.ACTIVE)).isEmpty();
        assertThat(techStackRepository.findByIdAndStatus(techStack.getId(), Status.INACTIVE)).isNotEmpty();
    }

    // 영속성 컨텍스트 비우기
    void emClear(){
        em.flush();
        em.clear();
    }
}