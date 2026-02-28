package com.RummyTriangle.domain;

/**
 * Lifecycle states of a rummy game.
 */
public enum GameState {
	LOBBY,      // Waiting for players
	DEAL,       // Dealing cards, pulling joker
	PLAY,       // Players drawing/discarding
	DECLARE,    // A player has declared
	SCORE,      // Calculating scores, distributing winnings
	FINISHED    // Game over
}
