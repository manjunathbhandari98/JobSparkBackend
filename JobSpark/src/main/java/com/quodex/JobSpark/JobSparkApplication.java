package com.quodex.JobSpark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JobSparkApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobSparkApplication.class, args);
	}

}
