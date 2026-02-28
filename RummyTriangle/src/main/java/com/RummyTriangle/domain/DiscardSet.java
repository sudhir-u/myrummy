package com.RummyTriangle.domain;

import java.util.ArrayDeque;

public class DiscardSet {

	private ArrayDeque<PlayingCard> cards;


	public DiscardSet(){
		cards = new ArrayDeque<PlayingCard>(); 
	}
	

	public ArrayDeque<PlayingCard> getCards() {
		return cards;
	}
	
	
	
	public void addCard(PlayingCard pc){
		cards.add(pc);
	}
	
	public int getCardCount(){
		return this.getCards().size() ;
	}
	
	/** Removes and returns the top (most recently discarded) card. */
	public PlayingCard pullOpenCard(){
		return cards.isEmpty() ? null : cards.removeLast();
	}

	/** Returns the top (most recently discarded) card without removing. */
	public PlayingCard getOpenCard(){
		return cards.isEmpty() ? null : cards.getLast();
	}

}
