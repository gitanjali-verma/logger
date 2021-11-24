package com.digitaldots.logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.digitaldots")
public class JaegerKafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JaegerKafkaApplication.class, args);
	}
}
