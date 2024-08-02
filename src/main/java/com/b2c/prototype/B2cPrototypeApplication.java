package com.b2c.prototype;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class B2cPrototypeApplication {

	public static void main(String[] args) {
		SpringApplication.run(B2cPrototypeApplication.class, args);
	}

//	@Bean
//	CommandLineRunner init(EnumConfig enumConfig) {
//		return args -> {
//			DynamicEnum colorsEnum = new DynamicEnum(enumConfig.getColors());
//			DynamicEnum shapesEnum = new DynamicEnum(enumConfig.getShapes());
//
//			System.out.println("Colors: " + colorsEnum.getValues());
//			System.out.println("Shapes: " + shapesEnum.getValues());
//		};
//	}
}
