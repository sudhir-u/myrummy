package com.RummyTriangle.service;

import static org.junit.Assert.*;

import org.junit.Test;

import com.RummyTriangle.domain.CardGroup;
import com.RummyTriangle.domain.PlayingCard;
import com.RummyTriangle.domain.Ranks;
import com.RummyTriangle.domain.StandardPlayingCard;
import com.RummyTriangle.domain.Suits;

public class HandAdditionalTest {

	@Test
	public void testDiscardCardRemovesFromGroup() {
		Hand hand = new Hand();
		hand.addCard(new StandardPlayingCard(Suits.CLUBS, Ranks.ACE));
		hand.addCard(new StandardPlayingCard(Suits.CLUBS, Ranks.TWO));
		hand.addCard(new StandardPlayingCard(Suits.CLUBS, Ranks.THREE));

		assertEquals(3, hand.getCardCount());

		PlayingCard discarded = hand.discardCard(0, 1);
		assertNotNull(discarded);
		assertEquals(Ranks.TWO, ((StandardPlayingCard) discarded).getRank());
		assertEquals(2, hand.getCardCount());
	}

	@Test
	public void testDiscardCardRemovesEmptyGroup() {
		Hand hand = new Hand();
		hand.addCard(new StandardPlayingCard(Suits.CLUBS, Ranks.ACE));

		assertEquals(1, hand.getCardCount());
		hand.discardCard(0, 0);
		assertEquals(0, hand.getCardCount());
		assertEquals(0, hand.getCardGroupCount());
	}

	@Test
	public void testCreateNewGroupOnRequest() {
		Hand hand = new Hand();
		hand.addCard(new StandardPlayingCard(Suits.CLUBS, Ranks.ACE));

		assertEquals(1, hand.getCardGroupCount());
		hand.createNewGroupOnRequest();
		assertEquals(2, hand.getCardGroupCount());
		hand.createNewGroupOnRequest();
		assertEquals(3, hand.getCardGroupCount());
	}

}
