package com.RummyTriangle.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import com.RummyTriangle.domain.DiscardSet;
import com.RummyTriangle.domain.Ranks;
import com.RummyTriangle.domain.StandardPlayingCard;
import com.RummyTriangle.domain.Suits;

public class DiscardSetTest {

	@Test
	public void testAddCard() {
		
		DiscardSet ds = new DiscardSet();
		
		
		StandardPlayingCard pc = new StandardPlayingCard();
	
		pc.setSuit(Suits.SPADES);
		pc.setRank(Ranks.ACE);
		
		ds.addCard(pc);

		pc.setSuit(Suits.SPADES);
		pc.setRank(Ranks.TWO);
		
		ds.addCard(pc);

		assertEquals(2, ds.getCardCount());
	}

	@Test
	public void testGetOpenCard() {
		
		DiscardSet ds = new DiscardSet();
		
		
		StandardPlayingCard pc = new StandardPlayingCard();
	
		pc.setSuit(Suits.SPADES);
		pc.setRank(Ranks.ACE);
		
		ds.addCard(pc);
		
		pc.setSuit(Suits.SPADES);
		pc.setRank(Ranks.TWO);
		
		ds.addCard(pc);
		
		pc.setSuit(Suits.SPADES);
		pc.setRank(Ranks.THREE);
		
		ds.addCard(pc);
		
		assertEquals(pc, ds.getOpenCard() );
	}

	@Test
	public void testPullOpenCard() {
		
		DiscardSet ds = new DiscardSet();
		
		
		StandardPlayingCard pc = new StandardPlayingCard();
	
		pc.setSuit(Suits.SPADES);
		pc.setRank(Ranks.ACE);
		
		ds.addCard(pc);
		
		StandardPlayingCard pc1 = new StandardPlayingCard();
		
		pc1.setSuit(Suits.SPADES);
		pc1.setRank(Ranks.TWO);
		
		ds.addCard(pc1);
		
		ds.pullOpenCard();
		
		assertEquals(pc, ds.getOpenCard() );
		
		StandardPlayingCard pc2 = new StandardPlayingCard();
	    
		pc2 = (StandardPlayingCard) ds.getOpenCard();
	    //System.out.println(pc2.getSuit() + " " + pc2.getRank());
		
		
		
		assertEquals(1, ds.getCardCount() );
		
	}

}
