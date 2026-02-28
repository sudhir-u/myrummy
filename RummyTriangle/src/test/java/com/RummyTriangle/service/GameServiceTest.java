package com.RummyTriangle.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.RummyTriangle.domain.CardGroup;
import com.RummyTriangle.domain.Deck;
import com.RummyTriangle.domain.GameState;
import com.RummyTriangle.domain.PlayingCard;
import com.RummyTriangle.domain.Ranks;
import com.RummyTriangle.domain.RummyDoubleDeck;
import com.RummyTriangle.domain.RummySingleDeck;
import com.RummyTriangle.domain.StandardDeck;
import com.RummyTriangle.domain.StandardPlayingCard;
import com.RummyTriangle.domain.Suits;
import com.RummyTriangle.domain.User;
import com.RummyTriangle.service.GameService;
import com.RummyTriangle.service.Player;


public class GameServiceTest {

	private GameService game1;
	private ArrayList<Player> players; 
	private Deck deck1;
	
	@Before 
	public void setUp(){
		
		//game1 = new GameService(new RummyDoubleDeck());
		game1 = new GameService();
		game1.setCardDeck(new RummyDoubleDeck());

		User user1 = new User();
		user1.setUserName("suunnikr");
		
		User user2 = new User();
		user2.setUserName("sh.sherkat");

		User user3 = new User();
		user3.setUserName("nazanin");

		Player p1 = new Player();
		p1.setUser(user1);

		Player p2 = new Player();
		p2.setUser(user2);

		Player p3 = new Player();
		p3.setUser(user3);
		
		game1.addPlayer(p1);
		game1.addPlayer(p2);
		game1.addPlayer(p3);
		
	}

	@Test
	public void testGameService() {

//		System.out.println("JACK's rank is " + Ranks.JACK.getPriority() + ". The points it contributes in Rummy is " + Ranks.JACK.getPoints());
		
		StandardDeck cd = new StandardDeck();
		assertEquals(cd.getCardCount(), 52);
		
		
		RummySingleDeck rd1 = new RummySingleDeck();//default is 2 jokers
		assertEquals(rd1.getCardCount(), 54);
		
		RummySingleDeck rd2 = new RummySingleDeck(1);
		assertEquals(rd2.getCardCount(), 53);

		RummyDoubleDeck rdd1 = new RummyDoubleDeck();
		assertEquals(rdd1.getCardCount(), 108);
		
	}

	@Test
	public void testGameServicePullCard() {

		RummyDoubleDeck rdd3 = new RummyDoubleDeck();
		assertEquals(rdd3.getCardCount(), 108);
		
		if  (rdd3.getCardCount()>0) {
			ArrayList<PlayingCard> rdd3cards = rdd3.getPlayingCards();
			
			for (int ii=0; ii < rdd3cards.size(); ii++ ){
				System.out.println("Index : "+ ii + ". Card : " + rdd3cards.get(ii).getDescription());
			}
			
			int rndIndex = rdd3.pickRandomIndex();
			PlayingCard rc = rdd3.pullCardAtIndex(rndIndex);
			assertEquals(rdd3.getCardCount(), 107);

		}
	}
		/*		
		

		RummyDoubleDeck rdd4 = new RummyDoubleDeck();
  		System.out.println("Card count in a double rummy deck is : " + rdd4.getCardCount());
		System.out.println("The cards are :");
		
		if  (rdd4.getCardCount()>0) {
			ArrayList<PlayingCard> rdd4cards = rdd4.getPlayingCards();
			
			for (int iii=0; iii < rdd4cards.size(); iii++ ){
				System.out.println("Index : "+ iii + ". Card : " + rdd4cards.get(iii).getDescription());
			}
			
			rdd4.shuffleCards();

			ArrayList<PlayingCard> rdd44cards = rdd4.getPlayingCards();
			
			for (int iij=0; iij < rdd44cards.size(); iij++ ){
				System.out.println("Index : "+ iij + ". Card : " + rdd44cards.get(iij).getDescription());
			}
			

		}

*/
	


		//setUp();

		/*		
		deck1   = game1.getCardDeck();
		
		
		if (deck1.getPlayingCards().size() > 0 ){

			System.out.println("This game has a deck, consisting of :" + deck1.getPlayingCards().size() + " cards. The cards are the following:");//done
			for (int z=0;z<deck1.getPlayingCards().size();z++){
				System.out.println( deck1.getPlayingCards().get(z).getDescription());
				
			}
		}
		*/
        
		//game1.dealCards();
		
		
		/*
		if (game1.getPlayers().size() > 0 ){
			
			System.out.println("Players Count :" + game1.getPlayers().size() + ".");//done
			
			ArrayList<Player> playersRetrieved = game1.getPlayers();

			Hand playerHand = new Hand() ;
			
			
			for (int zz=0;zz<playersRetrieved.size() ;zz++){
				System.out.println("Player " + zz + " is " + playersRetrieved.get(zz).getUser().getUserName());

				playerHand = game1.getPlayers().get(zz).getHand();

				int cardCount = playerHand.getCardCount();
				System.out.println("Card count in hand :" + cardCount);//done
				
				ArrayList<PlayingCard> playingCards = new ArrayList<PlayingCard>();
				playerHand.getCardsIntoArray(playingCards);
				 
				for (int cardIndex=0;cardIndex < playingCards.size(); cardIndex++){
					System.out.println(playingCards.get(cardIndex).getDescription());
				}
				playingCards = null;
			}

		}
		*/


		//game1.pullJoker();
		
		/*
		System.out.println("Joker card for this game is :" + game1.getJoker().getDescription());
		
		if (deck1.getPlayingCards().size() > 0 ){

			System.out.println("Now, the deck has " + deck1.getPlayingCards().size() + " cards. They are :");
			
			for (int zzz=0;zzz<deck1.getPlayingCards().size();zzz++){
				System.out.println( deck1.getPlayingCards().get(zzz).getDescription());
				
			}
		}
		*/
		


	@Test
	public void testGameSequence() {
		
		assertEquals(game1.getPlayerCount(),3 );
		
		assertFalse(game1.getCardDeck().getPlayingCards().size()== 0);
		assertEquals(game1.getCardDeck().getPlayingCards().size(), 108);

		game1.dealCards();

		Hand handOfUser1 =  game1.getPlayers().get(0).getHand(); 
		assertEquals(game1.getPlayers().get(0).getUser().getUserName() ,"suunnikr");
		assertEquals(handOfUser1.getCardCount(),13 );
		assertEquals(game1.getPlayers().get(1).getHand().getCardCount(),13 );
		assertEquals(game1.getPlayers().get(2).getHand().getCardCount(),13 );
		
		game1.pullJoker();

		// dealCards pulls 39 (13*3) + 1 (firstCard for discard), pullJoker pulls 1 = 41; 108-41=67
		assertEquals(67, game1.getCardDeck().getCardCount());

	}

	@Test
	public void testGameStateAndStartPlay() {
		assertEquals(GameState.LOBBY, game1.getState());
		game1.dealCards();
		assertEquals(GameState.DEAL, game1.getState());
		game1.startPlay();
		assertEquals(GameState.PLAY, game1.getState());
		assertNotNull(game1.getDiscardSet().getOpenCard());
		assertEquals(0, game1.getCurrentPlayerIndex());
	}

}
