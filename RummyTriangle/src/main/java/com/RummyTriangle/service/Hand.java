package com.RummyTriangle.service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.RummyTriangle.domain.CardGroup;
import com.RummyTriangle.domain.CardGroupType;
import com.RummyTriangle.domain.PlayingCard;
import com.RummyTriangle.domain.StandardPlayingCard;
import com.RummyTriangle.domain.Suits;

public class Hand {

	protected static Logger logger = Logger.getLogger("Hand");
	
	private ArrayList<CardGroup> cardGroups = new ArrayList<CardGroup>();
	private ArrayList<PlayingCard> allSupportedjokers = new ArrayList<PlayingCard>();
	
	@Autowired 
	private GameService gameService;

	private enum CountState {CARD_DRAWN, NORMAL};
	
	
//	private enum CardGroupStatus {IS_PURE_RUN, IS_VALID_RUN, IS_PURE_SET, IS_VALID_SET, IS_NONE};
//
//	public class CardsGroup {
//		CardGroup cardGroup;
//		public CardGroup getCardGroup() {
//			return cardGroup;
//		}
//
//		public void setCardGroup(CardGroup cardGroup) {
//			this.cardGroup = cardGroup;
//		}
//
//		CardGroupStatus status;
//		
//		public CardGroupStatus getStatus() {
//			return status;
//		}
//
//		public void setStatus(CardGroupStatus status) {
//			this.status = status;
//		}
//
//		public CardsGroup(CardGroup cardGroup) {
//			this.cardGroup = cardGroup;
//			status = CardGroupStatus.IS_NONE;
//		}
//	}
//
//	private ArrayList<CardsGroup> cardsGroups;

	public Hand(){
		//cardGroups = new ArrayList<CardGroup>();
		//cardsGroups = new ArrayList<CardsGroup>();
	}
	
	public Hand(ArrayList<PlayingCard> allSupportedjokers){
		super();
		this.allSupportedjokers = allSupportedjokers;
	}

	private void getCardsFromGroupsIntoSingleArray(ArrayList<PlayingCard> cards) {

		for (int cg=0;cg < cardGroups.size();cg++){
			for (int c=0;c < cardGroups.get(cg).getCardCount(); c++  ){
				cards.add(cardGroups.get(cg).getCard(c));
			}
		}
	}

	//adds card to the cardGroup at last index. if doesnt exist, creates a card group
	public void addCard(PlayingCard pc){

		
//		if (cardsGroups.size()==0) 
//			cardsGroups.add(new CardsGroup(new CardGroup()));
		
		if (cardGroups.size()==0) 
			cardGroups.add(new CardGroup());

//		cardsGroups.get(cardsGroups.size()-1).getCardGroup().addCard(pc);		
 		cardGroups.get(cardGroups.size()-1).addCard(pc);

	}

	//adds array of cards to the cardGroup at last index. (if doesnt exist, creates a card group)
	public void addCards(ArrayList<PlayingCard> cards){

		for (int cardIndex=0; cardIndex < cards.size(); cardIndex++){
			this.addCard(cards.get(cardIndex));
		}

	}

	// adds cards to a new instance of Cardgroup and adds the cardGroup to the hand
	public void addCardGroupOfCards(ArrayList<PlayingCard> cards){
		CardGroup cardGroup = new  CardGroup();
		for (PlayingCard card: cards) {
			cardGroup.addCard(card);
		}
		cardGroups.add(cardGroup);
		//cardsGroups.add( new CardsGroup(cardGroup));
		
	}

	public int getCardCount(){

		return getAllCards().size();
	}

	public ArrayList<PlayingCard> getAllCards(){

		ArrayList<PlayingCard> cards = new ArrayList<PlayingCard>();
		for (int cg=0;cg < cardGroups.size();cg++){
			for (int c=0;c < cardGroups.get(cg).getCardCount(); c++  ){
				cards.add(cardGroups.get(cg).getCard(c));
			}
		}

		return cards;
	}

	public PlayingCard discardCard(int cardGroupIndex, int cardIndexInGroup){

		PlayingCard pc = cardGroups.get(cardGroupIndex).getCard(cardIndexInGroup);
		cardGroups.get(cardGroupIndex).removeCard(cardIndexInGroup);
		
		if (cardGroups.get(cardGroupIndex).getCardCount() == 0) { removeCardGroup(cardGroupIndex); } ;
		
		return pc;
		
	}

	private void removeCardGroup (int cardGroupIndex){
		
		cardGroups.remove(cardGroupIndex);
		
	}

	private void removeAllEmptyCardGroups (){
		
		for (int cgIndex=cardGroups.size()-1;cgIndex >= 0 ;cgIndex--){

			if (cardGroups.get(cgIndex).getCardCount()==0) { 
				cardGroups.remove(cgIndex) ;
			}
		}
	}

