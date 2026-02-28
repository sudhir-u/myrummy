package com.RummyTriangle.domain;

import java.util.ArrayList;
import java.util.List;

public class StandardPlayingCard extends PlayingCard implements Comparable<StandardPlayingCard>{
	
	private Suits suit;
	private Ranks rank;
	
	public Suits getSuit() {
		return suit;
	}
	public void setSuit(Suits suit) {
		this.suit = suit;
	}
	public Ranks getRank() {
		return rank;
	}
	public void setRank(Ranks rank) {
		this.rank = rank;
	}

	public StandardPlayingCard (){
		this.setCardType(PlayingCardTypes.STANDARD);
	}

	public StandardPlayingCard (Suits s, Ranks r){
		this.setCardType(PlayingCardTypes.STANDARD);
		suit = s;
		rank = r;
	}

	public String getDescription(){
		return "(Type:" + this.getCardType() + ") " + rank + " of " + suit + ".";
		
	}

	public boolean isContainedIn( List<PlayingCard> cardList){
		return cardList.stream()
				.filter(o ->  o.getCardType().equals(PlayingCardTypes.STANDARD)) 
				.filter(o ->  ((StandardPlayingCard) o).getRank().equals(this.rank))
				.filter(o ->  ((StandardPlayingCard) o).getSuit().equals(this.suit))
				.findFirst()
				.isPresent();
	}
	
	public ArrayList<PlayingCard> addAllStandardCards(ArrayList<PlayingCard> playingCards){
		
		for (Suits s: Suits.values()){
			for (Ranks r : Ranks.values()){
				PlayingCard c = new StandardPlayingCard(s,r);
				playingCards.add(c);
			}
		}
		
		return playingCards;
		
	}

	public int compareTo(StandardPlayingCard standardPlayingCard) {

		if (this.getSuit().getSuitOrder() < standardPlayingCard.getSuit().getSuitOrder())  
			return 1;
		else if (this.getSuit().getSuitOrder() == standardPlayingCard.getSuit().getSuitOrder()){
			if (this.getRank().getPriority() < standardPlayingCard.getRank().getPriority())
				return 1;
			else if (this.getRank().getPriority() == standardPlayingCard.getRank().getPriority())
				return 0;
			else 
				return -1;
		}
		else 
			return -1;
	}
	
	public boolean isJokerInContext(PlayingCard gameJokerCard){
		
		StandardPlayingCard jokerCard = new StandardPlayingCard();
		
		if (gameJokerCard.getCardType()==PlayingCardTypes.JOKER) {
			jokerCard.setRank(Ranks.ACE);
		} else{
			jokerCard = (StandardPlayingCard)gameJokerCard;
		}

		if (rank==jokerCard.getRank()) {return true;}
			
		return false;
	}
	
	@Override
	public boolean equals(Object obj) {
		StandardPlayingCard standardPlayingCard = (StandardPlayingCard) obj; 
		if (this.getSuit().getSuitOrder() == standardPlayingCard.getSuit().getSuitOrder() &&
			 this.getRank().getPriority() == standardPlayingCard.getRank().getPriority())
				return true;
		return false;		
	}
	
	@Override
	public int hashCode() {
		return getDescription().hashCode();
	}
}



