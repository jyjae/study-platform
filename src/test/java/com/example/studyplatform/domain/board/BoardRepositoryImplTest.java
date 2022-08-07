package com.example.studyplatform.domain.board;

import com.example.studyplatform.domain.career.CareerRepository;
import com.example.studyplatform.constant.CareerStatus;
import com.example.studyplatform.domain.project.projectOrganization.ProjectOrganization;
import com.example.studyplatform.domain.project.projectOrganization.ProjectOrganizationRepository;
import com.example.studyplatform.domain.project.projectPost.ProjectPost;
import com.example.studyplatform.domain.project.projectPost.ProjectPostRepository;
import com.example.studyplatform.domain.studyBoard.QStudyBoard;
import com.example.studyplatform.domain.studyBoard.StudyBoard;
import com.example.studyplatform.domain.studyBoard.StudyBoardRepository;
import com.example.studyplatform.domain.studyTechStack.StudyTechStack;
import com.example.studyplatform.domain.studyTechStack.StudyTechStackRepository;
import com.example.studyplatform.domain.techStack.Stack;
import com.example.studyplatform.domain.techStack.TechStack;
import com.example.studyplatform.domain.techStack.TechStackRepository;

import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.domain.user.UserRepository;
import com.example.studyplatform.dto.board.BoardDto;
import com.example.studyplatform.dto.board.BoardReadCondition;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.lang.reflect.Field;
import java.text.Format;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.studyplatform.domain.board.QBoard.board;
import static com.example.studyplatform.domain.project.projectPost.QProjectPost.projectPost;
import static com.example.studyplatform.domain.user.QUser.user;
import static com.example.studyplatform.factory.entity.ProjectOrganizationFactory.createProjectOrganization;
import static com.example.studyplatform.factory.entity.ProjectPostFactory.createProjectPost;
import static com.example.studyplatform.factory.entity.StudyBoardFactory.createStudyBoard;
import static com.example.studyplatform.factory.entity.TechStackFactory.createTechStack;
import static com.example.studyplatform.factory.entity.UserFactory.createUser;

@Transactional
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
    @Autowired
    TechStackRepository techStackRepository;
    @Autowired
    CareerRepository careerRepository;
    @Autowired
    StudyTechStackRepository studyTechStackRepository;
    @Autowired
    ProjectOrganizationRepository organizationRepository;
    @PersistenceContext
    private EntityManager em;

    @BeforeEach
    void beforeEach() {
        // 1. 유저 생성
        User user = userRepository.save(createUser());

        // 2. 기술 스택 생성
        TechStack backend1 = techStackRepository.save(createTechStack("Spring", Stack.BACKEND));
        TechStack backend2 = techStackRepository.save(createTechStack("Express", Stack.BACKEND));
        TechStack frontend1 = techStackRepository.save(createTechStack("ReactJS", Stack.FRONTEND));
        TechStack frontend2 = techStackRepository.save(createTechStack("NextJS", Stack.FRONTEND));
        emClear();

        // 3. 스터디 게시판 생성 (스프링)
        StudyBoard studyBoard1 = studyBoardRepository.save(createStudyBoard(user, "스프링 스터디"));
        StudyTechStack studyTechStack1 = studyTechStackRepository.save(StudyTechStack.of(studyBoard1, backend1));
        studyBoard1.addStudyTechStack(studyTechStack1);
        studyBoardRepository.save(studyBoard1);
        emClear();

        // 4. 스터디 게시판 생성 (React, Next)
        StudyBoard studyBoard2 = studyBoardRepository.save(createStudyBoard(user, "프론트엔드 스토디"));
        List<StudyTechStack> studyTechStacks = studyTechStackRepository.saveAll(
                List.of(
                        StudyTechStack.of(studyBoard2, frontend1),
                        StudyTechStack.of(studyBoard2, frontend2)
                )
        );
        emClear();

        studyTechStacks.forEach(studyBoard2::addStudyTechStack);
        studyBoardRepository.save(studyBoard2);

        // 5. 프로젝트 게시판 생성 (Spring, Express)
        ProjectPost projectPost1 = createProjectPost(user, "백엔드 프로젝트");

        List<ProjectOrganization> organizations = organizationRepository.saveAll(
                List.of(
                        createProjectOrganization(backend1, CareerStatus.NONE, 2),
                        createProjectOrganization(backend2, CareerStatus.LOW, 2)
                )
        );

        organizations.forEach(projectPost1::addOrganization);
        projectPostRepository.save(projectPost1);
    }

    @Test
    void jpaQueryDslTest() {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        QStudyBoard studyBoard = QStudyBoard.studyBoard;


        List<Board> result = jpaQueryFactory
                .selectFrom(board)
                .leftJoin(projectPost).on(projectPost.eq(board))
                .leftJoin(studyBoard).on(studyBoard.eq(board))
                .fetch();

        List<Board> r = result.stream().map(i -> {
            if (i instanceof ProjectPost)
                return (ProjectPost) i;
            if (i instanceof StudyBoard)
                return (StudyBoard) i;
            return null;
        }).collect(Collectors.toList());

        r.forEach(c -> System.out.println(c));
    }

    @Test
    void findAllByConditionTest() {
        User user = userRepository.save(createUser());
        emClear();
        BoardReadCondition condition = new BoardReadCondition(null, null, null, null, null, null);
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