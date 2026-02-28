package com.RummyTriangle.domain;

import org.junit.Test;

import com.RummyTriangle.domain.StandardDeck;

import static org.junit.Assert.*;

public class StandardDeckTest {

	    @Test
	    public void testStandardDeckPlayingCardsCount() {
	    	StandardDeck stdDeck = new StandardDeck();

	        int stdDeckPlayingCardsCount = stdDeck.getPlayingCards().size();

	        assertEquals(52, stdDeckPlayingCardsCount);

	    }
}
