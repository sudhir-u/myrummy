package com.RummyTriangle.service;

import org.springframework.stereotype.Service;

import com.RummyTriangle.domain.GameResult;
import com.RummyTriangle.domain.GameResult.PlayerScore;

/**
 * Computes game results and commission.
 * Commission is taken from the winner's winnings (house cut).
 */
@Service
public class ScoringService {

	private static final double DEFAULT_COMMISSION_RATE = 0.05;  // 5%
	private static final long CENTS_PER_POINT = 10;  // 10 cents per deadwood point (configurable)

	/**
	 * Builds GameResult when a player declares.
	 * @param gameId game identifier
	 * @param winnerIndex index of declaring (winning) player
	 * @param pointsPerPlayer deadwood points for each player (winner=0)
	 * @param userNames user names for each player
	 * @return filled GameResult
	 */
	public GameResult computeResult(String gameId, int winnerIndex,
			int[] pointsPerPlayer, String[] userNames) {
		return computeResult(gameId, winnerIndex, pointsPerPlayer, userNames, DEFAULT_COMMISSION_RATE);
	}

	public GameResult computeResult(String gameId, int winnerIndex,
			int[] pointsPerPlayer, String[] userNames, double commissionRate) {

		GameResult result = new GameResult();
		result.setGameId(gameId);
		result.setWinnerPlayerIndex(winnerIndex);
		result.setCommissionRate(commissionRate);

		int playerCount = pointsPerPlayer.length;
		long totalPotCents = 0;
		for (int i = 0; i < playerCount; i++) {
			if (i != winnerIndex) {
				totalPotCents += pointsPerPlayer[i] * CENTS_PER_POINT;
			}
		}

		long commissionCents = Math.round(totalPotCents * commissionRate);
		long winnerNetCents = totalPotCents - commissionCents;

		result.setTotalPotCents(totalPotCents);
		result.setCommissionCents(commissionCents);

		for (int i = 0; i < playerCount; i++) {
			int pts = pointsPerPlayer[i];
			long winningsCents = (i == winnerIndex) ? winnerNetCents : -(pts * CENTS_PER_POINT);
			result.addPlayerScore(new PlayerScore(
					i,
					i < userNames.length ? userNames[i] : "player" + i,
					pts, winningsCents, i == winnerIndex));
		}

		return result;
	}
}
