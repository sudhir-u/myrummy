package com.RummyTriangle.service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.RummyTriangle.domain.Deck;
import com.RummyTriangle.domain.GameState;
import com.RummyTriangle.domain.User;

/**
 * In-memory game rooms. Creates games and routes players to rooms.
 */
@Service
public class GameRoomService {

	@Autowired
	private Deck deck;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	private final Map<String, GameService> rooms = new ConcurrentHashMap<>();
	private int roomCounter;

	public String createRoom(User creator, int maxPlayers) {
		String roomId = "room-" + (++roomCounter);
		GameService game = new GameService();
		game.setCardDeck(deck);
		game.setState(GameState.LOBBY);
		Player p = new Player();
		p.setUser(creator);
		game.addPlayer(p);
		rooms.put(roomId, game);
		return roomId;
	}

	public Optional<GameService> getRoom(String roomId) {
		return Optional.ofNullable(rooms.get(roomId));
	}

	public boolean joinRoom(String roomId, User user) {
		GameService game = rooms.get(roomId);
		if (game == null || game.getState() != GameState.LOBBY) {
			return false;
		}
		if (game.getPlayerCount() >= 6) {
			return false;
		}
		Player p = new Player();
		p.setUser(user);
		game.addPlayer(p);
		broadcastRoomState(roomId, game);
		return true;
	}

	public void broadcastRoomState(String roomId, GameService game) {
		String[] playerNames = game.getPlayers().stream()
				.map(pl -> pl.getUser() != null ? pl.getUser().getUserName() : "?")
				.toArray(String[]::new);
		com.RummyTriangle.service.Player current = game.getState() == GameState.PLAY ? game.getCurrentPlayer() : null;
		messagingTemplate.convertAndSend("/topic/room/" + roomId, new RoomStatePayload(
				roomId,
				game.getState().name(),
				game.getPlayerCount(),
				playerNames,
				current != null ? game.getCurrentPlayerIndex() : null,
				current != null && current.getUser() != null ? current.getUser().getUserName() : null,
				game.getState() == GameState.PLAY && game.isCurrentPlayerMustDiscard()
		));
	}

	public static class RoomStatePayload {
		public String roomId;
		public String state;
		public int playerCount;
		public String[] playerNames;
		public Integer currentPlayerIndex;
		public String currentPlayerName;
		public Boolean mustDiscard;

		public RoomStatePayload(String roomId, String state, int playerCount, String[] playerNames,
				Integer currentPlayerIndex, String currentPlayerName, Boolean mustDiscard) {
			this.roomId = roomId;
			this.state = state;
			this.playerCount = playerCount;
			this.playerNames = playerNames;
			this.currentPlayerIndex = currentPlayerIndex;
			this.currentPlayerName = currentPlayerName;
			this.mustDiscard = mustDiscard;
		}
	}
}
