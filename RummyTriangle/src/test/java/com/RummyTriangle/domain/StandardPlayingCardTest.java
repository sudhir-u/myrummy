package com.RummyTriangle.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

import com.RummyTriangle.domain.Ranks;
import com.RummyTriangle.domain.StandardPlayingCard;
import com.RummyTriangle.domain.Suits;

public class StandardPlayingCardTest {

	@Test
	public void testSortingSameSuits() {
		
		StandardPlayingCard spc = new StandardPlayingCard (Suits.CLUBS, Ranks.QUEEN);
		
		StandardPlayingCard spc1 = new StandardPlayingCard (Suits.CLUBS, Ranks.TWO);

		StandardPlayingCard spc2 = new StandardPlayingCard ();
		spc2.setRank(Ranks.ACE);
		spc2.setSuit(Suits.CLUBS);

		StandardPlayingCard spc3 = new StandardPlayingCard ();
		spc3.setRank(Ranks.KING);
		spc3.setSuit(Suits.CLUBS);
		
		ArrayList<StandardPlayingCard> cards = new  ArrayList<StandardPlayingCard>();
		
		cards.add(spc);
		cards.add(spc1);
		cards.add(spc2);
		cards.add(spc3);
		
		assertEquals(Ranks.QUEEN, cards.get(0).getRank());
		assertEquals(Suits.CLUBS, cards.get(0).getSuit());

		Collections.sort(cards);

		assertEquals(Ranks.TWO, cards.get(0).getRank());
		assertEquals(Ranks.QUEEN, cards.get(1).getRank());
		assertEquals(Ranks.KING, cards.get(2).getRank());
		assertEquals(Ranks.ACE, cards.get(3).getRank());
	}

	@Test
	public void testSortingSameRanksDifferentSuits() {
		
		StandardPlayingCard spc = new StandardPlayingCard (Suits.CLUBS, Ranks.TWO);
		
		StandardPlayingCard spc1 = new StandardPlayingCard (Suits.SPADES, Ranks.TWO);

		StandardPlayingCard spc2 = new StandardPlayingCard ();
		spc2.setRank(Ranks.TWO);
		spc2.setSuit(Suits.DIAMONDS);

		ArrayList<StandardPlayingCard> cards = new  ArrayList<StandardPlayingCard>();
		
		cards.add(spc);
		cards.add(spc1);
		cards.add(spc2);
		
		Collections.sort(cards);

		assertEquals(Suits.CLUBS, cards.get(0).getSuit());
		assertEquals(Suits.DIAMONDS, cards.get(1).getSuit());
		assertEquals(Suits.SPADES, cards.get(2).getSuit());

	}

	@Test
	public void testSortingDifferentRanksDifferentSuits() {
		
		
		
		StandardPlayingCard spc = new StandardPlayingCard (Suits.SPADES, Ranks.KING);
		
		StandardPlayingCard spc1 = new StandardPlayingCard (Suits.SPADES, Ranks.ACE);

		StandardPlayingCard spc2 = new StandardPlayingCard ();
		spc2.setRank(Ranks.QUEEN);
		spc2.setSuit(Suits.SPADES);

		StandardPlayingCard spc3 = new StandardPlayingCard (Suits.CLUBS, Ranks.ACE);
		
		ArrayList<StandardPlayingCard> cards = new  ArrayList<StandardPlayingCard>();
		
		cards.add(spc);
		cards.add(spc1);
		cards.add(spc2);
		cards.add(spc3);
		
		Collections.sort(cards);

		assertEquals(Suits.CLUBS, cards.get(0).getSuit());
		assertEquals(Ranks.ACE, cards.get(0).getRank());
		
		assertEquals(Suits.SPADES, cards.get(1).getSuit());
		assertEquals(Ranks.QUEEN, cards.get(1).getRank());

		assertEquals(Suits.SPADES, cards.get(2).getSuit());
		assertEquals(Ranks.KING, cards.get(2).getRank());

		assertEquals(Suits.SPADES, cards.get(3).getSuit());
		assertEquals(Ranks.ACE, cards.get(3).getRank());
	}

	@Test
	public void testIsJokerInContextWithNonAceStandardCardAsJoker() {

		StandardPlayingCard spc = new StandardPlayingCard (Suits.SPADES, Ranks.KING);
		
		StandardPlayingCard jokerCard; 
		
		jokerCard = new StandardPlayingCard (Suits.CLUBS, Ranks.TWO);
		assertEquals(spc.isJokerInContext(jokerCard),false);
		
		jokerCard = new StandardPlayingCard (Suits.SPADES, Ranks.KING);
		assertEquals(spc.isJokerInContext(jokerCard),true);

		jokerCard = new StandardPlayingCard (Suits.CLUBS, Ranks.KING);
		assertEquals(spc.isJokerInContext(jokerCard),true);

	}


	@Test
	public void testIsJokerInContextWithAceCardAsJoker() {

		StandardPlayingCard cardAsJoker = new StandardPlayingCard (Suits.CLUBS, Ranks.ACE);
		
		StandardPlayingCard theCard; 
		
		theCard = new StandardPlayingCard (Suits.SPADES, Ranks.ACE);
		assertEquals(theCard.isJokerInContext(cardAsJoker),true);

		theCard = new StandardPlayingCard (Suits.SPADES, Ranks.TWO);
		assertEquals(theCard.isJokerInContext(cardAsJoker),false);


	}


	@Test
	public void testIsJokerInContextWithJokerCardAsJoker() {

		PlayingCard cardAsJoker = new JokerPlayingCard() ;
		
		StandardPlayingCard theCard; 
		
		theCard = new StandardPlayingCard (Suits.SPADES, Ranks.ACE);
		assertEquals(theCard.isJokerInContext(cardAsJoker),true);

		theCard = new StandardPlayingCard (Suits.SPADES, Ranks.TWO);
		assertEquals(theCard.isJokerInContext(cardAsJoker),false);


	}
	
	@Test
	public void testIsContainedIn() {


		StandardPlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.FIVE);
		StandardPlayingCard pc2 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		JokerPlayingCard jpc = new JokerPlayingCard();

		ArrayList<PlayingCard> jokersInGame = new ArrayList<PlayingCard>();

		StandardPlayingCard j1 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		StandardPlayingCard j2 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.ACE);
		JokerPlayingCard j3 = new JokerPlayingCard();
		jokersInGame.add(j1);
		jokersInGame.add(j2);
		jokersInGame.add(j3);


		assertFalse(pc1.isContainedIn(jokersInGame));
		assertTrue(pc2.isContainedIn(jokersInGame));
		assertTrue(jpc.isContainedIn(jokersInGame));

	}

}
