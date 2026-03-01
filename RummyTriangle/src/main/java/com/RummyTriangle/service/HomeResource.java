package com.RummyTriangle.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RummyTriangle.domain.User;

@RestController
public class HomeResource {

	@Autowired
	private UserManagementService userManagementService;

	@GetMapping("/")
	public String home() {
		return ("<html><h1>Welcome to Rummy Triangle</h1>\n"
				+ "<a href=\"/user\">Login</a>\n"
				+ " to play</html>");
	}

	@GetMapping("/admin")
	public String admin() {
		return "<h1>Welcome to Rummy Triangle Administration</h1>";
	}

	@GetMapping("/user")
	public String user() {
		return "<h1>Welcome to Rummy Triangle</h1><p><a href=\"/game.html\">Play Rummy</a></p>";
	}

	@GetMapping("/liveusers")
	public List<User> getLiveUsers() {
		return userManagementService.getAllUsers();
	}

	@GetMapping("/users/{userName}")
	public ResponseEntity<User> getUser(@PathVariable("userName") String userName) {
		return userManagementService.getUserByUserName(userName)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/users")
	public User addUser(@Valid @RequestBody User user) {
		return userManagementService.createUser(user);
	}

	@PutMapping("/users/{userName}")
	public ResponseEntity<User> updateUser(@PathVariable("userName") String userName, @Valid @RequestBody User user) {
		return userManagementService.updateUser(userName, user)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	/**
	 * Reset password for a user (admin only). Use after manual DB insert with wrong BCrypt hash.
	 * Body: { "password": "newpassword" } (min 6 chars).
	 */
	@PutMapping("/users/{userName}/password")
	public ResponseEntity<?> resetPassword(@PathVariable("userName") String userName, @RequestBody Map<String, String> body) {
		String password = body != null ? body.get("password") : null;
		try {
			return userManagementService.resetPassword(userName, password)
					.map(u -> ResponseEntity.ok().body(Map.of("ok", true, "message", "Password updated for " + userName)))
					.orElse(ResponseEntity.notFound().build());
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
		}
	}
}
