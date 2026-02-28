package com.RummyTriangle.domain;

import java.util.ArrayList;
import java.util.Collections;

import java.util.logging.Logger;

public abstract class Deck {

	protected static Logger logger = Logger.getLogger("Deck");
	
	private ArrayList<PlayingCard> playingCards = new ArrayList<PlayingCard>();

	
	public Deck(){
	}
	
	public ArrayList<PlayingCard> getPlayingCards() {
		return playingCards;
	}
		
	
	public void setPlayingCards(ArrayList<PlayingCard> playingCards) {
		this.playingCards = playingCards;
	}
	
	public void addPlayingCard(PlayingCard pc){
		playingCards.add(pc);
	}
	
	public int getCardCount(){
		return this.getPlayingCards().size() ;
	}
	
	public void addJokersToDeck(int numberOfJokers){
		for (int i=0; i< numberOfJokers;i++){
			playingCards.add(new JokerPlayingCard());
		}
	}
	
	public void addStandardCardsToDeck(){
		
		//////Add all standard cards.//////
		//The method to do so is in the StandardPlayingCard class. The only reason behind creating an instance of the class.
		StandardPlayingCard stdcard = new StandardPlayingCard(); 
		//Playing cards array list already instantiated in parent PlayingCardDeck class. Now, invoking the getAllStandardCards() in the freshly instantiated StandardPlayingCard class 
		//to create the list of standard cards, and then assign to the existing arraylist (in PlayingCardDeck class).
		stdcard.addAllStandardCards(this.getPlayingCards());
	}

	public PlayingCard pullCardAtIndex(int index){

		PlayingCard pc = playingCards.get(index);
		playingCards.remove(index);
		return pc;
		
	}

	public int pickRandomIndex(){
		int cardCount = getCardCount();
		
		//Min + (int)(Math.random() * ((Max - Min) + 1)) //picked from this
		int rnd = 0 + (int)(Math.random() * (((cardCount - 1) - 0) + 1));

		logger.info("CardCount: " + cardCount  + ". Random index: " + rnd);

		return rnd;
	}
	
	public void shuffleCards(){
		Collections.shuffle(playingCards);
	}

	public ArrayList<PlayingCard> drawRandomCards(int numberOfCards){
		ArrayList<PlayingCard> randomCards = new ArrayList<PlayingCard>(); 
		for (int i=0;i<numberOfCards;i++){
			randomCards.add(pullCardAtIndex(pickRandomIndex()));
		}
		return randomCards;
	}
	
	public boolean HasCards(int numberOfCards){
		if (playingCards.size() < numberOfCards){
			return false;
		}
		return true;
	}
}
