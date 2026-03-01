package com.RummyTriangle.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.RummyTriangle.domain.CardGroup;
import com.RummyTriangle.domain.JokerPlayingCard;
import com.RummyTriangle.domain.PlayingCard;
import com.RummyTriangle.domain.Ranks;
import com.RummyTriangle.domain.StandardPlayingCard;
import com.RummyTriangle.domain.Suits;
import com.RummyTriangle.service.Hand;

public class CardGroupTest {

	@Test
	public void test() {

		CardGroup cardGroup = new CardGroup();
		
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.ACE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		
		assertEquals(false,cardGroup.hasMinimumSetCount());
		
		PlayingCard pc3 = new StandardPlayingCard(Suits.CLUBS,Ranks.KING);
		cardGroup.addCard(pc3);
		
		assertEquals(true,cardGroup.hasMinimumSetCount());
		
		assertEquals(true,cardGroup.hasSameSuit(cardGroup.getCards()));
		
		PlayingCard pc4 = new StandardPlayingCard(Suits.CLUBS,Ranks.THREE);
		PlayingCard pc5 = new StandardPlayingCard(Suits.SPADES,Ranks.FOUR);
		PlayingCard pc6 = new StandardPlayingCard(Suits.CLUBS,Ranks.FIVE);

		cardGroup.addCard(pc4);
		cardGroup.addCard(pc5);
		cardGroup.addCard(pc6);

		assertEquals(false,cardGroup.hasSameSuit(cardGroup.getCards()));
		
		assertEquals(true,cardGroup.hasAllStandardCards());
		
		assertEquals(false,cardGroup.isPureSequence());
	}

	@Test
	public void testTwoCardsDoNotMakeASet() {
		CardGroup cardGroup = new CardGroup();
		
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.ACE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		
		assertFalse(cardGroup.hasMinimumSetCount());

	}		

	@Test
	public void testMoreThanTwoCardsWithSameSuitMakeASet() {
		CardGroup cardGroup = new CardGroup();
		
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.ACE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		PlayingCard pc3 = new StandardPlayingCard(Suits.CLUBS,Ranks.KING);
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		cardGroup.addCard(pc3);
		assertEquals(cardGroup.hasMinimumSetCount(),true);

	}		