	public void sortIntoGroupsOnRequest(){
		
		ArrayDeque<PlayingCard> playingCardDeque = new ArrayDeque<PlayingCard>(); 
		
		
		transferAllCardsToStack(playingCardDeque);
		logger.info("Card group count after transfering all cards to stack: " + cardGroups.size());
		
		addEmptyGroupsJokersAndStandard();
		logger.info("Card group count after adding empty groups for jokers and standard cards to stack: " + cardGroups.size());
		
		
		int totalCardCount =  playingCardDeque.size(); 
		
		for (int cardIndex=0;cardIndex < totalCardCount ;cardIndex++){
			
			PlayingCard pc = playingCardDeque.remove();
			//logger.info("Card (" + cardIndex + ") removed from deques is " + pc.getDescription());
			
			switch (pc.getCardType()) { 
				case STANDARD: 
			
					StandardPlayingCard spc = (StandardPlayingCard)pc;
					
					//the order: SPADES, HEARTS, DIAMONDS, CLUBS;
					for (int suitIndex=1; suitIndex <= Suits.values().length ; suitIndex++){
						
						if (cardGroups.get(suitIndex).getSuit()==spc.getSuit()){

							cardGroups.get(suitIndex).addCard(spc);
							//System.out.println("This card : " + spc.getDescription() + " went into group with suit : " + cardGroups.get(suitIndex).getSuit() );

						}

					}
					break;
					
				case JOKER :
					cardGroups.get(0).addCard(pc);
					break;
					
				case KNIGHT:
					cardGroups.get(0).addCard(pc);
					break;
			}
			
		}
		
		/*
		for (int i=0;i<cardGroups.size();i++){
			logger.info("Card group (" + i +  "): has cards: " + cardGroups.get(i).getCardCount() );
			for (int j=0 ; j< cardGroups.get(i).getCardCount(); j++){
				logger.info("Card (" + j +  "): is : " + cardGroups.get(i).getCard(j).getDescription() );
			}
		}
		*/

		removeAllEmptyCardGroups();
		logger.info("Card group count before after removing empty card groups:" + cardGroups.size());
		
	}
	
	private void transferAllCardsToStack(ArrayDeque<PlayingCard> playingCardDeque){
		
		//PlayingCard pc = new PlayingCard();
		PlayingCard pc;
		
		for (int cardGroupIndex=cardGroups.size()-1;cardGroupIndex >= 0;cardGroupIndex--){
			
			int cardCount = cardGroups.get(cardGroupIndex).getCardCount();
			for (int cardIndex=cardCount-1;cardIndex>=0;cardIndex-- ){
				
				pc = cardGroups.get(cardGroupIndex).getCard(cardIndex);
				cardGroups.get(cardGroupIndex).removeCard(cardIndex);
				playingCardDeque.add(pc);
				
			}
		}
		
		removeAllEmptyCardGroups();
	}
	
	
	
	private void addEmptyGroupsJokersAndStandard(){
		addEmptyGroupJokers();
		addEmptyStandardGroupsForAllSuits();
	}
	private void addEmptyStandardGroupsForAllSuits(){
		
		
		for (int suitIndex=0; suitIndex < Suits.values().length ; suitIndex++){
			cardGroups.add(new CardGroup());
			cardGroups.get(cardGroups.size()-1).setSuit(Suits.values()[suitIndex]);
			//logger.info("Added a new empty card group for accomodating cards with suit " + Suits.values()[suitIndex]  + " later");
		}
		
	}

	private void addEmptyGroupJokers(){
		
			cardGroups.add(new CardGroup());
			logger.info("Added a new empty card group for accomodating jokers later");
		
	}

	public int getCardGroupCount(){
		return cardGroups.size();
	}
	
	public boolean isRummy(){

		if (getPoints() == 0) {return true;};
		return false;
	}
	
	//TODO implement logic for calculating points for hand
	public int getPoints(){
		
//		cardGroups.stream()
//			.filter(cg -> cg.getType().equals(CardGroupType.NONE))
//			//.collect(Collectors.toList());
//			.forEach(cg -> {
//				System.out.println(cg.getCards().toString());     
//			}
//			);
		return 80;
	}
	
	//TODO implement logic for profiling for cards available
	public void profile(){
		for (CardGroup cardGroup: cardGroups) {
			//System.out.println("Now... cardGroup with index" + cardGroup.toString());
 			if (cardGroup.isPureSequence()) {
 				cardGroup.setType(CardGroupType.PURE_RUN);
 				continue;
 			} else 
 			if (cardGroup.isValidSequence(allSupportedjokers)) {
 				cardGroup.setType(CardGroupType.RUN);
 				continue;
 			} else
 			if (cardGroup.isPureSet()) {
 				cardGroup.setType(CardGroupType.PURE_SET);
 				continue;
 			} else
 			if (cardGroup.isValidSet(allSupportedjokers)) {
 				cardGroup.setType(CardGroupType.SET);
 				continue;
 			} else 
 				cardGroup.setType(CardGroupType.NONE);
		}
	}

	public void createNewGroupOnRequest() {
		cardGroups.add(new CardGroup());
	}

   //TODO Handle for non-existing cardindex, or non-existing sourcecardgroup or non-existing destcardgroupIndex still to be implemented
	public void moveCardBetweenGroupsOnRequest(int cardIndex, int sourceCardGroupIndex, int destCardGroupIndex){
		
		PlayingCard pc = cardGroups.get(sourceCardGroupIndex).getCard(cardIndex);
		cardGroups.get(sourceCardGroupIndex).removeCard(cardIndex);
		cardGroups.get(destCardGroupIndex).addCard(pc);

		removeAllEmptyCardGroups();

	}
	
	public CardGroup getCardGroupAtIndex(int cardGroupIndex){
		return cardGroups.get(cardGroupIndex);
	}

	public void moveCardInGroupOnRequest(int CardGroupIndex, int sourceIndex, int destIndex ){
		
		PlayingCard pc = cardGroups.get(CardGroupIndex).getCard(sourceIndex);
		cardGroups.get(CardGroupIndex).removeCard(sourceIndex);
		cardGroups.get(CardGroupIndex).insertCardAtIndex(destIndex, pc);

	}

	
}
