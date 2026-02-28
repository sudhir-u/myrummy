package com.RummyTriangle.domain;

public enum Ranks {
	ACE(1,10),
	KING(2,10),
	QUEEN(3,10),
	JACK(4,10),
	TEN(5,10),
	NINE(6,9),
	EIGHT(7,8),
	SEVEN(8,7),
	SIX(9,6),
	FIVE(10,5),
	FOUR(11,4),
	THREE(12,3),
	TWO(13,2);
	
	private int priority;
	private int points;
	
	private Ranks(int rankPriority, int rankPoints){
		priority = rankPriority;
		points = rankPoints;
	}
	
	public int getPriority() {
		return priority;
	}

	public int getPoints() {
		return points;
	}

}


