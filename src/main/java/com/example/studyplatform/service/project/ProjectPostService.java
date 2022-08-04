package com.example.studyplatform.service.project;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.project.projectOrganization.ProjectOrganization;
import com.example.studyplatform.domain.project.projectOrganization.ProjectOrganizationRepository;
import com.example.studyplatform.domain.project.projectPost.ProjectPost;
import com.example.studyplatform.domain.project.projectPost.ProjectPostRepository;
import com.example.studyplatform.domain.project.projectResume.ProjectResume;
import com.example.studyplatform.domain.techStack.TechStackRepository;
import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.project.*;
import com.example.studyplatform.dto.techStack.TechStackDto;
import com.example.studyplatform.exception.ProjectOrganizationNotFoundException;
import com.example.studyplatform.exception.ProjectPostNotFoundException;
import com.example.studyplatform.exception.TechStackNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProjectPostService {
    private final ProjectPostRepository projectPostRepository;
    private final ProjectOrganizationRepository projectOrganizationRepository;
    private final TechStackRepository techStackRepository;

    // 프로젝트 게시글 생성 메소드
    @Transactional
    public void create(ProjectPostCreateRequest req, User user) {
        ProjectPost projectPost = projectPostRepository.save(ProjectPostCreateRequest.toEntity(req, user));

        // 테스트
        if (req.getOrganizations() != null) {
            List<ProjectOrganization> organizations = toOrganizationList(req.getOrganizations());
            projectOrganizationRepository.saveAll(organizations);
            organizations.forEach(projectPost::addOrganization);
        }
    }

    public ProjectPostResponse read(UUID id){
        ProjectPost projectPost = projectPostRepository.findById(id).orElseThrow(ProjectPostNotFoundException::new);

        return ProjectPostResponse.toDto(projectPost, createProjectOrganizationDto(projectPost));
    }

    @Transactional
    public void update(UUID id, ProjectPostUpdateRequest req) {
        ProjectPost projectPost = projectPostRepository.findById(id).orElseThrow(ProjectPostNotFoundException::new);

        if (!req.getAddOrganizations().isEmpty()) {
            List<ProjectOrganization> organizations = toOrganizationList(req.getAddOrganizations());
            projectOrganizationRepository.saveAll(organizations);
            organizations.forEach(projectPost::addOrganization);
        }

        req.getDeleteOrganizationIds().forEach(i -> {
            ProjectOrganization organization = projectOrganizationRepository.findByIdAndStatus(i, Status.ACTIVE)
                    .orElseThrow(ProjectOrganizationNotFoundException::new);

            projectPost.deleteOrganization(organization);
            organization.inActive();
        });

        projectPost.update(req);
    }

    @Transactional
    public void delete(UUID id) {
        ProjectPost projectPost = projectPostRepository.findById(id).orElseThrow(ProjectPostNotFoundException::new);
        projectPost.inActive();

        projectPost.getOrganizations().forEach(ProjectOrganization::inActive);
        projectPost.getProjectResumes().forEach(ProjectResume::inActive);

        projectPostRepository.delete(projectPost);
    }

    private List<ProjectOrganizationDto> createProjectOrganizationDto(ProjectPost projectPost) {
        return projectPost.getOrganizations().stream().map(organization ->
                ProjectOrganizationDto.toDto(organization, TechStackDto.toDto(organization.getTechStack()))
        ).collect(Collectors.toList());
    }

    private List<ProjectOrganization> toOrganizationList(List<ProjectOrganizationCreateDto> req) {
        return req.stream().map(dto ->
                ProjectOrganizationCreateDto.toEntity(dto, techStackRepository.findByIdAndStatus(dto.getTechId(), Status.ACTIVE)
                        .orElseThrow(TechStackNotFoundException::new))).collect(Collectors.toList());
    }
}
