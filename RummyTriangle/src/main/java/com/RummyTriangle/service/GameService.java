package com.RummyTriangle.service;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import com.RummyTriangle.domain.CardGroup;
import com.RummyTriangle.domain.Deck;
import com.RummyTriangle.domain.DiscardSet;
import com.RummyTriangle.domain.GameState;
import com.RummyTriangle.domain.JokerPlayingCard;
import com.RummyTriangle.domain.PlayingCard;


public class GameService implements IGameService{

	protected static Logger logger = Logger.getLogger("GameService");

	private String ID;
	private ArrayList<Player> players = new ArrayList<>();
	private GameState state = GameState.LOBBY;

	@Autowired
	private Deck cardDeck;
	private DiscardSet discardSet = new DiscardSet();
	private PlayingCard jokerCard;
	private int currentPlayerIndex;
	private boolean currentPlayerMustDiscard;  // true after current player has drawn, until they discard
	private PlayingCard firstCardFromDeck;  // Card to seed discard pile at game start

	public GameService() {
		ID = "1";
	}

	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public DiscardSet getDiscardSet() {
		return discardSet;
	}

	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}

	public Player getCurrentPlayer() {
		return players.isEmpty() ? null : players.get(currentPlayerIndex);
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
		if (state != GameState.LOBBY && state != GameState.DEAL) {
			throw new IllegalStateException("Cannot deal in state: " + state);
		}
		state = GameState.DEAL;
		logger.info("Dealing cards...");

		if (getPlayerCount() > 0) {
			for (int zz = 0; zz < getPlayerCount(); zz++) {
				if (cardDeck.HasCards(13)) {
					Hand hand = new Hand();
					hand.addCards(cardDeck.drawRandomCards(13));
					players.get(zz).setHand(hand);
				}
			}
			firstCardFromDeck = cardDeck.HasCards(1)
					? cardDeck.pullCardAtIndex(cardDeck.pickRandomIndex())
					: null;
		}
	}

	/** Call after dealCards to complete DEAL phase: pull joker, seed discard pile, go to PLAY. */
	public void startPlay() {
		if (state != GameState.DEAL) {
			throw new IllegalStateException("startPlay requires state DEAL, got: " + state);
		}
		pullJoker();
		if (firstCardFromDeck != null) {
			discardSet.addCard(firstCardFromDeck);
		}
		currentPlayerIndex = 0;
		currentPlayerMustDiscard = false;
		state = GameState.PLAY;
		logger.info("Game in PLAY state. Current player: " + (getCurrentPlayer() != null ? getCurrentPlayer().getUser().getUserName() : "none"));
	}

	public boolean isCurrentPlayerMustDiscard() {
		return currentPlayerMustDiscard;
	}

	/** Returns player index for the given username, or -1. */
	public int getPlayerIndexByUsername(String userName) {
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getUser() != null && userName.equals(players.get(i).getUser().getUserName())) {
				return i;
			}
		}
		return -1;
	}

	/** Current player draws from deck; card is added to their hand. Returns the drawn card description or null. */
	public String drawFromDeckForCurrentPlayer() {
		if (currentPlayerMustDiscard || state != GameState.PLAY) return null;
		PlayingCard card = drawFromDeck();
		if (card == null) return null;
		Player current = getCurrentPlayer();
		if (current != null && current.getHand() != null) {
			current.getHand().addCard(card);
			currentPlayerMustDiscard = true;
			return card.getDescription();
		}
		return null;
	}

	/** Current player draws top card from discard pile; card is added to their hand. Returns the drawn card description or null. */
	public String drawFromDiscardForCurrentPlayer() {
		if (currentPlayerMustDiscard || state != GameState.PLAY) return null;
		PlayingCard card = drawFromDiscard();
		if (card == null) return null;
		Player current = getCurrentPlayer();
		if (current != null && current.getHand() != null) {
			current.getHand().addCard(card);
			currentPlayerMustDiscard = true;
			return card.getDescription();
		}
		return null;
	}

	/** Current player discards a card by groupIndex and cardIndexInGroup. Returns true if successful. */
	public boolean discardFromCurrentPlayer(int groupIndex, int cardIndexInGroup) {
		if (!currentPlayerMustDiscard || state != GameState.PLAY) return false;
		Player current = getCurrentPlayer();
		if (current == null || current.getHand() == null) return false;
		Hand hand = current.getHand();
		if (groupIndex < 0 || groupIndex >= hand.getCardGroupCount()) return false;
		CardGroup group = hand.getCardGroupAtIndex(groupIndex);
		if (cardIndexInGroup < 0 || cardIndexInGroup >= group.getCardCount()) return false;
		PlayingCard card = hand.discardCard(groupIndex, cardIndexInGroup);
		if (card != null) {
			discardToPile(card);
			currentPlayerMustDiscard = false;
			return true;
		}
		return false;
	}

	/** Draw from deck (top card). Returns null if deck empty. */
	public PlayingCard drawFromDeck() {
		if (state != GameState.PLAY || !cardDeck.HasCards(1)) {
			return null;
		}
		return cardDeck.pullCardAtIndex(cardDeck.pickRandomIndex());
	}

	/** Draw the top card from the discard pile. Returns null if empty. */
	public PlayingCard drawFromDiscard() {
		if (state != GameState.PLAY) {
			return null;
		}
		return discardSet.pullOpenCard();
	}

	/** Add a card to the discard pile (player has chosen to discard it). */
	public void discardToPile(PlayingCard card) {
		if (state != GameState.PLAY || card == null) {
			return;
		}
		discardSet.addCard(card);
		advanceToNextPlayer();
	}

	private void advanceToNextPlayer() {
		currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
	}

	/** Player declares; transitions to DECLARE then SCORE. */
	public void declare(Player declaringPlayer) {
		if (state != GameState.PLAY) {
			throw new IllegalStateException("Cannot declare in state: " + state);
		}
		state = GameState.DECLARE;
		state = GameState.SCORE;
		logger.info("Player " + (declaringPlayer != null ? declaringPlayer.getUser().getUserName() : "?") + " declared.");
	}

	public void play() {
		logger.info("Game is on... state=" + state);
	}

	public void pullJoker() {
		logger.info("Pulling joker card...");
		jokerCard = cardDeck.pullCardAtIndex(cardDeck.pickRandomIndex());
		logger.info("The joker card is: " + jokerCard.getDescription());
		applyJokersToHands();
	}

	/** Builds joker list (game joker + printed jokers) and sets on all player hands. */
	private void applyJokersToHands() {
		ArrayList<PlayingCard> jokers = new ArrayList<>();
		if (jokerCard != null) {
			jokers.add(jokerCard);
		}
		jokers.add(new JokerPlayingCard());  // Printed jokers match via type
		for (Player p : players) {
			if (p.getHand() != null) {
				p.getHand().setAllSupportedJokers(jokers);
			}
		}
	}
	
	public PlayingCard getJoker(){
		return jokerCard;
	}
	
}
