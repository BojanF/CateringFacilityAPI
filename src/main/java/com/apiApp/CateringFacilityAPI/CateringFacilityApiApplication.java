package com.apiApp.CateringFacilityAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CateringFacilityApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CateringFacilityApiApplication.class, args);
	}
}
