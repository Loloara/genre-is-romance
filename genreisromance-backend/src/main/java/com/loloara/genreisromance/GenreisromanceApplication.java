package com.loloara.genreisromance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class GenreisromanceApplication {

	public static void main(String[] args) {
		String profile = System.getProperty("spring.profiles.active");
		if(profile == null) {
			System.setProperty("spring.profiles.active", "development");
			profile = "development";
		}

		log.info("Server Started with profile : {}", profile);

		SpringApplication.run(GenreisromanceApplication.class, args);
	}

}
