package com.jaenyeong.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApplication.class, args);
	}

	// configs/AppConfig 파일로 이동
//	@Bean
//	public ModelMapper modelMapper() {
//		return new ModelMapper();
//	}
}
