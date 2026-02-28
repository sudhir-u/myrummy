package com.RummyTriangle.service;

import static org.junit.Assert.*;

import org.junit.Test;

import com.RummyTriangle.domain.GameResult;

public class ScoringServiceTest {

	private ScoringService scoringService = new ScoringService();

	@Test
	public void testComputeResultWinnerGetsNetAfterCommission() {
		int[] points = {0, 20, 15};  // winner has 0, others have deadwood
		String[] names = {"alice", "bob", "carol"};

		GameResult result = scoringService.computeResult("g1", 0, points, names);

		assertEquals(3, result.getPlayerScores().size());
		assertEquals(0, result.getWinnerPlayerIndex());
		assertTrue(result.getCommissionCents() > 0);
		// totalPot = 20*10 + 15*10 = 350 cents; 5% = 17.5 round 18; winner gets 332
		assertEquals(350, result.getTotalPotCents());
		assertEquals(18, result.getCommissionCents());
		// Winner gets totalPot - commission
		GameResult.PlayerScore winner = result.getPlayerScores().get(0);
		assertTrue(winner.isWinner());
		assertEquals(332, winner.getWinningsCents());
	}

	@Test
	public void testLosersHaveNegativeWinnings() {
		int[] points = {0, 10, 25};
		String[] names = {"w", "l1", "l2"};

		GameResult result = scoringService.computeResult("g2", 0, points, names);

		assertEquals(-100, result.getPlayerScores().get(1).getWinningsCents());
		assertEquals(-250, result.getPlayerScores().get(2).getWinningsCents());
	}
}
