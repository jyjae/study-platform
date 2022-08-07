package com.example.studyplatform.service.project;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.project.projectOrganization.ProjectOrganization;
import com.example.studyplatform.domain.project.projectOrganization.ProjectOrganizationRepository;
import com.example.studyplatform.domain.project.projectPost.ProjectPost;
import com.example.studyplatform.domain.project.projectPost.ProjectPostRepository;
import com.example.studyplatform.domain.project.projectResume.ProjectResume;
import com.example.studyplatform.domain.project.projectResume.ProjectResumeRepository;
import com.example.studyplatform.domain.user.User;
import com.example.studyplatform.dto.project.ProjectResumeCreateRequest;
import com.example.studyplatform.dto.project.ProjectResumeDeleteRequest;
import com.example.studyplatform.dto.project.ProjectResumeApprovalRequest;
import com.example.studyplatform.dto.project.ProjectResumeResponse;
import com.example.studyplatform.exception.ProjectOrganizationNotFoundException;
import com.example.studyplatform.exception.ProjectPostNotFoundException;
import com.example.studyplatform.exception.ProjectResumeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProjectResumeService {
    private final ProjectResumeRepository projectResumeRepository;
    private final ProjectOrganizationRepository projectOrganizationRepository;
    private final ProjectPostRepository projectPostRepository;

    @Transactional
    public void create(ProjectResumeCreateRequest req, User user) {
        ProjectOrganization projectOrganization = projectOrganizationRepository
                .findByIdAndStatus(req.getOrganizationId(), Status.ACTIVE)
                .orElseThrow(ProjectOrganizationNotFoundException::new);

        projectResumeRepository.save(ProjectResumeCreateRequest.toEntity(user, projectOrganization));
    }

    // 신청서 승인
    @Transactional
    public void approvalResume(Long resumeId, ProjectResumeApprovalRequest req) {
        ProjectResume projectResume = projectResumeRepository.findByIdAndStatus(resumeId, Status.ACTIVE)
                .orElseThrow(ProjectResumeNotFoundException::new);

        ProjectPost projectPost = projectPostRepository.findByIdAndStatus(req.getProjectPostId(), Status.ACTIVE)
                .orElseThrow(ProjectPostNotFoundException::new);

        // 프로젝트 인원 감소
        projectResume.getOrganization().decrease();

        // 게시글에 신청서 추가
        projectPost.addProjectResume(projectResume);
    }

    // 신청서 거절
    @Transactional
    public void deniedResume(Long resumeId) {
        ProjectResume projectResume = projectResumeRepository.findByIdAndStatus(resumeId, Status.ACTIVE)
                .orElseThrow(ProjectResumeNotFoundException::new);

        projectResume.inActive();
    }

    // 확정된 신청서 삭제
    @Transactional
    public void deleteAppliedResume(Long resumeId, ProjectResumeDeleteRequest req) {
        ProjectResume projectResume = projectResumeRepository.findByIdAndStatus(resumeId, Status.ACTIVE)
                .orElseThrow(ProjectResumeNotFoundException::new);

        ProjectPost projectPost = projectPostRepository.findByIdAndStatus(req.getProjectPostId(), Status.ACTIVE)
                .orElseThrow(ProjectPostNotFoundException::new);

        // 프로젝트 인원 증가
        projectResume.getOrganization().increase();

        // 게시글에 신청서 삭제
        projectPost.deleteProjectResume(projectResume);
    }

    public ProjectResumeResponse read(Long resumeId) {
        ProjectResume projectResume = projectResumeRepository.findByIdAndStatus(resumeId, Status.ACTIVE)
                .orElseThrow(ProjectResumeNotFoundException::new);

        return ProjectResumeResponse.toDto(projectResume);
    }
}
