package com.RummyTriangle.domain;

import org.junit.Test;





import com.RummyTriangle.domain.StandardDeck;

import static org.junit.Assert.*;

public class DeckTest {

	    @Test
	    public void testdrawRandomCards() {
	    	StandardDeck deck = new StandardDeck();

	    	deck.drawRandomCards(5);
	    	
	        int stdDeckPlayingCardsCount = deck.getPlayingCards().size();

	        assertEquals(47, stdDeckPlayingCardsCount);

	    }

	    @Test
	    public void testPickRandomIndex() {

	    	StandardDeck deck = new StandardDeck();
	    	
	    	assertFalse(deck.pickRandomIndex()== deck.getCardCount());
	    	assertEquals(deck.pickRandomIndex()>= 0,true);
	    	assertEquals(deck.pickRandomIndex()< deck.getCardCount(),true);


	    }
	    
	    @Test
	    public void testMatchRandomBehavior() {

	    	int cardCount = 50;
	    	
	    	assertEquals(1 + (int)(Math.random() * ((cardCount - 1) + 1))< 50,true);


	    }
	    
	    
	    
	    //1 + (int)(Math.random() * ((cardCount - 1) + 1))
}
