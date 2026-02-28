package com.RummyTriangle.domain;

public class RummyDoubleDeck extends Deck{
	
	public RummyDoubleDeck(){

		this.addStandardCardsToDeck();// the first of the two decks
		this.addJokersToDeck(2);
		this.addStandardCardsToDeck();// the second deck
		this.addJokersToDeck(2);
		this.shuffleCards();
	}
}
