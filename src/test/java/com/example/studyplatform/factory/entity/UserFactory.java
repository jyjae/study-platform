package com.example.studyplatform.factory.entity;

import com.example.studyplatform.domain.career.Career;
import com.example.studyplatform.domain.techStack.TechStack;
import com.example.studyplatform.domain.user.User;

public class UserFactory {
    public static User createUser() {
        return User.builder().username("username").profileImg("img").password("password")
                .nickname("nickname").email("email").build();
    }

    public static User createUser(Career career, TechStack techStack) {
        User user =  User.builder().username("username").profileImg("img").password("password")
                .nickname("nickname").email("email").build();

        user.addCareer(career);
        user.addTechStack(techStack);

        return user;
    }
}
