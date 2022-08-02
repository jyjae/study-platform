package com.example.studyplatform.domain.project.projectOrganization;

import com.example.studyplatform.constant.Status;
import com.example.studyplatform.domain.BaseTimeEntity;
import com.example.studyplatform.domain.techStack.TechStack;
import com.example.studyplatform.exception.ProjectAccommodateZeroException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ProjectOrganization extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO : 경력 조건 유무?

    @ManyToOne(fetch = FetchType.EAGER)
    private TechStack techStack;

    Boolean isDeadLine;

    int personnel;

    Status status;

    public void accommodate() {
        if(this.personnel > 0){
            this.personnel--;
        }else{
            throw new ProjectAccommodateZeroException();
        }
    }

    @Builder
    public ProjectOrganization(TechStack techStack, Boolean isDeadLine, int personnel) {
        this.techStack = techStack;
        this.isDeadLine = isDeadLine;
        this.personnel = personnel;
        this.status = Status.ACTIVE;
    }
}
