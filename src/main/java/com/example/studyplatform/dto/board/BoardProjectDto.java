package com.example.studyplatform.dto.board;

import com.example.studyplatform.domain.project.projectPost.ProjectPost;
import com.example.studyplatform.dto.project.ProjectOrganizationDto;
import com.example.studyplatform.dto.techStack.TechStackDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BoardProjectDto extends BoardDto{
    private List<ProjectOrganizationDto> organizations;

    public BoardProjectDto(ProjectPost projectPost) {
        this.id = projectPost.getId();
        this.dtype = projectPost.getDtype();
        this.nickname = projectPost.getUser().getNickname();
        this.title = projectPost.getTitle();
        this.isOnline = projectPost.getIsOnline();
        this.isCamera = projectPost.getIsCamera();
        this.isMike = projectPost.getIsMike();
        this.isFinish = projectPost.getIsFinish();
        this.recruitEndedAt = projectPost.getRecruitEndedAt();
        this.recruitStartedAt = projectPost.getRecruitStartedAt();
        this.startedAt = projectPost.getStartedAt();
        this.endedAt = projectPost.getEndedAt();
        this.organizations = projectPost.getOrganizations().stream().map(i ->
                ProjectOrganizationDto.toDto(i, TechStackDto.toDto(i.getTechStack()))).collect(Collectors.toList());
    }

}
