package com.RummyTriangle.domain;

import java.util.List;

public class PlayingCard {

	private PlayingCardTypes cardType;
	

	public PlayingCard(){
		
	}
	
	public PlayingCardTypes getCardType() {
		return cardType;
	}

	public void setCardType(PlayingCardTypes cardType) {
		this.cardType = cardType;
	}
	
	public String getDescription(){
		return "Just a playing card.";
	}

	public boolean isContainedIn( List<PlayingCard> cardList){
		return false;
	}
	
	public boolean isJokerInContext(PlayingCard pc){
		return false;
	}

}
