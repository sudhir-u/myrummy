package com.RummyTriangle.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.RummyTriangle.domain.IUserRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = IUserRepository.class)
public class LaunchApp {

	public static void main(String[] args) {
		SpringApplication.run(LaunchApp.class, args);
		
		//GameService game1 = new GameService();
		//game1.play();
		//System.out.println(game1.getCardDeck().getClass());

	}

}
