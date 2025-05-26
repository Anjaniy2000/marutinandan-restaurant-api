package com.anjaniy.marutinandan_restaurant_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MarutinandanRestaurantApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarutinandanRestaurantApiApplication.class, args);
	}

}
