package com.RummyTriangle.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
//import org.apache.log4j.Logger;
import java.util.logging.Logger;


public class CardGroup {
	
	protected static Logger logger = Logger.getLogger("CardGroup");

	private ArrayList<PlayingCard> cards = new ArrayList<PlayingCard>();
	private Suits suit;
	private CardGroupType type;
	
	
	public CardGroupType getType() {
		return type;
	}

	public void setType(CardGroupType type) {
		this.type = type;
	}

	public CardGroup(){
		this.type = CardGroupType.NONE;
	}
	
	public ArrayList<PlayingCard> getCards() {
		return cards;
	}

	public void setCards(ArrayList<PlayingCard> cards) {
		this.cards = cards;
	}

	public Suits getSuit() {
		return suit;
	}

	public void setSuit(Suits suit) {
		this.suit = suit;
	}

	public void addCard(PlayingCard pc){
		cards.add(pc);
	}
	
	public int getCardCount(){
		return cards.size();
	}
	
	public PlayingCard getCard(int cardIndex){
		return cards.get(cardIndex);
	}

	public void removeCard(int cardIndex){
		cards.remove(cardIndex); 
	}

	public void insertCardAtIndex(int cardIndex, PlayingCard pc){
		cards.add(cardIndex, pc);; 
	}

	
	public String toString(){
		
		int cardCount = cards.size();
		String desc = new String(); 
		
		if (cardCount>0) {
			for (int cardIndex = 0 ; cardIndex < cardCount; cardIndex++ ){

				desc = desc + cards.get(cardIndex).getDescription() + "\n";
			}
			desc = desc + this.getType();
		}
		return desc;
	}
	
	private int getMissingCardCountInSequence() {
		
		return cards.size()-1;
		
	}
	public boolean isPureSequence(){
		//System.out.println("In pure sequence check");
		if (!hasMinimumSetCount()) {return false;}
		
		if (!hasSameSuit(cards)) {return false;}
		
		ArrayList<StandardPlayingCard> sortedCards = new ArrayList<StandardPlayingCard>();

		for (int cardIndex =0;cardIndex < cards.size();cardIndex++){
			
			sortedCards.add((StandardPlayingCard)cards.get(cardIndex));
			
		}

		Collections.sort(sortedCards);

		//in case of special sequences (A,2,3) (A,2,3,4,5) etc., remove card A, and then feed into normal sequence check
		// this is TWO card
		if (sortedCards.get(0).getRank().getPriority() == 13 
				// this is ACE 
				&&  sortedCards.get(sortedCards.size()-1).getRank().getPriority() == 1) {
			sortedCards.remove(sortedCards.size()-1);
		};
		
		// normal sequence check
		int tempCardRankPriority  = sortedCards.get(0).getRank().getPriority();
		for (int cardIndex =1;cardIndex < sortedCards.size();cardIndex++){
			tempCardRankPriority --;
			if (tempCardRankPriority !=  sortedCards.get(cardIndex).getRank().getPriority()) { return false;};
		}

		return true; 
	}
	
	public boolean isValidSequence(ArrayList<PlayingCard> jokerCardsSuperSet){
		//System.out.println("Inside valid sequence check");
		if (!hasMinimumSetCount()) {return false;}

		// this is important because we do not have to check with respect to if the joker is also of same suit. 
		if (isPureSequence()) {return true;}

		//checking sequence with joker in place
		///remove the cards that are jokers from among the cards, 
		///then sort the cards, and 
		///then check the count of cards that are missing from ***within*** the range
		/// if the missing cards are less than or equal to the count of jokers we are good. 
		//All this could have been done as part of profiling (outside of isPureSequence and IsValidSequence): whether has minimum count for set, and/or is pure sequence, and/or is sequence with joker, or is a set same rank, and different suits, or is a set only that doesnt fall in any category   

		ArrayList<PlayingCard> qualifiedJokersInGroup = getQualifiedJokersInGroup(jokerCardsSuperSet);
		int qualifiedJokersInGroupCount = qualifiedJokersInGroup.size();

		ArrayList<PlayingCard> localCards = cardsInGroupJokersRemoved(qualifiedJokersInGroup);

        // If there is only one card after removing jokers, it is obviously a valid sequence
        if (localCards.size() <= 1) {return true;}
        
        if (!hasSameSuit(localCards)) {return false;}

		ArrayList<StandardPlayingCard> sortedCards = new ArrayList<StandardPlayingCard>();

		for (int cardIndex=0;cardIndex < localCards.size();cardIndex++){

			sortedCards.add((StandardPlayingCard)localCards.get(cardIndex));

		}

		Collections.sort(sortedCards);

		//in case of special sequences (A,2,3) (A,2,3,4,5) etc., remove card A, and then feed into normal sequence check
		if (sortedCards.get(0).getRank().getPriority() == 13 &&  sortedCards.get(sortedCards.size()-1).getRank().getPriority() == 1) {
			sortedCards.remove(sortedCards.size()-1);
		};
		
		// computation to find the filler number of cards required looking at the card we have in the group and the jokers 
		return (sortedCards.get(0).getRank().getPriority() -  sortedCards.get(sortedCards.size()-1).getRank().getPriority() <= (sortedCards.size() - 1 +  qualifiedJokersInGroupCount));

	}
	
