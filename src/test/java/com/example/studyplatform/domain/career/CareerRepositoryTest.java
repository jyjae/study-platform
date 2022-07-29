package com.example.studyplatform.domain.career;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.techStack.TechStack;
import com.example.studyplatform.domain.techStack.TechStackRepository;
import com.example.studyplatform.exception.CareerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static com.example.studyplatform.factory.entity.CareerFactory.createCareer;
import static com.example.studyplatform.factory.entity.TechStackFactory.createTechStack;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class CareerRepositoryTest {
    @Autowired
    private TechStackRepository techStackRepository;
    @Autowired
    private CareerRepository careerRepository;
    @PersistenceContext
    private EntityManager em;

    private TechStack techStack;

    @BeforeEach
    void beforeEach() {
        techStack = techStackRepository.save(createTechStack());
    }

    @Test
    void saveAndReadTest() {
        // given
        Career career = careerRepository.save(createCareer(techStack));
        emClear();

        // when
        Career foundCareer = careerRepository.findById(career.getId()).orElseThrow(CareerNotFoundException::new);

        // then
        assertThat(foundCareer.getId()).isEqualTo(career.getId());
        assertThat(foundCareer.getMonth()).isEqualTo(career.getMonth());
        assertThat(foundCareer.getTechStack().getId()).isEqualTo(techStack.getId());
    }

    @Test
    void deleteTest() {
        // given
        Career career = careerRepository.save(createCareer(techStack));
        emClear();

        // when
        careerRepository.delete(career);
        emClear();

        // then
        assertThatThrownBy(() -> careerRepository.findById(career.getId()).orElseThrow(CareerNotFoundException::new))
                .isInstanceOf(CareerNotFoundException.class);
    }

    @Test
    void findByIdAndStatusActiveTest() {
        // given
        Career career = createCareer(techStack);
        career.inActive();
        careerRepository.save(career);
        emClear();

        // when, then
        assertThat(careerRepository.findByIdAndStatus(career.getId(), Status.ACTIVE)).isEmpty();
        assertThat(careerRepository.findByIdAndStatus(career.getId(), Status.INACTIVE)).isNotEmpty();
    }

    void emClear(){
        em.flush();
        em.clear();
    }
}