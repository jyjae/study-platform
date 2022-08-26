package com.example.studyplatform.domain.user;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.constant.oauth.ProviderName;
import com.example.studyplatform.domain.BaseTimeEntity;
import com.example.studyplatform.domain.board.Board;
import com.example.studyplatform.domain.career.Career;
import com.example.studyplatform.domain.studyBoard.StudyBoard;
import com.example.studyplatform.domain.studyNotice.StudyNotice;
import com.example.studyplatform.domain.studyUser.StudyUser;
import com.example.studyplatform.domain.techStack.TechStack;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseTimeEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String nickname;

    private String email;

    private String password;

    private String profileImg;

    @Enumerated(EnumType.STRING)
    private ProviderName providerName;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "User_Roles",
            joinColumns=@JoinColumn(name = "user_id")
    )
    private List<String> roles = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Status status;

    // 관계 매핑 시 초기화 해주는 것이 좋음
    @OneToMany(fetch = FetchType.LAZY)
    private List<TechStack> techStacks = new ArrayList<>();

    @OneToMany(fetch=FetchType.LAZY, orphanRemoval = true)
    private List<Career> careers = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StudyNotice> studyNotices = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StudyUser> studyUsers = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StudyBoard> studyBoards = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    public List<Board> scrapBoards = new ArrayList<>();

//    public List<String> getRole(){
//        return this.roles.stream()
//                .map(Role::getKey)
//                .collect(Collectors.toList());
//    }

    public void inActive() {
        this.status = Status.INACTIVE;
    }

    public void addCareer(Career career){
        this.careers.add(career);
    }

    public void addTechStack(TechStack techStack) {
        this.techStacks.add(techStack);
    }

    public void addScrapBoard(Board board) {
        this.scrapBoards.add(board);
    }

    public void deleteScrapBoard(Board board) {
        this.scrapBoards.remove(board);
    }

    // List 형식 매개변수 빼야됨 -> add 함수 이용
    @Builder
    public User(
            String username,
            String nickname,
            String email,
            String password,
            String profileImg,
            List<String> roles,
            ProviderName providerName
    ) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.profileImg = profileImg;
        this.status = Status.ACTIVE;
        this.roles = roles;
        this.providerName = providerName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
