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
	
	public PlayingCard pullOpenCard(){
		
		PlayingCard pc = cards.element();

		cards.removeLast();
		
		return pc;
	}

	public PlayingCard getOpenCard(){

		PlayingCard pc = cards.element();
		
		return pc;
	}

}
