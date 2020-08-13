package com.inbiznetcorp.acs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan("com.inbiznetcorp")
@EnableScheduling
@Configuration
@EnableAutoConfiguration
public class ArsCustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArsCustomerServiceApplication.class, args);
	}

}
