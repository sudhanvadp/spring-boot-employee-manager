package com.example.EmployeeManagement;

import com.example.EmployeeManagement.RabbitMQ.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeeManagementApplication {
	private static final Logger logger = LoggerFactory.getLogger(EmployeeManagementApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementApplication.class, args);
		logger.info("Server Started");
	}

}
