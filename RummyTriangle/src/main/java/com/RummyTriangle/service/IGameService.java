package com.RummyTriangle.service;


public interface IGameService {

	void addCardDeck();
	void addPlayer(Player player);
	void dealCards();
	void pullJoker();
	void play();
	
}
