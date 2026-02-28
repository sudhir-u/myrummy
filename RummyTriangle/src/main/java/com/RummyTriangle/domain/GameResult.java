package com.RummyTriangle.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Result of a completed game: scores per player, winner, commission.
 */
public class GameResult {

	private String gameId;
	private int winnerPlayerIndex;
	private List<PlayerScore> playerScores = new ArrayList<>();
	private long totalPotCents;
	private long commissionCents;
	private double commissionRate;  // e.g. 0.05 for 5%

	public static class PlayerScore {
		private int playerIndex;
		private String userName;
		private int points;           // Deadwood points
		private long winningsCents;   // Positive for winner, negative for losers
		private boolean isWinner;

		public PlayerScore(int playerIndex, String userName, int points, long winningsCents, boolean isWinner) {
			this.playerIndex = playerIndex;
			this.userName = userName;
			this.points = points;
			this.winningsCents = winningsCents;
			this.isWinner = isWinner;
		}

		public int getPlayerIndex() { return playerIndex; }
		public String getUserName() { return userName; }
		public int getPoints() { return points; }
		public long getWinningsCents() { return winningsCents; }
		public boolean isWinner() { return isWinner; }
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public int getWinnerPlayerIndex() {
		return winnerPlayerIndex;
	}

	public void setWinnerPlayerIndex(int winnerPlayerIndex) {
		this.winnerPlayerIndex = winnerPlayerIndex;
	}

	public List<PlayerScore> getPlayerScores() {
		return playerScores;
	}

	public void addPlayerScore(PlayerScore ps) {
		playerScores.add(ps);
	}

	public long getTotalPotCents() {
		return totalPotCents;
	}

	public void setTotalPotCents(long totalPotCents) {
		this.totalPotCents = totalPotCents;
	}

	public long getCommissionCents() {
		return commissionCents;
	}

	public void setCommissionCents(long commissionCents) {
		this.commissionCents = commissionCents;
	}

	public double getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(double commissionRate) {
		this.commissionRate = commissionRate;
	}
}
