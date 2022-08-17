package com.mangapunch.mangareaderbackend;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.mangapunch.mangareaderbackend.models")
public class MangaReaderBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MangaReaderBackendApplication.class, args);
	}

}
