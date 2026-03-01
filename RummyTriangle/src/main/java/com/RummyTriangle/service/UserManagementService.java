package com.RummyTriangle.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.RummyTriangle.domain.IUserRepository;
import com.RummyTriangle.domain.User;

@Service
public class UserManagementService {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public Optional<User> getUserByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}

	public Optional<User> getUserById(int id) {
		return userRepository.findById(id);
	}

	public User createUser(User user) {
		if (user.getPassword() == null || user.getPassword().length() < 6) {
			throw new IllegalArgumentException("Password must be at least 6 characters");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		if (user.getRoles() == null || user.getRoles().isEmpty()) {
			user.setRoles("USER");
		}
		if (user.getUserName() == null || !user.getUserName().matches("^[a-zA-Z0-9_]{2,50}$")) {
			throw new IllegalArgumentException("userName must be 2-50 alphanumeric or underscore characters");
		}
		return userRepository.save(user);
	}

	public Optional<User> updateUser(String userName, User updates) {
		return userRepository.findByUserName(userName)
			.map(existing -> {
				if (updates.getPassword() != null && !updates.getPassword().isEmpty()) {
					existing.setPassword(passwordEncoder.encode(updates.getPassword()));
				}
				if (updates.getRoles() != null) {
					existing.setRoles(updates.getRoles());
				}
				if (updates.getActive() != existing.getActive()) {
					existing.setActive(updates.getActive());
				}
				return userRepository.save(existing);
			});
	}

	/** Reset password for a user (e.g. after manual DB insert with wrong hash). Min 6 characters. */
	public Optional<User> resetPassword(String userName, String newPassword) {
		if (newPassword == null || newPassword.length() < 6) {
			throw new IllegalArgumentException("Password must be at least 6 characters");
		}
		return userRepository.findByUserName(userName)
			.map(u -> {
				u.setPassword(passwordEncoder.encode(newPassword));
				return userRepository.save(u);
			});
	}
}
