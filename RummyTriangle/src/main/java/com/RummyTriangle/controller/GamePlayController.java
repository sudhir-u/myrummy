package com.RummyTriangle.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.RummyTriangle.domain.GameResult;
import com.RummyTriangle.domain.PlayingCard;
import com.RummyTriangle.domain.PlayingCardTypes;
import com.RummyTriangle.domain.StandardPlayingCard;
import com.RummyTriangle.service.GameRoomService;
import com.RummyTriangle.service.GameService;
import com.RummyTriangle.service.Hand;
import com.RummyTriangle.service.Player;
import com.RummyTriangle.service.ScoringService;

@RestController
public class GamePlayController {

	@Autowired
	private GameRoomService gameRoomService;

	@SuppressWarnings("unchecked")
	private static ResponseEntity<Map<String, Object>> notFoundMap() {
		return (ResponseEntity<Map<String, Object>>) (ResponseEntity<?>) ResponseEntity.status(404).body(new HashMap<String, Object>());
	}

	private static ResponseEntity<Map<String, Object>> forbiddenMap(String message) {
		Map<String, Object> m = new HashMap<>();
		m.put("error", message != null ? message : "Forbidden");
		return ResponseEntity.status(403).body(m);
	}

	@Autowired
	private ScoringService scoringService;

	@GetMapping("/api/game/{roomId}/turn")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getTurn(Principal principal, @PathVariable String roomId) {
		if (principal == null) return ResponseEntity.status(401).build();
		return gameRoomService.getRoom(roomId)
				.map(game -> {
					int myIndex = game.getPlayerIndexByUsername(principal.getName());
					if (myIndex < 0) return forbiddenMap("Not in this room");
					Player current = game.getCurrentPlayer();
					Map<String, Object> m = new HashMap<>();
					m.put("currentPlayerIndex", game.getCurrentPlayerIndex());
					m.put("currentPlayerName", current != null && current.getUser() != null ? current.getUser().getUserName() : null);
					m.put("isYourTurn", game.getCurrentPlayerIndex() == myIndex);
					m.put("mustDiscard", game.isCurrentPlayerMustDiscard());
					m.put("canDraw", game.getCurrentPlayerIndex() == myIndex && !game.isCurrentPlayerMustDiscard());
					m.put("state", game.getState().name());
					return ResponseEntity.ok(m);
				})
				.orElse(notFoundMap());
	}

	@GetMapping("/api/game/{roomId}/hand")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getHand(Principal principal, @PathVariable String roomId) {
		if (principal == null) return ResponseEntity.status(401).build();
		return gameRoomService.getRoom(roomId)
				.map(game -> {
					int myIndex = game.getPlayerIndexByUsername(principal.getName());
					if (myIndex < 0) return forbiddenMap("Not in this room");
					Player p = game.getPlayers().get(myIndex);
					Hand hand = p.getHand();
					if (hand == null) {
						Map<String, Object> m = new HashMap<>();
						m.put("cards", new ArrayList<>());
						return ResponseEntity.ok(m);
					}
					List<Map<String, Object>> cards = new ArrayList<>();
					for (int gi = 0; gi < hand.getCardGroupCount(); gi++) {
						for (int ci = 0; ci < hand.getCardGroupAtIndex(gi).getCardCount(); ci++) {
							PlayingCard card = hand.getCardGroupAtIndex(gi).getCard(ci);
							Map<String, Object> cardMap = new HashMap<>();
							cardMap.put("groupIndex", gi);
							cardMap.put("cardIndex", ci);
							cardMap.put("description", card.getDescription());
							cardMap.put("type", card.getCardType().name());
							if (card.getCardType() == PlayingCardTypes.STANDARD) {
								StandardPlayingCard spc = (StandardPlayingCard) card;
								cardMap.put("suit", spc.getSuit().name());
								cardMap.put("rank", spc.getRank().name());
							} else {
								cardMap.put("suit", null);
								cardMap.put("rank", null);
							}
							cards.add(cardMap);
						}
					}
					Map<String, Object> m = new HashMap<>();
					m.put("cards", cards);
					return ResponseEntity.ok(m);
				})
				.orElse(notFoundMap());
	}

	@GetMapping("/api/game/{roomId}/discard-top")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getDiscardTop(Principal principal, @PathVariable String roomId) {
		if (principal == null) return ResponseEntity.status(401).build();
		return gameRoomService.getRoom(roomId)
				.map(game -> {
					Map<String, Object> m = new HashMap<>();
					PlayingCard top = game.getDiscardSet().getOpenCard();
					if (top == null) {
						m.put("description", null);
						return ResponseEntity.ok(m);
					}
					m.put("description", top.getDescription());
					m.put("type", top.getCardType().name());
					if (top.getCardType() == PlayingCardTypes.STANDARD) {
						StandardPlayingCard spc = (StandardPlayingCard) top;
						m.put("suit", spc.getSuit().name());
						m.put("rank", spc.getRank().name());
					}
					return ResponseEntity.ok(m);
				})
				.orElse(notFoundMap());
	}

