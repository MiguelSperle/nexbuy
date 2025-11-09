package com.miguelsperle.nexbuy;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class NexbuyApplication {

	public static void main(String[] args) {
		SpringApplication.run(NexbuyApplication.class, args);
	}

}