	@Test
	public void testMoreThanTwoCardsWithDifferentSuitMakeASet() {
		CardGroup cardGroup = new CardGroup();
		
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.ACE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		PlayingCard pc3 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.KING);
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		cardGroup.addCard(pc3);
		assertEquals(cardGroup.hasMinimumSetCount(),true);

	}		

	@Test
	public void testCardsWithDifferentSuitDoNotQualifyAsSetWithSameSuit() {
		CardGroup cardGroup = new CardGroup();
		
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.ACE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		PlayingCard pc3 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.KING);
		//PlayingCard pc4 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.QUEEN);
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		cardGroup.addCard(pc3);
		//cardGroup.addCard(pc4);
		assertEquals(true,cardGroup.hasMinimumSetCount());
		assertFalse(cardGroup.hasSameSuit(cardGroup.getCards()));

	}		

	@Test
	public void testCardsWithJokerCardDoNotQualifyAsSetWithSameSuit() {
		CardGroup cardGroup = new CardGroup();
		
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.ACE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		PlayingCard pc3 = new JokerPlayingCard();
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		cardGroup.addCard(pc3);
		assertEquals(true,cardGroup.hasMinimumSetCount());
		assertFalse(cardGroup.hasSameSuit(cardGroup.getCards()));

	}		

	@Test
	public void testCardsWithSameSuitQualifyAsSetWithSameSuit() {
		CardGroup cardGroup = new CardGroup();
		
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.ACE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		PlayingCard pc3 = new StandardPlayingCard(Suits.CLUBS,Ranks.FIVE);
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		cardGroup.addCard(pc3);
		assertEquals(true,cardGroup.hasMinimumSetCount());
		assertEquals(cardGroup.hasSameSuit(cardGroup.getCards()),true);

	}		

	@Test
	public void testCardsWithSameSuitAceTwoThreeIsPureSequence() {
		CardGroup cardGroup = new CardGroup();
		
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.ACE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		PlayingCard pc3 = new StandardPlayingCard(Suits.CLUBS,Ranks.THREE);
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		cardGroup.addCard(pc3);
		assertEquals(cardGroup.isPureSequence() ,true);

	}		

	@Test
	public void testCardsWithSameSuitAceTwoButThirdCardFarInsideIsNotPureSequence() {
		CardGroup cardGroup = new CardGroup();
		
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.ACE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		PlayingCard pc3 = new StandardPlayingCard(Suits.CLUBS,Ranks.SEVEN);
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		cardGroup.addCard(pc3);
		assertEquals(cardGroup.isPureSequence() ,false);

	}		

	@Test
	public void testCardsWithSameSuitAceTwoButRestCardsFarInsideIsNotPureSequence() {
		CardGroup cardGroup = new CardGroup();
		
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.ACE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		PlayingCard pc3 = new StandardPlayingCard(Suits.CLUBS,Ranks.SEVEN);
		PlayingCard pc4 = new StandardPlayingCard(Suits.CLUBS,Ranks.EIGHT);
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		cardGroup.addCard(pc3);
		cardGroup.addCard(pc4);
		assertEquals(cardGroup.isPureSequence() ,false);

	}		

	@Test
	public void testCardsWithSameSuitTwoThreeFourIsPureSequence() {
		CardGroup cardGroup = new CardGroup();
		
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.FOUR);
		PlayingCard pc2 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		PlayingCard pc3 = new StandardPlayingCard(Suits.CLUBS,Ranks.THREE);
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		cardGroup.addCard(pc3);
		assertEquals(cardGroup.isPureSequence() ,true);

	}		

	@Test
	public void testCardsWithSameSuitKingQueenAceIsPureSequence() {
		CardGroup cardGroup = new CardGroup();
		
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.ACE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.CLUBS,Ranks.QUEEN);
		PlayingCard pc3 = new StandardPlayingCard(Suits.CLUBS,Ranks.KING);
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		cardGroup.addCard(pc3);
		assertEquals(cardGroup.isPureSequence() ,true);

	}		

	@Test
	public void testCardsWithSameSuitJackKingQueenIsPureSequence() {
		CardGroup cardGroup = new CardGroup();
		
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.JACK);
		PlayingCard pc2 = new StandardPlayingCard(Suits.CLUBS,Ranks.KING);
		PlayingCard pc3 = new StandardPlayingCard(Suits.CLUBS,Ranks.QUEEN);
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		cardGroup.addCard(pc3);
		assertEquals(cardGroup.isPureSequence() ,true);

	}		

	@Test
	public void testCardsWithSameSuitAceTwoKingIsNotPureSequence() {
		CardGroup cardGroup = new CardGroup();
		
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.ACE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		PlayingCard pc3 = new StandardPlayingCard(Suits.CLUBS,Ranks.KING);
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		cardGroup.addCard(pc3);
		assertEquals(cardGroup.isPureSequence() ,false);

	}		

	@Test
	public void testCardsWithSameSuitTwoThreeFiveIsNotPureSequence() {
		CardGroup cardGroup = new CardGroup();
		
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.FIVE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		PlayingCard pc3 = new StandardPlayingCard(Suits.CLUBS,Ranks.THREE);
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		cardGroup.addCard(pc3);
		assertEquals(true,cardGroup.hasMinimumSetCount());
		assertEquals(cardGroup.isPureSequence() ,false);

	}		

	@Test
	public void testCardsWithSameSuitFourCardsIncludingAceIsPureSequence() {
		CardGroup cardGroup = new CardGroup();
		
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.ACE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.CLUBS,Ranks.THREE);
		PlayingCard pc3 = new StandardPlayingCard(Suits.CLUBS,Ranks.FOUR);
		PlayingCard pc4 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		cardGroup.addCard(pc3);
		cardGroup.addCard(pc4);
		assertEquals(cardGroup.isPureSequence() ,true);

	}		

	@Test
	public void testCardsWithSameSuitFourCardsExcludingAceIsPureSequence() {
		CardGroup cardGroup = new CardGroup();
		
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.FIVE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		PlayingCard pc3 = new StandardPlayingCard(Suits.CLUBS,Ranks.THREE);
		PlayingCard pc4 = new StandardPlayingCard(Suits.CLUBS,Ranks.FOUR);
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		cardGroup.addCard(pc3);
		cardGroup.addCard(pc4);
		assertEquals(cardGroup.isPureSequence() ,true);

	}		

	@Test
	public void testCardsWithSameSuitSixCardsIsPureSequence() {
		CardGroup cardGroup = new CardGroup();
		
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.FIVE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		PlayingCard pc3 = new StandardPlayingCard(Suits.CLUBS,Ranks.THREE);
		PlayingCard pc4 = new StandardPlayingCard(Suits.CLUBS,Ranks.FOUR);
		PlayingCard pc5 = new StandardPlayingCard(Suits.CLUBS,Ranks.SIX);
		PlayingCard pc6 = new StandardPlayingCard(Suits.CLUBS,Ranks.SEVEN);
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		cardGroup.addCard(pc3);
		cardGroup.addCard(pc4);
		cardGroup.addCard(pc5);
		cardGroup.addCard(pc6);
		assertEquals(cardGroup.isPureSequence() ,true);

	}		

	@Test
	public void testCardsWithSameSuitAceAceTwoThreeIsNotPureSequence() {
		CardGroup cardGroup = new CardGroup();
		
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.ACE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		PlayingCard pc3 = new StandardPlayingCard(Suits.CLUBS,Ranks.THREE);
		PlayingCard pc4 = new StandardPlayingCard(Suits.CLUBS,Ranks.ACE);
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		cardGroup.addCard(pc3);
		cardGroup.addCard(pc4);
		assertEquals(cardGroup.isPureSequence() ,false);

	}		

	@Test
	public void testIsNotValidSequenceHasLessThanThreeCards() {
		CardGroup cardGroup = new CardGroup();
				
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.FIVE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		
		ArrayList<PlayingCard> jokers = new ArrayList<PlayingCard>();

		PlayingCard j1 = new StandardPlayingCard(Suits.CLUBS,Ranks.FIVE);
		jokers.add(j1);
		
		assertEquals(false, cardGroup.isValidSequence(jokers));
		assertEquals(2,cardGroup.getPoints(jokers));

	}		

	@Test
	public void testIsValidSequenceSetIsASequenceWithoutJoker() {
		CardGroup cardGroup = new CardGroup();
				
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.FOUR);
		PlayingCard pc2 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		PlayingCard pc3 = new StandardPlayingCard(Suits.CLUBS,Ranks.THREE);
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		cardGroup.addCard(pc3);
		
		ArrayList<PlayingCard> jokers = new ArrayList<PlayingCard>();

		PlayingCard j1 = new StandardPlayingCard(Suits.CLUBS,Ranks.FIVE);
		jokers.add(j1);
		
		assertEquals(true, cardGroup.isValidSequence(jokers));

	}		

	@Test
	public void testIsValidSequenceJokerHasSameSuitButDoesNotCompleteSequence() {
		CardGroup cardGroup = new CardGroup();
				
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.FIVE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		PlayingCard pc3 = new StandardPlayingCard(Suits.CLUBS,Ranks.THREE);
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		cardGroup.addCard(pc3);

		ArrayList<PlayingCard> jokers = new ArrayList<PlayingCard>();

		PlayingCard j1 = new StandardPlayingCard(Suits.CLUBS,Ranks.FIVE);
		jokers.add(j1);

		assertEquals(cardGroup.isValidSequence(jokers) ,true);

	}		

	@Test
	public void testIsValidSequenceNormalScenario() {
		CardGroup cardGroup = new CardGroup();

		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.FIVE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.HEARTS,Ranks.TWO);
		PlayingCard pc3 = new StandardPlayingCard(Suits.CLUBS,Ranks.THREE);
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		cardGroup.addCard(pc3);

		ArrayList<PlayingCard> jokers = new ArrayList<PlayingCard>();

		PlayingCard j1 = new StandardPlayingCard(Suits.HEARTS,Ranks.TWO);
		jokers.add(j1);

		assertEquals(true, cardGroup.isValidSequence(jokers));

	}		

	@Test
	public void testIsValidSequenceThreeCardsTwoJokers() {
		CardGroup cardGroup = new CardGroup();
				
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.FIVE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.HEARTS,Ranks.TWO);
		PlayingCard pc3 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		cardGroup.addCard(pc3);

		ArrayList<PlayingCard> jokers = new ArrayList<PlayingCard>();

		PlayingCard j1 = new StandardPlayingCard(Suits.HEARTS,Ranks.TWO);
		PlayingCard j2 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		jokers.add(j1);
		jokers.add(j2);

		assertEquals(true, cardGroup.isValidSequence(jokers));

	}		
	
	@Test
	public void testIsNotValidSequenceNormal() {
		CardGroup cardGroup = new CardGroup();
				
		PlayingCard pc1 = new StandardPlayingCard(Suits.SPADES,Ranks.FIVE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.HEARTS,Ranks.TWO);
		PlayingCard pc3 = new StandardPlayingCard(Suits.SPADES,Ranks.EIGHT);
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		cardGroup.addCard(pc3);

		ArrayList<PlayingCard> jokers = new ArrayList<PlayingCard>();

		PlayingCard j1 = new StandardPlayingCard(Suits.HEARTS,Ranks.TWO);
		jokers.add(j1);

		assertEquals(false, cardGroup.isValidSequence(jokers));
		assertEquals(13,cardGroup.getPoints(jokers));

	}		

	@Test
	public void testIsNotvalidSequenceDifferentSuitsNoJokers() {
		CardGroup cardGroup = new CardGroup();

		PlayingCard pc1 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.ACE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.CLUBS,Ranks.ACE);
		PlayingCard pc3 = new StandardPlayingCard(Suits.CLUBS,Ranks.FIVE);
		PlayingCard pc4 = new StandardPlayingCard(Suits.HEARTS,Ranks.FIVE);

		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		cardGroup.addCard(pc3);
		cardGroup.addCard(pc4);

		ArrayList<PlayingCard> jokers = new ArrayList<PlayingCard>();		
		assertFalse(cardGroup.isValidSequence(jokers));
		assertEquals(30,cardGroup.getPoints(jokers));

	}

	@Test
	public void testIsNotValidSequenceWithTheTwoCard() {
		CardGroup cardGroup = new CardGroup();

		PlayingCard pc1 = new StandardPlayingCard(Suits.SPADES,Ranks.FIVE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.HEARTS,Ranks.TWO);
		PlayingCard pc3 = new StandardPlayingCard(Suits.SPADES,Ranks.TWO);
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		cardGroup.addCard(pc3);

		ArrayList<PlayingCard> jokers = new ArrayList<PlayingCard>();

		PlayingCard j1 = new StandardPlayingCard(Suits.HEARTS,Ranks.TWO);
		jokers.add(j1);

		assertEquals(false, cardGroup.isValidSequence(jokers));
		assertEquals(7,cardGroup.getPoints(jokers));

	}
	
	@Test
	public void testCardsWithSameSuitAceAceTwoThreeWithJokerIsNotValidSequence() {
		CardGroup cardGroup = new CardGroup();
		
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.ACE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.HEARTS,Ranks.KING);
		PlayingCard pc3 = new StandardPlayingCard(Suits.CLUBS,Ranks.THREE);
		PlayingCard pc4 = new StandardPlayingCard(Suits.CLUBS,Ranks.ACE);
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		cardGroup.addCard(pc3);
		cardGroup.addCard(pc4);
		
		ArrayList<PlayingCard> jokers = new ArrayList<PlayingCard>();

		PlayingCard j1 = new StandardPlayingCard(Suits.HEARTS,Ranks.KING);
		jokers.add(j1);
		
		assertEquals(false, cardGroup.isValidSequence(jokers));
		assertEquals(23,cardGroup.getPoints(jokers));

	}		

	@Test
	public void testInsertCardAtIndex() {
		
		CardGroup cardGroup = new CardGroup();
		
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.FIVE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		
		StandardPlayingCard spc3 = new StandardPlayingCard(Suits.CLUBS,Ranks.THREE);
		cardGroup.insertCardAtIndex(1, spc3);
		
		spc3 = (StandardPlayingCard)cardGroup.getCard(1);
		
		assertEquals(Suits.CLUBS,spc3.getSuit());
		assertEquals(Ranks.THREE,spc3.getRank());
		
		spc3 = (StandardPlayingCard)cardGroup.getCard(2);
		
		assertEquals(Suits.CLUBS,spc3.getSuit());
		assertEquals(Ranks.TWO,spc3.getRank());
		
	}

	@Test
	public void testGetJokerCountWithStandardCardAsGameJoker() {
		
		CardGroup cardGroup = new CardGroup();
		
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.FIVE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);

		PlayingCard jokerCard = new StandardPlayingCard(Suits.DIAMONDS,Ranks.FIVE);
		
		assertEquals(1,cardGroup.getJokerCount(jokerCard));
		
		jokerCard = new StandardPlayingCard(Suits.DIAMONDS,Ranks.SIX);
		
		assertEquals(0,cardGroup.getJokerCount(jokerCard));

		jokerCard = new StandardPlayingCard(Suits.DIAMONDS,Ranks.ACE);
		
		assertEquals(0,cardGroup.getJokerCount(jokerCard));
		
		PlayingCard pc3 = new StandardPlayingCard(Suits.SPADES,Ranks.ACE);
		cardGroup.addCard(pc3);
		
		assertEquals(1,cardGroup.getJokerCount(jokerCard));
		
		PlayingCard pc4 = new JokerPlayingCard();
		cardGroup.addCard(pc4);

		assertEquals(2,cardGroup.getJokerCount(jokerCard));
	}

	@Test
	public void testGetJokerCountWithPlainJokerAsGameJoker() {
		
		CardGroup cardGroup = new CardGroup();
		
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.FIVE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);

		PlayingCard jokerCard = new JokerPlayingCard();
		
		assertEquals(0,cardGroup.getJokerCount(jokerCard));
		
		PlayingCard pc3 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.ACE);
		cardGroup.addCard(pc3);
		
		assertEquals(1,cardGroup.getJokerCount(jokerCard));

		
	}

	@Test
	public void testGetIntersectCountwithOneContextJoker() {
		
		CardGroup cardGroup = new CardGroup();
		
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.FIVE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		PlayingCard pc3 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.ACE);
		
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		cardGroup.addCard(pc3);

		ArrayList<PlayingCard> jokers = new ArrayList<PlayingCard>();
		PlayingCard j1 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		jokers.add(j1);

		assertEquals(1,cardGroup.getQualifiedJokersInGroup(jokers).size());

		
	}
	@Test
	public void testGetIntersectCountwithTwoContextJokers() {
		
		CardGroup cardGroup = new CardGroup();
		
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.FIVE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		PlayingCard pc3 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.ACE);
		
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		cardGroup.addCard(pc3);

		ArrayList<PlayingCard> jokers = new ArrayList<PlayingCard>();

		PlayingCard j1 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		PlayingCard j2 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.ACE);
		jokers.add(j1);
		jokers.add(j2);

		assertEquals(2,cardGroup.getQualifiedJokersInGroup(jokers).size());

	}
	
	@Test
	public void testGetIntersectCountwithOneContextJokersAndOneRealJoker() {
		
		CardGroup cardGroup = new CardGroup();
		
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.FIVE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		PlayingCard jpc = new JokerPlayingCard();
		
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		cardGroup.addCard(jpc);

		ArrayList<PlayingCard> jokers = new ArrayList<PlayingCard>();

		PlayingCard j1 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		JokerPlayingCard j3 = new JokerPlayingCard();
		jokers.add(j1);
		jokers.add(j3);

		assertEquals(2,cardGroup.getQualifiedJokersInGroup(jokers).size());


	}

	@Test
	public void testIsPureSetSameRankMinmumCount() {
		
		CardGroup cardGroup = new CardGroup();
		
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.FIVE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.FIVE);
		
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		
		assertFalse(cardGroup.isPureSet());
		
		PlayingCard pc3 = new StandardPlayingCard(Suits.HEARTS,Ranks.FIVE);
		cardGroup.addCard(pc3);
		
		assertTrue(cardGroup.isPureSet());

	}

	@Test
	public void testIsInvalidPureSetNotSameRank() {
		
		CardGroup cardGroup = new CardGroup();
		
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.FIVE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.FIVE);
		PlayingCard pc3 = new StandardPlayingCard(Suits.HEARTS,Ranks.FOUR);
		
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		cardGroup.addCard(pc3);
		
		assertFalse(cardGroup.isPureSet());


	}

	@Test
	public void testIsPureSetSameRankFourCards() {
		
		CardGroup cardGroup = new CardGroup();
		
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.FIVE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.FIVE);
		PlayingCard pc3 = new StandardPlayingCard(Suits.HEARTS,Ranks.FIVE);
		PlayingCard pc4 = new StandardPlayingCard(Suits.SPADES,Ranks.FIVE);
		
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		cardGroup.addCard(pc3);
		cardGroup.addCard(pc4);
		
		assertTrue(cardGroup.isPureSet());

	}

	@Test
	public void testIsInvalidPureSetFourCardsSuitDuplicate() {
		
		CardGroup cardGroup = new CardGroup();
		
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.FIVE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.FIVE);
		PlayingCard pc3 = new StandardPlayingCard(Suits.SPADES,Ranks.FIVE);
		PlayingCard pc4 = new StandardPlayingCard(Suits.SPADES,Ranks.FIVE);
		
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		cardGroup.addCard(pc3);
		cardGroup.addCard(pc4);
		
		assertFalse(cardGroup.isPureSet());
		

	}


	@Test
	public void testIsInvalidSetNotMinmumCount() {
		
		CardGroup cardGroup = new CardGroup();
		
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.FIVE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.FIVE);
		
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);

		ArrayList<PlayingCard> jokers = new ArrayList<PlayingCard>();
		PlayingCard j1 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		JokerPlayingCard j3 = new JokerPlayingCard();
		jokers.add(j1);
		jokers.add(j3);
		
		assertFalse(cardGroup.isValidSet(jokers));

	}

	@Test
	public void testIsValidSetTwoJokersandAStandardCard() {
		
		CardGroup cardGroup = new CardGroup();
		
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.FIVE);
		JokerPlayingCard pc2 = new JokerPlayingCard();
		PlayingCard pc3 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		cardGroup.addCard(pc3);

		ArrayList<PlayingCard> jokers = new ArrayList<PlayingCard>();
		PlayingCard j1 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		JokerPlayingCard j3 = new JokerPlayingCard();
		jokers.add(j1);
		jokers.add(j3);
		
		assertTrue(cardGroup.isValidSet(jokers));

	}

	@Test
	public void testIsValidSetNormal() {

		CardGroup cardGroup = new CardGroup();
		
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.FIVE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.DIAMONDS,Ranks.FIVE);
		PlayingCard pc3 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		cardGroup.addCard(pc3);

		ArrayList<PlayingCard> jokers = new ArrayList<PlayingCard>();
		PlayingCard j1 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		JokerPlayingCard j3 = new JokerPlayingCard();
		jokers.add(j1);
		jokers.add(j3);
		
		assertTrue(cardGroup.isValidSet(jokers));

	}

	@Test
	public void testHasAllStandardCardsEmptyGroup() {
		CardGroup cardGroup = new CardGroup();
		assertFalse(cardGroup.hasAllStandardCards());
	}

	@Test
	public void testHasAllStandardCardsGroupWithJoker() {
		CardGroup cardGroup = new CardGroup();
		cardGroup.addCard(new StandardPlayingCard(Suits.CLUBS, Ranks.ACE));
		cardGroup.addCard(new StandardPlayingCard(Suits.CLUBS, Ranks.TWO));
		cardGroup.addCard(new JokerPlayingCard());
		assertFalse(cardGroup.hasAllStandardCards());
	}

	@Test
	public void testHasAllStandardCardsAllStandardCards() {
		CardGroup cardGroup = new CardGroup();
		cardGroup.addCard(new StandardPlayingCard(Suits.CLUBS, Ranks.ACE));
		cardGroup.addCard(new StandardPlayingCard(Suits.HEARTS, Ranks.TWO));
		assertTrue(cardGroup.hasAllStandardCards());
	}

	@Test
	public void testIsInvalidSetHasDuplicates() {

		CardGroup cardGroup = new CardGroup();
		
		PlayingCard pc1 = new StandardPlayingCard(Suits.CLUBS,Ranks.FIVE);
		PlayingCard pc2 = new StandardPlayingCard(Suits.HEARTS,Ranks.FIVE);
		PlayingCard pc3 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		PlayingCard pc4 = new StandardPlayingCard(Suits.CLUBS,Ranks.FIVE);
		
		cardGroup.addCard(pc1);
		cardGroup.addCard(pc2);
		cardGroup.addCard(pc3);
		cardGroup.addCard(pc4);

		ArrayList<PlayingCard> jokers = new ArrayList<PlayingCard>();
		PlayingCard j1 = new StandardPlayingCard(Suits.CLUBS,Ranks.TWO);
		JokerPlayingCard j3 = new JokerPlayingCard();
		jokers.add(j1);
		jokers.add(j3);
		
		assertFalse(cardGroup.isValidSet(jokers));

	}
	
}
