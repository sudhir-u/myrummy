package com.RummyTriangle.domain;

public class RummySingleDeck extends Deck{
	
	
	public RummySingleDeck(){

		this.addStandardCardsToDeck();
		this.addJokersToDeck(2);
		
	}

	public RummySingleDeck(int numberOfJokers){

		this.addStandardCardsToDeck();
		this.addJokersToDeck(numberOfJokers);
	}


}
