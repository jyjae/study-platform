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
import java.util.ArrayList;
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

    // 관계 매핑 시 초기화 해주는 것이 좋음
    @OneToMany(fetch = FetchType.LAZY)
    private List<TechStack> techStacks = new ArrayList<>();

    @OneToMany(fetch=FetchType.LAZY, orphanRemoval = true)
    private List<Career> careers = new ArrayList<>();

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

    // List 형식 매개변수 빼야됨 -> add 함수 이용
    @Builder
    public User(String username, String nickname, String email, String password, String profileImg) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.profileImg = profileImg;
        this.role = Role.USER;
        this.status = Status.ACTIVE;
    }
}
