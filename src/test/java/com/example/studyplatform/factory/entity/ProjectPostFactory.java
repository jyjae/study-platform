package com.example.studyplatform.factory.entity;

import com.example.studyplatform.domain.project.projectPost.ProjectPost;

import java.time.LocalDateTime;

public class ProjectPostFactory {
    public static ProjectPost createProjectPost() {
        return ProjectPost.builder()
                .title("title")
                .content("content")
                .isOnline(true)
                .isMike(true)
                .isCamera(true)
                .recruitStartedAt(LocalDateTime.now())
                .projectEndedAt(LocalDateTime.now())
                .recruitEndedAt(LocalDateTime.now())
                .projectStartedAt(LocalDateTime.now()).build();
    }
}
