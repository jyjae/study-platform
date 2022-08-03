package com.example.studyplatform.service.project;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.project.projectOrganization.ProjectOrganization;
import com.example.studyplatform.domain.project.projectOrganization.ProjectOrganizationRepository;
import com.example.studyplatform.domain.project.projectPost.ProjectPost;
import com.example.studyplatform.domain.project.projectPost.ProjectPostRepository;
import com.example.studyplatform.domain.techStack.TechStackRepository;
import com.example.studyplatform.dto.project.ProjectOrganizationCreateDto;
import com.example.studyplatform.dto.project.ProjectPostCreateRequest;
import com.example.studyplatform.dto.project.ProjectPostDto;
import com.example.studyplatform.exception.ProjectPostNotFoundException;
import com.example.studyplatform.exception.TechStackNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public void create(ProjectPostCreateRequest req) {
        ProjectPost projectPost = projectPostRepository.save(ProjectPostCreateRequest.toEntity(req));

        List<ProjectOrganization> organizations = toOrganizationList(req);
        projectOrganizationRepository.saveAll(organizations);

        organizations.forEach(projectPost::addOrganization);
    }

    public ProjectPostDto read(Long id){
        return ProjectPostDto.toDto(projectPostRepository.findById(id).orElseThrow(ProjectPostNotFoundException::new));
    }

    @Transactional
    public void delete(Long id) {
        ProjectPost projectPost = projectPostRepository.findById(id).orElseThrow(ProjectPostNotFoundException::new);
        projectPostRepository.delete(projectPost);
    }

    private List<ProjectOrganization> toOrganizationList(ProjectPostCreateRequest req) {
        return req.getOrganizations().stream().map(dto ->
                ProjectOrganizationCreateDto.toEntity(dto, techStackRepository.findByIdAndStatus(dto.getTechId(), Status.ACTIVE)
                        .orElseThrow(TechStackNotFoundException::new))).collect(Collectors.toList());
    }
}
