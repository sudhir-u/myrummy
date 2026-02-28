package com.RummyTriangle.service;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.RummyTriangle.domain.Deck;
import com.RummyTriangle.domain.DiscardSet;
import com.RummyTriangle.domain.PlayingCard;


public class GameService implements IGameService{

	protected static Logger logger = Logger.getLogger("GameService");
	
	private String ID;
	private ArrayList<Player> players =  new ArrayList<Player>();

	@Autowired
	private Deck cardDeck;
	private DiscardSet discardSet = new DiscardSet();
	private PlayingCard jokerCard;
	
	
	public GameService (){

		ID = "1";
		
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}


	public Deck getCardDeck() {
		return cardDeck;
	}

	public void setCardDeck(Deck cardDeck) {
		this.cardDeck = cardDeck;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public int getPlayerCount (){
		return players.size();
	}

	public void addCardDeck() {
		// TODO Auto-generated method stub
		
	}

	public void addPlayer(Player player) {
		players.add(player);
		
	}

	public void dealCards() {
		
		logger.info("Dealing cards...");
		
		if (getPlayerCount() > 0 ){
		
			logger.info("Players are non-zero. The count is " + getPlayerCount());
			
			for (int zz=0;zz<getPlayerCount() ;zz++){

				logger.info("Deck size is " + cardDeck.getPlayingCards().size() + ".");	
				if (cardDeck.HasCards(13)) {
					
					Hand hand = new Hand();
					//assert hand.getCardCount() == 1;

					hand.addCards(cardDeck.drawRandomCards(13));
					players.get(zz).setHand(hand);
					hand = null;
					
				}
				
				
			}

		}
		
	}

	public void play() {
		// TODO Auto-generated method stub
		logger.info("Game is on...");
	}

	public void pullJoker() {
		logger.info("Pulling joker card...");
		jokerCard = cardDeck.pullCardAtIndex(cardDeck.pickRandomIndex()) ;
		logger.info("The joker card is: " + jokerCard.getDescription());
		
	}
	
	public PlayingCard getJoker(){
		return jokerCard;
	}
	
}
