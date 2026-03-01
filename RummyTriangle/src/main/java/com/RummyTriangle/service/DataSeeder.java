package com.RummyTriangle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.RummyTriangle.domain.IUserRepository;
import com.RummyTriangle.domain.User;

/**
 * Ensures default users exist on every startup: admin (admin/admin123), alice (alice/alice123), bob (bob/bob123).
 * Creates only if missing, so existing DB users are left unchanged.
 * Set app.seed-users=false to disable.
 */
@Component
@Order(1)
public class DataSeeder implements ApplicationRunner {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Value("${app.seed-users:true}")
	private boolean seedUsers;

	@Override
	public void run(ApplicationArguments args) {
		if (!seedUsers) {
			return;
		}
		// Admin — create only if not present
		userRepository.findByUserName("admin").orElseGet(() -> {
			User u = new User("admin");
			u.setPassword(passwordEncoder.encode("admin123"));
			u.setActive(true);
			u.setRoles("ADMIN,USER");
			return userRepository.save(u);
		});
		// Alice — create only if not present
		userRepository.findByUserName("alice").orElseGet(() -> {
			User u = new User("alice");
			u.setPassword(passwordEncoder.encode("alice123"));
			u.setActive(true);
			u.setRoles("USER");
			return userRepository.save(u);
		});
		// Bob — create only if not present
		userRepository.findByUserName("bob").orElseGet(() -> {
			User u = new User("bob");
			u.setPassword(passwordEncoder.encode("bob123"));
			u.setActive(true);
			u.setRoles("USER");
			return userRepository.save(u);
		});
	}
}
