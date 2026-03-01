package com.RummyTriangle.service;

import java.util.List;

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
}
