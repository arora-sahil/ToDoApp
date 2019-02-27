package com.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
@EntityScan(basePackages= {"com.todo.repository"})
public class ToDoAppProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToDoAppProjectApplication.class, args);
	}

}
