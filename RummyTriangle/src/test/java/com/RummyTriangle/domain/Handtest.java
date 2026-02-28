package com.RummyTriangle.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.RummyTriangle.domain.JokerPlayingCard;
import com.RummyTriangle.domain.PlayingCard;
import com.RummyTriangle.domain.Ranks;
import com.RummyTriangle.domain.StandardPlayingCard;
import com.RummyTriangle.domain.Suits;
import com.RummyTriangle.service.Hand;

public class Handtest {

	@Test
	public void testAddCard() {
		
		Hand hand = new Hand();
		
		PlayingCard pc = new StandardPlayingCard(Suits.CLUBS,Ranks.ACE);
		
		hand.addCard(pc);
		
		
		assertEquals(1, hand.getCardCount());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetCardCount() {
		
		Hand hand = new Hand();
		
		PlayingCard pc = new StandardPlayingCard(Suits.CLUBS,Ranks.ACE);
				
		hand.addCard(pc);
		
		pc = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		
		hand.addCard(pc);
		
		assertEquals(2, hand.getCardCount());

	}

	@Test
	public void testSortCreateGroupsThatHasAllSuitsAndJoker() {
		
		Hand hand = new Hand();
		
		PlayingCard pc = new StandardPlayingCard(Suits.DIAMONDS,Ranks.ACE);
		
		hand.addCard(pc);
		
		pc = new StandardPlayingCard(Suits.DIAMONDS,Ranks.TWO);
		
		hand.addCard(pc);

		pc = new StandardPlayingCard(Suits.HEARTS,Ranks.ACE);
		
		hand.addCard(pc);

		pc = new StandardPlayingCard(Suits.HEARTS,Ranks.ACE);
		
		hand.addCard(pc);

		pc = new StandardPlayingCard(Suits.DIAMONDS,Ranks.TWO);
		
		hand.addCard(pc);

		pc = new StandardPlayingCard(Suits.SPADES,Ranks.ACE);
		
		hand.addCard(pc);

		pc = new StandardPlayingCard(Suits.CLUBS,Ranks.ACE);
		
		hand.addCard(pc);

		JokerPlayingCard jpc = new JokerPlayingCard();
		
		hand.addCard(jpc);

		hand.sortIntoGroupsOnRequest();
		assertEquals(5, hand.getCardGroupCount());

		
	}

	@Test
	public void testSortCreateGroupsThatHasAllSuitsAndNoJoker() {
		
		Hand hand = new Hand();
		
		PlayingCard pc = new StandardPlayingCard(Suits.DIAMONDS,Ranks.ACE);
		
		hand.addCard(pc);
		
		pc = new StandardPlayingCard(Suits.DIAMONDS,Ranks.TWO);
		
		hand.addCard(pc);

		pc = new StandardPlayingCard(Suits.HEARTS,Ranks.ACE);
		
		hand.addCard(pc);

		pc = new StandardPlayingCard(Suits.HEARTS,Ranks.ACE);
		
		hand.addCard(pc);

		pc = new StandardPlayingCard(Suits.DIAMONDS,Ranks.TWO);
		
		hand.addCard(pc);

		pc = new StandardPlayingCard(Suits.SPADES,Ranks.ACE);
		
		hand.addCard(pc);


		pc = new StandardPlayingCard(Suits.CLUBS,Ranks.ACE);
		
		hand.addCard(pc);

		hand.sortIntoGroupsOnRequest();
		assertEquals(4, hand.getCardGroupCount());

		
	}
	
	@Test
	public void testMoveCardBetweenGroupsOnRequest() {
		
		PlayingCard pc1 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.ACE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.CLUBS,Ranks.ACE);
		PlayingCard pc3 = new StandardPlayingCard(Suits.CLUBS,Ranks.FIVE);
		PlayingCard pc4 = new StandardPlayingCard(Suits.HEARTS,Ranks.FIVE);
		PlayingCard pc5 = new StandardPlayingCard(Suits.HEARTS,Ranks.SIX);
		PlayingCard pc6 = new StandardPlayingCard(Suits.CLUBS,Ranks.SIX);
		PlayingCard pc7 = new StandardPlayingCard(Suits.CLUBS,Ranks.SEVEN);
		PlayingCard pc8 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.SEVEN);
		PlayingCard pc9 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.EIGHT);
		PlayingCard pc10 = new StandardPlayingCard(Suits.CLUBS,Ranks.EIGHT);
		PlayingCard pc11 = new StandardPlayingCard(Suits.CLUBS,Ranks.NINE);
		PlayingCard pc12 = new StandardPlayingCard(Suits.HEARTS,Ranks.NINE);
		PlayingCard pc13 = new StandardPlayingCard(Suits.HEARTS,Ranks.TEN);
		
		Hand h = new Hand();
		
		h.addCard(pc1);
		h.addCard(pc2);
		h.addCard(pc3);
		h.addCard(pc4);
		h.addCard(pc5);
		h.addCard(pc6);
		h.addCard(pc7);
		h.addCard(pc8);
		h.addCard(pc9);
		h.addCard(pc10);
		h.addCard(pc11);
		h.addCard(pc12);
		h.addCard(pc13);
		
		assertEquals(1,h.getCardGroupCount());
		
		//the order: JOKERS, SPADES, HEARTS, DIAMONDS, CLUBS;
		h.sortIntoGroupsOnRequest();
		
		//HEARTS-4, DIAMONDS -3, CLUBS - 6
		assertEquals(3,h.getCardGroupCount());
		assertEquals(4,h.getCardGroupAtIndex(0).getCardCount());
		assertEquals(3,h.getCardGroupAtIndex(1).getCardCount());
		assertEquals(6,h.getCardGroupAtIndex(2).getCardCount());

		StandardPlayingCard pc = (StandardPlayingCard) h.getCardGroupAtIndex(0).getCard(0);
		
		assertEquals(Suits.HEARTS,pc.getSuit() );
		assertEquals(Ranks.TEN,pc.getRank() );
		
		//Move card(Ten Hearts) in 1st position (0) of cardgroup 0 (Hearts) to cardgroup 1 (.
		h.moveCardBetweenGroupsOnRequest(0, 0, 1);

		//HEARTS-3, DIAMONDS -4, CLUBS - 6
		assertEquals(3,h.getCardGroupCount());
		assertEquals(3,h.getCardGroupAtIndex(0).getCardCount());
		assertEquals(4,h.getCardGroupAtIndex(1).getCardCount());
		assertEquals(6,h.getCardGroupAtIndex(2).getCardCount());
		
		//
		pc = (StandardPlayingCard) h.getCardGroupAtIndex(1).getCard(3);
		
		assertEquals(Suits.HEARTS,pc.getSuit() );
		assertEquals(Ranks.TEN,pc.getRank() );
		
	}
	
	@Test
	public void testMoveCardInGroupOnRequest() {
		
		PlayingCard pc1 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.ACE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.CLUBS,Ranks.ACE);
		PlayingCard pc3 = new StandardPlayingCard(Suits.CLUBS,Ranks.FIVE);
		PlayingCard pc4 = new StandardPlayingCard(Suits.HEARTS,Ranks.FIVE);
		PlayingCard pc5 = new StandardPlayingCard(Suits.HEARTS,Ranks.SIX);
		PlayingCard pc6 = new StandardPlayingCard(Suits.CLUBS,Ranks.SIX);
		PlayingCard pc7 = new StandardPlayingCard(Suits.CLUBS,Ranks.SEVEN);
		PlayingCard pc8 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.SEVEN);
		PlayingCard pc9 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.EIGHT);
		PlayingCard pc10 = new StandardPlayingCard(Suits.CLUBS,Ranks.EIGHT);
		PlayingCard pc11 = new StandardPlayingCard(Suits.CLUBS,Ranks.NINE);
		PlayingCard pc12 = new StandardPlayingCard(Suits.HEARTS,Ranks.NINE);
		PlayingCard pc13 = new StandardPlayingCard(Suits.HEARTS,Ranks.TEN);
		
		Hand h = new Hand();
		
		h.addCard(pc1);
		h.addCard(pc2);
		h.addCard(pc3);
		h.addCard(pc4);
		h.addCard(pc5);
		h.addCard(pc6);
		h.addCard(pc7);
		h.addCard(pc8);
		h.addCard(pc9);
		h.addCard(pc10);
		h.addCard(pc11);
		h.addCard(pc12);
		h.addCard(pc13);
		
		assertEquals(1,h.getCardGroupCount());
		
		//the order: JOKERS, SPADES, HEARTS, DIAMONDS, CLUBS;
		h.sortIntoGroupsOnRequest();
		
		//HEARTS-4, DIAMONDS -3, CLUBS - 6
		assertEquals(3,h.getCardGroupCount());
		assertEquals(4,h.getCardGroupAtIndex(0).getCardCount());
		assertEquals(3,h.getCardGroupAtIndex(1).getCardCount());
		assertEquals(6,h.getCardGroupAtIndex(2).getCardCount());

		StandardPlayingCard pc = (StandardPlayingCard) h.getCardGroupAtIndex(0).getCard(0);
		
		assertEquals(Suits.HEARTS,pc.getSuit() );
		assertEquals(Ranks.TEN,pc.getRank() );
		
		h.moveCardInGroupOnRequest(0, 0, 1);

		assertEquals(3,h.getCardGroupCount());
		assertEquals(4,h.getCardGroupAtIndex(0).getCardCount());
		assertEquals(3,h.getCardGroupAtIndex(1).getCardCount());
		assertEquals(6,h.getCardGroupAtIndex(2).getCardCount());
		
		pc = (StandardPlayingCard) h.getCardGroupAtIndex(0).getCard(1);
		
		assertEquals(Suits.HEARTS,pc.getSuit() );
		assertEquals(Ranks.TEN,pc.getRank() );
		
		pc = (StandardPlayingCard) h.getCardGroupAtIndex(0).getCard(0);
		
		assertEquals(Suits.HEARTS,pc.getSuit() );
		assertEquals(Ranks.NINE,pc.getRank() );
		
		pc = (StandardPlayingCard) h.getCardGroupAtIndex(0).getCard(2);
		
		assertEquals(Suits.HEARTS,pc.getSuit() );
		assertEquals(Ranks.SIX,pc.getRank() );
		
	}

	@Test
	public void testAddCardGroupsUsingCardsArray() {

		PlayingCard pc1 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.ACE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.CLUBS,Ranks.ACE);
		PlayingCard pc3 = new StandardPlayingCard(Suits.CLUBS,Ranks.FIVE);
		PlayingCard pc4 = new StandardPlayingCard(Suits.HEARTS,Ranks.FIVE);

		Hand h = new Hand();

		ArrayList<PlayingCard> cards = new ArrayList<PlayingCard>();
		cards.add(pc1);
		cards.add(pc2);
		cards.add(pc3);
		cards.add(pc4);

		h.addCardGroupOfCards(cards);

		assertEquals(1,h.getCardGroupCount());

	}

	@Test
	public void testHandProfilingInitial() {

		PlayingCard pc1 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.ACE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.CLUBS,Ranks.ACE);
		PlayingCard pc3 = new StandardPlayingCard(Suits.CLUBS,Ranks.FIVE);
		PlayingCard pc4 = new StandardPlayingCard(Suits.HEARTS,Ranks.FIVE);
		PlayingCard pc5 = new StandardPlayingCard(Suits.HEARTS,Ranks.SIX);
		PlayingCard pc6 = new StandardPlayingCard(Suits.CLUBS,Ranks.SIX);
		PlayingCard pc7 = new StandardPlayingCard(Suits.CLUBS,Ranks.SEVEN);
		PlayingCard pc8 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.SEVEN);
		PlayingCard pc9 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.EIGHT);
		PlayingCard pc10 = new StandardPlayingCard(Suits.CLUBS,Ranks.EIGHT);
		PlayingCard pc11 = new StandardPlayingCard(Suits.CLUBS,Ranks.NINE);
		PlayingCard pc12 = new StandardPlayingCard(Suits.HEARTS,Ranks.NINE);
		PlayingCard pc13 = new StandardPlayingCard(Suits.HEARTS,Ranks.TEN);

		Hand h = new Hand();

		h.addCard(pc1);
		h.addCard(pc2);
		h.addCard(pc3);
		h.addCard(pc4);
		h.addCard(pc5);
		h.addCard(pc6);
		h.addCard(pc7);
		h.addCard(pc8);
		h.addCard(pc9);
		h.addCard(pc10);
		h.addCard(pc11);
		h.addCard(pc12);
		h.addCard(pc13);
		
		assertEquals(1,h.getCardGroupCount());
		
		h.profile();
		
		assertEquals(1,h.getCardGroupCount());
		
		assertEquals(CardGroupType.NONE,h.getCardGroupAtIndex(0).getType());
		
		h.sortIntoGroupsOnRequest();
		assertEquals(3,h.getCardGroupCount());

		//TODO profile and get points
		//h.profile(); 
	}

	@Test
	public void testHandnitialGetPointsPostProfiling() {

		PlayingCard pc1 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.ACE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.CLUBS,Ranks.ACE);
		PlayingCard pc3 = new StandardPlayingCard(Suits.CLUBS,Ranks.FIVE);
		PlayingCard pc4 = new StandardPlayingCard(Suits.HEARTS,Ranks.FIVE);

		Hand h = new Hand();

		h.addCard(pc1);
		h.addCard(pc2);
		h.addCard(pc3);
		h.addCard(pc4);
		
		assertEquals(1,h.getCardGroupCount());
		
		h.profile();
		
		assertEquals(1,h.getCardGroupCount());
		assertEquals(CardGroupType.NONE,h.getCardGroupAtIndex(0).getType());
		assertEquals(80,h.getPoints());
		//assertEquals(CardGroupType.NONE,h.getCardGroupAtIndex(0).getType());

	}
	
	@Test
	public void testHandProfilingTwoPureSequenceTwoPureSetsNoJokersDefined() {

		PlayingCard pc1 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.ACE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.QUEEN);
		PlayingCard pc3 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.KING);
		PlayingCard pc4 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.JACK);
		CardGroup cardGroup1 = new CardGroup();
		cardGroup1.addCard(pc1);
		cardGroup1.addCard(pc2);
		cardGroup1.addCard(pc3);
		cardGroup1.addCard(pc4);

		
		PlayingCard pc5 = new StandardPlayingCard(Suits.HEARTS,Ranks.SIX);
		PlayingCard pc6 = new StandardPlayingCard(Suits.HEARTS,Ranks.EIGHT);
		PlayingCard pc7 = new StandardPlayingCard(Suits.HEARTS,Ranks.SEVEN);
		CardGroup cardGroup2 = new CardGroup();
		cardGroup2.addCard(pc5);
		cardGroup2.addCard(pc6);
		cardGroup2.addCard(pc7);

		PlayingCard pc8 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.SEVEN);
		PlayingCard pc9 = new StandardPlayingCard(Suits.HEARTS,Ranks.SEVEN);
		PlayingCard pc10 = new StandardPlayingCard(Suits.SPADES,Ranks.SEVEN);
		CardGroup cardGroup3 = new CardGroup();
		cardGroup3.addCard(pc8);
		cardGroup3.addCard(pc9);
		cardGroup3.addCard(pc10);
		
		PlayingCard pc11 = new StandardPlayingCard(Suits.CLUBS,Ranks.NINE);
		PlayingCard pc12 = new StandardPlayingCard(Suits.HEARTS,Ranks.NINE);
		PlayingCard pc13 = new StandardPlayingCard(Suits.SPADES,Ranks.NINE);
		CardGroup cardGroup4 = new CardGroup();
		cardGroup4.addCard(pc11);
		cardGroup4.addCard(pc12);
		cardGroup4.addCard(pc13);

		Hand h = new Hand();

		h.addCardGroupOfCards(cardGroup1.getCards());
		h.addCardGroupOfCards(cardGroup2.getCards());
		h.addCardGroupOfCards(cardGroup3.getCards());
		h.addCardGroupOfCards(cardGroup4.getCards());

		assertEquals(4,h.getCardGroupCount());
		assertEquals(CardGroupType.NONE,h.getCardGroupAtIndex(0).getType());
		assertEquals(CardGroupType.NONE,h.getCardGroupAtIndex(0).getType());
		assertEquals(CardGroupType.NONE,h.getCardGroupAtIndex(0).getType());
		assertEquals(CardGroupType.NONE,h.getCardGroupAtIndex(0).getType());
		
		h.profile();
		
		assertEquals(4,h.getCardGroupCount());
		assertEquals(CardGroupType.PURE_RUN,h.getCardGroupAtIndex(0).getType());
		assertEquals(CardGroupType.PURE_RUN,h.getCardGroupAtIndex(1).getType());
		assertEquals(CardGroupType.PURE_SET,h.getCardGroupAtIndex(2).getType());
		assertEquals(CardGroupType.PURE_SET,h.getCardGroupAtIndex(3).getType());

	}

	@Test
	public void testHandProfilingTwoPureSequenceTwoPureSetsJokersNotInHand() {

		PlayingCard pc1 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.ACE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.QUEEN);
		PlayingCard pc3 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.KING);
		PlayingCard pc4 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.JACK);
		CardGroup cardGroup1 = new CardGroup();
		cardGroup1.addCard(pc1);
		cardGroup1.addCard(pc2);
		cardGroup1.addCard(pc3);
		cardGroup1.addCard(pc4);

		
		PlayingCard pc5 = new StandardPlayingCard(Suits.HEARTS,Ranks.SIX);
		PlayingCard pc6 = new StandardPlayingCard(Suits.HEARTS,Ranks.EIGHT);
		PlayingCard pc7 = new StandardPlayingCard(Suits.HEARTS,Ranks.SEVEN);
		CardGroup cardGroup2 = new CardGroup();
		cardGroup2.addCard(pc5);
		cardGroup2.addCard(pc6);
		cardGroup2.addCard(pc7);

		PlayingCard pc8 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.SEVEN);
		PlayingCard pc9 = new StandardPlayingCard(Suits.HEARTS,Ranks.SEVEN);
		PlayingCard pc10 = new StandardPlayingCard(Suits.SPADES,Ranks.SEVEN);
		CardGroup cardGroup3 = new CardGroup();
		cardGroup3.addCard(pc8);
		cardGroup3.addCard(pc9);
		cardGroup3.addCard(pc10);
		
		PlayingCard pc11 = new StandardPlayingCard(Suits.CLUBS,Ranks.NINE);
		PlayingCard pc12 = new StandardPlayingCard(Suits.HEARTS,Ranks.NINE);
		PlayingCard pc13 = new StandardPlayingCard(Suits.SPADES,Ranks.NINE);
		CardGroup cardGroup4 = new CardGroup();
		cardGroup4.addCard(pc11);
		cardGroup4.addCard(pc12);
		cardGroup4.addCard(pc13);

		ArrayList<PlayingCard> allsupportedjokers = new ArrayList<PlayingCard>();
		PlayingCard j1 = new StandardPlayingCard(Suits.CLUBS,Ranks.FIVE);
		allsupportedjokers.add(j1);
		
		Hand h = new Hand(allsupportedjokers);

		h.addCardGroupOfCards(cardGroup1.getCards());
		h.addCardGroupOfCards(cardGroup2.getCards());
		h.addCardGroupOfCards(cardGroup3.getCards());
		h.addCardGroupOfCards(cardGroup4.getCards());

		assertEquals(4,h.getCardGroupCount());
		assertEquals(CardGroupType.NONE,h.getCardGroupAtIndex(0).getType());
		assertEquals(CardGroupType.NONE,h.getCardGroupAtIndex(0).getType());
		assertEquals(CardGroupType.NONE,h.getCardGroupAtIndex(0).getType());
		assertEquals(CardGroupType.NONE,h.getCardGroupAtIndex(0).getType());
		
		h.profile();
		
		assertEquals(4,h.getCardGroupCount());
		assertEquals(CardGroupType.PURE_RUN,h.getCardGroupAtIndex(0).getType());
		assertEquals(CardGroupType.PURE_RUN,h.getCardGroupAtIndex(1).getType());
		assertEquals(CardGroupType.PURE_SET,h.getCardGroupAtIndex(2).getType());
		assertEquals(CardGroupType.PURE_SET,h.getCardGroupAtIndex(3).getType());

	}

	@Test
	public void testHandProfilingTwoPureSequenceTwoPureSetsOneofthecardsisAJoker() {

		PlayingCard pc1 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.ACE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.QUEEN);
		PlayingCard pc3 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.KING);
		PlayingCard pc4 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.JACK);
		CardGroup cardGroup1 = new CardGroup();
		cardGroup1.addCard(pc1);
		cardGroup1.addCard(pc2);
		cardGroup1.addCard(pc3);
		cardGroup1.addCard(pc4);

		
		PlayingCard pc5 = new StandardPlayingCard(Suits.HEARTS,Ranks.SIX);
		PlayingCard pc6 = new StandardPlayingCard(Suits.HEARTS,Ranks.EIGHT);
		PlayingCard pc7 = new StandardPlayingCard(Suits.HEARTS,Ranks.SEVEN);
		CardGroup cardGroup2 = new CardGroup();
		cardGroup2.addCard(pc5);
		cardGroup2.addCard(pc6);
		cardGroup2.addCard(pc7);

		PlayingCard pc8 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.SEVEN);
		PlayingCard pc9 = new StandardPlayingCard(Suits.HEARTS,Ranks.SEVEN);
		PlayingCard pc10 = new StandardPlayingCard(Suits.SPADES,Ranks.SEVEN);
		CardGroup cardGroup3 = new CardGroup();
		cardGroup3.addCard(pc8);
		cardGroup3.addCard(pc9);
		cardGroup3.addCard(pc10);
		
		PlayingCard pc11 = new StandardPlayingCard(Suits.CLUBS,Ranks.NINE);
		PlayingCard pc12 = new StandardPlayingCard(Suits.HEARTS,Ranks.NINE);
		PlayingCard pc13 = new StandardPlayingCard(Suits.SPADES,Ranks.NINE);
		CardGroup cardGroup4 = new CardGroup();
		cardGroup4.addCard(pc11);
		cardGroup4.addCard(pc12);
		cardGroup4.addCard(pc13);

		ArrayList<PlayingCard> allsupportedjokers = new ArrayList<PlayingCard>();
		PlayingCard j1 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.ACE);
		allsupportedjokers.add(j1);
		
		Hand h = new Hand(allsupportedjokers);

		h.addCardGroupOfCards(cardGroup1.getCards());
		h.addCardGroupOfCards(cardGroup2.getCards());
		h.addCardGroupOfCards(cardGroup3.getCards());
		h.addCardGroupOfCards(cardGroup4.getCards());

		assertEquals(4,h.getCardGroupCount());
		assertEquals(CardGroupType.NONE,h.getCardGroupAtIndex(0).getType());
		assertEquals(CardGroupType.NONE,h.getCardGroupAtIndex(0).getType());
		assertEquals(CardGroupType.NONE,h.getCardGroupAtIndex(0).getType());
		assertEquals(CardGroupType.NONE,h.getCardGroupAtIndex(0).getType());
		
		h.profile();
		
		assertEquals(4,h.getCardGroupCount());
		assertEquals(CardGroupType.PURE_RUN,h.getCardGroupAtIndex(0).getType());
		assertEquals(CardGroupType.PURE_RUN,h.getCardGroupAtIndex(1).getType());
		assertEquals(CardGroupType.PURE_SET,h.getCardGroupAtIndex(2).getType());
		assertEquals(CardGroupType.PURE_SET,h.getCardGroupAtIndex(3).getType());

	}
	@Test
	public void testHandProfilingOnePureOneValidSequenceOnePureOneValidSetAndTwoJokerAmongThem() {

		PlayingCard pc1 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.ACE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.ACE);
		PlayingCard pc3 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.KING);
		PlayingCard pc4 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.JACK);
		CardGroup cardGroup1 = new CardGroup();
		cardGroup1.addCard(pc1);
		cardGroup1.addCard(pc2);
		cardGroup1.addCard(pc3);
		cardGroup1.addCard(pc4);

		
		PlayingCard pc5 = new StandardPlayingCard(Suits.HEARTS,Ranks.SIX);
		PlayingCard pc6 = new StandardPlayingCard(Suits.HEARTS,Ranks.EIGHT);
		PlayingCard pc7 = new StandardPlayingCard(Suits.HEARTS,Ranks.SEVEN);
		CardGroup cardGroup2 = new CardGroup();
		cardGroup2.addCard(pc5);
		cardGroup2.addCard(pc6);
		cardGroup2.addCard(pc7);

		PlayingCard pc8 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.SEVEN);
		PlayingCard pc9 = new StandardPlayingCard(Suits.HEARTS,Ranks.SEVEN);
		PlayingCard pc10 = new StandardPlayingCard(Suits.SPADES,Ranks.SEVEN);
		CardGroup cardGroup3 = new CardGroup();
		cardGroup3.addCard(pc8);
		cardGroup3.addCard(pc9);
		cardGroup3.addCard(pc10);
		
		PlayingCard pc11 = new StandardPlayingCard(Suits.CLUBS,Ranks.NINE);
		PlayingCard pc12 = new StandardPlayingCard(Suits.HEARTS,Ranks.NINE);
		PlayingCard pc13 = new JokerPlayingCard();
		CardGroup cardGroup4 = new CardGroup();
		cardGroup4.addCard(pc11);
		cardGroup4.addCard(pc12);
		cardGroup4.addCard(pc13);

		ArrayList<PlayingCard> allsupportedjokers = new ArrayList<PlayingCard>();
		PlayingCard j1 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.ACE);
		PlayingCard j2 = new JokerPlayingCard();
		allsupportedjokers.add(j1);
		allsupportedjokers.add(j2);
		
		Hand h = new Hand(allsupportedjokers);

		h.addCardGroupOfCards(cardGroup1.getCards());
		h.addCardGroupOfCards(cardGroup2.getCards());
		h.addCardGroupOfCards(cardGroup3.getCards());
		h.addCardGroupOfCards(cardGroup4.getCards());

		assertEquals(4,h.getCardGroupCount());
		assertEquals(CardGroupType.NONE,h.getCardGroupAtIndex(0).getType());
		assertEquals(CardGroupType.NONE,h.getCardGroupAtIndex(0).getType());
		assertEquals(CardGroupType.NONE,h.getCardGroupAtIndex(0).getType());
		assertEquals(CardGroupType.NONE,h.getCardGroupAtIndex(0).getType());
		
		h.profile();
		
		assertEquals(4,h.getCardGroupCount());
		assertEquals(CardGroupType.RUN,h.getCardGroupAtIndex(0).getType());
		assertEquals(CardGroupType.PURE_RUN,h.getCardGroupAtIndex(1).getType());
		assertEquals(CardGroupType.PURE_SET,h.getCardGroupAtIndex(2).getType());
		assertEquals(CardGroupType.SET,h.getCardGroupAtIndex(3).getType());
	}
}
