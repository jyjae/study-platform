package com.example.studyplatform.domain.attend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendRepository extends JpaRepository<Attend, Long> {

    List<Attend> findByCalenderId(Long calenderId);

}
