package com.RummyTriangle.domain;

import org.junit.Test;

import com.RummyTriangle.domain.RummySingleDeck;

import static org.junit.Assert.*;

public class RummySingleDeckTest {

	    @Test
	    public void testRummySingleDeckPlayingCardsCount() {
	    	RummySingleDeck rummySingleDeck = new RummySingleDeck(2);

	        int rummySingleDeckPlayingCardsCount = rummySingleDeck.getPlayingCards().size();

	        assertEquals(54, rummySingleDeckPlayingCardsCount);

	    }
}
