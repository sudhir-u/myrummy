package com.RummyTriangle.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.RummyTriangle.domain.Deck;
import com.RummyTriangle.domain.RummySingleDeck;

@Configuration
public class AppConfiguration {

	@Bean
	public Deck deck() {
		return new RummySingleDeck();
	}
}
