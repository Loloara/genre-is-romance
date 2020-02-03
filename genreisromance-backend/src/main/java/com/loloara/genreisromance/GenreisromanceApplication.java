package com.loloara.genreisromance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GenreisromanceApplication {

	public static void main(String[] args) {
		String profile = System.getProperty("spring.profiles.active");
		if(profile == null){
			System.setProperty("spring.profiles.active", "development");
		}

		SpringApplication.run(GenreisromanceApplication.class, args);
	}

}
