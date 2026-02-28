package com.RummyTriangle.domain;

import java.util.List;

public class JokerPlayingCard extends PlayingCard{
	
	public JokerPlayingCard(){
		this.setCardType(PlayingCardTypes.JOKER);
	}

	public String getDescription(){
		return "(Type:" + this.getCardType() + ") Joker.";
		
	}

	public boolean isContainedIn( List<PlayingCard> cardList){
		return cardList.stream()
				.filter(o ->  o.getCardType().equals(PlayingCardTypes.JOKER))
				.findFirst()
				.isPresent();
	}
	public int compareTo(JokerPlayingCard jokerPlayingCard) {
		return 0;
	}

	public boolean isJokerInContext(PlayingCard gameJokerCard){
		return true;
	}
	
}
