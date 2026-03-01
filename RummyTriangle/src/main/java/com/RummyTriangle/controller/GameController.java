package com.RummyTriangle.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.RummyTriangle.domain.User;
import com.RummyTriangle.service.GameRoomService;
import com.RummyTriangle.service.GameService;
import com.RummyTriangle.service.UserManagementService;

@Controller
public class GameController {

	@Autowired
	private GameRoomService gameRoomService;

	@Autowired
	private UserManagementService userManagementService;

	@PostMapping("/api/game/create")
	@ResponseBody
	public ResponseEntity<Map<String, String>> createRoom(Principal principal, @RequestBody Map<String, Object> body) {
		if (principal == null) {
			return ResponseEntity.status(401).build();
		}
		User user = userManagementService.getUserByUserName(principal.getName()).orElse(null);
		if (user == null) {
			return ResponseEntity.status(401).build();
		}
		int maxPlayers = body.containsKey("maxPlayers") ? ((Number) body.get("maxPlayers")).intValue() : 4;
		String roomId = gameRoomService.createRoom(user, maxPlayers);
		Map<String, String> res = new HashMap<>();
		res.put("roomId", roomId);
		return ResponseEntity.ok(res);
	}

	@PostMapping("/api/game/{roomId}/join")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> joinRoom(Principal principal, @PathVariable String roomId) {
		if (principal == null) {
			return ResponseEntity.status(401).build();
		}
		User user = userManagementService.getUserByUserName(principal.getName()).orElse(null);
		if (user == null) {
			return ResponseEntity.status(401).build();
		}
		boolean joined = gameRoomService.joinRoom(roomId, user);
		Map<String, Object> res = new HashMap<>();
		res.put("joined", joined);
		res.put("roomId", roomId);
		return ResponseEntity.ok(res);
	}

	@GetMapping("/api/game/{roomId}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getRoomState(@PathVariable String roomId) {
		return gameRoomService.getRoom(roomId)
				.map(game -> {
					Map<String, Object> m = new HashMap<>();
					m.put("roomId", roomId);
					m.put("state", game.getState().name());
					m.put("playerCount", game.getPlayerCount());
					return ResponseEntity.ok(m);
				})
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/api/game/{roomId}/start")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> startGameRest(Principal principal, @PathVariable String roomId) {
		if (principal == null) {
			return ResponseEntity.status(401).build();
		}
		Map<String, Object> out = new HashMap<>();
		out.put("roomId", roomId);
		out.put("type", "start");
		return gameRoomService.getRoom(roomId)
				.map(game -> {
					try {
						game.dealCards();
						game.startPlay();
						gameRoomService.broadcastRoomState(roomId, game);
						out.put("success", true);
						out.put("state", game.getState().name());
						out.put("playerCount", game.getPlayerCount());
						return ResponseEntity.ok(out);
					} catch (Exception e) {
						out.put("success", false);
						out.put("error", e.getMessage());
						return ResponseEntity.ok(out);
					}
				})
				.orElse(ResponseEntity.notFound().build());
	}

	@MessageMapping("/game/start")
	@SendTo("/topic/game")
	public Map<String, Object> startGame(Principal principal, Map<String, String> payload) {
		String roomId = payload.get("roomId");
		Map<String, Object> out = new HashMap<>();
		out.put("type", "start");
		out.put("roomId", roomId);
		gameRoomService.getRoom(roomId).ifPresent(game -> {
			try {
				game.dealCards();
				game.startPlay();
				gameRoomService.broadcastRoomState(roomId, game);
				out.put("success", true);
			} catch (Exception e) {
				out.put("success", false);
				out.put("error", e.getMessage());
			}
		});
		return out;
	}
}
