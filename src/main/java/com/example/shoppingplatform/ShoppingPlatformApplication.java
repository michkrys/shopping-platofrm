package com.example.shoppingplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ShoppingPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingPlatformApplication.class, args);
	}

}