	private  ArrayList<PlayingCard> cardsInGroupJokersRemoved(ArrayList<PlayingCard> qualifiedJokersInGroup) {

		ArrayList<PlayingCard> localCards = new ArrayList<PlayingCard>();

		//checking sequence with joker in place
		///remove the cards that are jokers from among the cards, 

		boolean skipthiscard = false; 
        for (PlayingCard card : cards) {
    		if (card.isContainedIn(qualifiedJokersInGroup)) {
    			skipthiscard = true;
    		} 
        	if (!skipthiscard) {
        		localCards.add(card);
        	}
        	skipthiscard = false;
        }
        return localCards;
	}

	public boolean hasMinimumSetCount(){
		if (cards.size() >= 3 ) 
		{
			return true;
		}
		return false;
	}

	public boolean hasSameSuit(ArrayList<PlayingCard> cards){

		if (cards.stream()
				.filter (o ->  o.getCardType().equals(PlayingCardTypes.JOKER) || o.getCardType().equals(PlayingCardTypes.KNIGHT))
				.count() > 0) 
					{return false;} 
		
		StandardPlayingCard thefirstCard = (StandardPlayingCard) cards.get(0);
				int suitOrderForFirstCard = thefirstCard.getSuit().getSuitOrder();

		if (cards.stream()
				.filter(o ->  o.getCardType().equals(PlayingCardTypes.STANDARD)) 
				.filter(o ->  ((StandardPlayingCard) o).getSuit().getSuitOrder() == suitOrderForFirstCard)
				.count() == cards.size()) 
		{
			//System.out.println("Same suit aint it...");
			return true;
		}
		//System.out.println("Not same suit aint it...");
		return false;

	}

	public boolean isPureSet(){
		//System.out.println("In pure set check");
		if (!hasMinimumSetCount()) return false;
		if (!hasSameRank(cards)) return false;
		if (hasDuplicates(cards)) return false;

		return true;
	}

	public boolean isValidSet(ArrayList<PlayingCard> jokerCardsSuperSet){
		//System.out.println("In valid set check");
		if (!hasMinimumSetCount()) {return false;}
		if (isPureSet()) {return true;}

		ArrayList<PlayingCard> qualifiedJokersInGroup = getQualifiedJokersInGroup(jokerCardsSuperSet);

		ArrayList<PlayingCard> localCards = cardsInGroupJokersRemoved(qualifiedJokersInGroup);

        // If there is only one card after removing jokers, it is obviously a valid sequence
        if (localCards.size() <= 1) {return true;}

		if (!hasSameRank(localCards)) return false;
		if (hasDuplicates(localCards)) return false;
        
		return true;

	}

	public boolean hasSameRank(ArrayList<PlayingCard> cardsToCheck){
		
		StandardPlayingCard theFirstCard = (StandardPlayingCard)cards.get(0); 
		if (cardsToCheck.stream()
				.filter(o ->  o.getCardType().equals(PlayingCardTypes.STANDARD)) 
				.filter(o ->  ((StandardPlayingCard) o).getRank().equals(theFirstCard.getRank()))
				.count() == cardsToCheck.size()) {return true;} 
		return false;

	}

	public int getPoints(ArrayList<PlayingCard> jokerCardsSuperSet) {

		ArrayList<PlayingCard> qualifiedJokersInGroup = getQualifiedJokersInGroup(jokerCardsSuperSet);
		ArrayList<PlayingCard> localCards = cardsInGroupJokersRemoved(qualifiedJokersInGroup);
		
		return localCards.stream()
			.filter(c -> c.getCardType().equals(PlayingCardTypes.STANDARD))
			.mapToInt(o ->  ((StandardPlayingCard) o).getRank().getPoints())
			.sum();

	}

	public boolean hasDuplicates(ArrayList<PlayingCard> cardsToCheck){
		
		Set<PlayingCard> distinctCards = new HashSet<PlayingCard>(cardsToCheck);
		if (distinctCards.size() != cardsToCheck.size())
			return true;
		
		return false;
	}

	public boolean hasAllStandardCards(){

		for (int cardIndex = 0; cardIndex < cards.size(); cardIndex++) {
			if (cards.get(cardIndex).getCardType() != PlayingCardTypes.STANDARD) {
				return false;
			}
		}
		return !cards.isEmpty();
	}

	public int getJokerCount(PlayingCard cardAsJoker) {

		int jokerCount = 0;

		for (int i=0; i<this.getCardCount();i++) {
			if (cards.get(i).isJokerInContext(cardAsJoker)) {jokerCount+=1;}
		}

		return jokerCount;
	}


	public ArrayList<PlayingCard> getQualifiedJokersInGroup(ArrayList<PlayingCard> cardsAsJoker) {

		ArrayList<PlayingCard> intersectingCards = new ArrayList<PlayingCard>(); 

        for (PlayingCard card : cards) {        	
        		if (card.isContainedIn(cardsAsJoker)) 
        			intersectingCards.add(card);

        }
		return intersectingCards;

	}
}
