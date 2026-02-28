package com.RummyTriangle.service;

import com.RummyTriangle.domain.User;

public class Player {

	String playerId;
	User user;
	GameService game;
	Hand hand;
	
	public String getPlayerId() {
		return playerId;
	}
	
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public GameService getGame() {
		return game;
	}
	
	public void setGame(GameService game) {
		this.game = game;
	}

	public Hand getHand() {
		return hand;
	}
	
	public void setHand(Hand hand) {
		this.hand = hand;
	}
	
}
