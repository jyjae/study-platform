package com.example.studyplatform.domain.user;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.BaseTimeEntity;
import com.example.studyplatform.domain.career.Career;
import com.example.studyplatform.domain.techStack.TechStack;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String nickname;

    private String email;

    private String password;

    private String profileImg;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(fetch = FetchType.LAZY)
    private List<TechStack> techStacks;

    @OneToMany(fetch=FetchType.LAZY)
    private List<Career> careers;

    public String getRole(){
        return this.role.getKey();
    }

    public void inActive() {
        this.status = Status.INACTIVE;
    }

    public void addCareer(Career career){
        this.careers.add(career);
    }

    public void addTechStack(TechStack techStack) {
        this.techStacks.add(techStack);
    }

    @Builder
    public User(String username, String nickname, String email, String password, String profileImg, List<TechStack> techStacks,
                List<Career> careers) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.profileImg = profileImg;
        this.techStacks = techStacks;
        this.careers = careers;
        this.role = Role.USER;
        this.status = Status.ACTIVE;
    }
}
