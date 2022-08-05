package com.example.studyplatform.factory.entity;

import com.example.studyplatform.domain.project.projectPost.ProjectPost;
import com.example.studyplatform.domain.user.User;

import java.time.LocalDateTime;

import static com.example.studyplatform.factory.entity.UserFactory.createUser;

public class ProjectPostFactory {
    public static ProjectPost createProjectPost(User user) {
        return ProjectPost.builder()
                .title("title")
                .content("content")
                .isOnline(true)
                .isMike(true)
                .isCamera(true)
                .recruitStartedAt(LocalDateTime.now())
                .projectEndedAt(LocalDateTime.now())
                .recruitEndedAt(LocalDateTime.now())
                .projectStartedAt(LocalDateTime.now())
                .user(user)
                .build();
    }
}
