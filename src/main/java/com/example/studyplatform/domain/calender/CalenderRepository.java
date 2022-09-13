package com.example.studyplatform.domain.calender;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalenderRepository extends JpaRepository<Calender, Long> {
    List<Calender> findByStudy_Id(Long studyId);
}