	@PostMapping("/api/game/{roomId}/draw/deck")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> drawFromDeck(Principal principal, @PathVariable String roomId) {
		if (principal == null) return ResponseEntity.status(401).build();
		return gameRoomService.getRoom(roomId)
				.map(game -> {
					if (game.getCurrentPlayerIndex() != game.getPlayerIndexByUsername(principal.getName())) {
						Map<String, Object> err = new HashMap<>(); err.put("error", "Not your turn"); return ResponseEntity.status(403).body(err);
					}
					String desc = game.drawFromDeckForCurrentPlayer();
					if (desc != null) gameRoomService.broadcastRoomState(roomId, game);
					Map<String, Object> m = new HashMap<>();
					m.put("success", desc != null);
					m.put("drawn", desc);
					return ResponseEntity.ok(m);
				})
				.orElse(notFoundMap());
	}

	@PostMapping("/api/game/{roomId}/draw/discard")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> drawFromDiscard(Principal principal, @PathVariable String roomId) {
		if (principal == null) return ResponseEntity.status(401).build();
		return gameRoomService.getRoom(roomId)
				.map(game -> {
					if (game.getCurrentPlayerIndex() != game.getPlayerIndexByUsername(principal.getName())) {
						Map<String, Object> err = new HashMap<>(); err.put("error", "Not your turn"); return ResponseEntity.status(403).body(err);
					}
					String desc = game.drawFromDiscardForCurrentPlayer();
					if (desc != null) gameRoomService.broadcastRoomState(roomId, game);
					Map<String, Object> m = new HashMap<>();
					m.put("success", desc != null);
					m.put("drawn", desc);
					return ResponseEntity.ok(m);
				})
				.orElse(notFoundMap());
	}

	@PostMapping("/api/game/{roomId}/discard")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> discard(Principal principal, @PathVariable String roomId, @RequestBody Map<String, Object> body) {
		if (principal == null) return ResponseEntity.status(401).build();
		return gameRoomService.getRoom(roomId)
				.map(game -> {
					if (game.getCurrentPlayerIndex() != game.getPlayerIndexByUsername(principal.getName())) {
						Map<String, Object> err = new HashMap<>(); err.put("error", "Not your turn"); return ResponseEntity.status(403).body(err);
					}
					int groupIndex = body.get("groupIndex") != null ? ((Number) body.get("groupIndex")).intValue() : -1;
					int cardIndex = body.get("cardIndex") != null ? ((Number) body.get("cardIndex")).intValue() : -1;
					boolean ok = game.discardFromCurrentPlayer(groupIndex, cardIndex);
					if (ok) gameRoomService.broadcastRoomState(roomId, game);
					Map<String, Object> m = new HashMap<>();
					m.put("success", ok);
					return ResponseEntity.ok(m);
				})
				.orElse(notFoundMap());
	}

	@PostMapping("/api/game/{roomId}/declare")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> declare(Principal principal, @PathVariable String roomId) {
		if (principal == null) return ResponseEntity.status(401).build();
		return gameRoomService.getRoom(roomId)
				.map(game -> {
					int myIndex = game.getPlayerIndexByUsername(principal.getName());
					if (myIndex < 0) return forbiddenMap("Not in this room");
					if (game.getCurrentPlayerIndex() != myIndex) {
						Map<String, Object> errMap = new HashMap<>();
						errMap.put("success", false);
						errMap.put("error", "Not your turn");
						return ResponseEntity.ok(errMap);
					}
					Player me = game.getPlayers().get(myIndex);
					Hand hand = me.getHand();
					if (hand == null || !hand.isRummy()) {
						Map<String, Object> errMap = new HashMap<>();
						errMap.put("success", false);
						errMap.put("error", "Invalid hand. Need valid runs/sets with 0 points.");
						return ResponseEntity.ok(errMap);
					}
					try {
						game.declare(me);
						int[] points = new int[game.getPlayerCount()];
						String[] names = new String[game.getPlayerCount()];
						for (int i = 0; i < game.getPlayerCount(); i++) {
							Player pl = game.getPlayers().get(i);
							points[i] = pl.getHand() != null ? pl.getHand().getPoints() : 0;
							names[i] = pl.getUser() != null ? pl.getUser().getUserName() : "player" + i;
						}
						GameResult result = scoringService.computeResult(roomId, myIndex, points, names);
						Map<String, Object> m = new HashMap<>();
						m.put("success", true);
						m.put("message", "You declared! You win.");
						Map<String, Object> resultMap = new HashMap<>();
						resultMap.put("winnerIndex", result.getWinnerPlayerIndex());
						resultMap.put("commissionCents", result.getCommissionCents());
						resultMap.put("playerScores", result.getPlayerScores().stream().map(ps -> {
							Map<String, Object> psMap = new HashMap<>();
							psMap.put("userName", ps.getUserName());
							psMap.put("points", ps.getPoints());
							psMap.put("winningsCents", ps.getWinningsCents());
							psMap.put("winner", ps.isWinner());
							return psMap;
						}).collect(Collectors.toList()));
						m.put("result", resultMap);
						gameRoomService.broadcastRoomState(roomId, game);
						return ResponseEntity.ok(m);
					} catch (Exception e) {
						Map<String, Object> errMap = new HashMap<>();
						errMap.put("success", false);
						errMap.put("error", e.getMessage());
						return ResponseEntity.ok(errMap);
					}
				})
				.orElse(notFoundMap());
	}
}
