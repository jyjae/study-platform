package com.example.studyplatform.domain.project.projectPost;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.exception.ProjectPostNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static com.example.studyplatform.factory.entity.ProjectPostFactory.createProjectPost;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProjectPostRepositoryTest {
    @Autowired
    private ProjectPostRepository projectPostRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    void saveAndReadTest() {
        // given
        ProjectPost projectPost = projectPostRepository.save(createProjectPost());
        emClear();

        // when
        ProjectPost foundProjectPost = projectPostRepository.findByIdAndStatus(projectPost.getId(), Status.ACTIVE)
                .orElseThrow(ProjectPostNotFoundException::new);
        emClear();

        // then
        assertThat(foundProjectPost.getId()).isEqualTo(projectPost.getId());
    }

    void emClear(){
        em.flush();
        em.clear();
    }
}