package com.example.studyplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class StudyPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudyPlatformApplication.class, args);
	}

}
