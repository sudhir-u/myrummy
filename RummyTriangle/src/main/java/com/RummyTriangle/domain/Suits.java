package com.RummyTriangle.domain;


public enum Suits{
	SPADES(1),
	HEARTS(2),
	DIAMONDS(3),
	CLUBS(4);
	
	private int suitOrder;
	
	private Suits(int o){
		suitOrder = o;
	}
	
	public int getSuitOrder() {
		return suitOrder;
	}
		
}


