package com.in28minutes.microservice.springcurrencyconversionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SpringCurrencyConversionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCurrencyConversionServiceApplication.class, args);
	}

}
