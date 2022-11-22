package com.work.planningservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class PlanningServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlanningServiceApplication.class, args);
	}

}
