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
 * Seeds an admin user (admin/admin123) if no users exist.
 * Set app.seed-admin=false to disable.
 */
@Component
@Order(1)
public class DataSeeder implements ApplicationRunner {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Value("${app.seed-admin:true}")
	private boolean seedAdmin;

	@Override
	public void run(ApplicationArguments args) {
		if (!seedAdmin || userRepository.count() > 0) {
			return;
		}
		User admin = new User("admin");
		admin.setPassword(passwordEncoder.encode("admin123"));
		admin.setActive(true);
		admin.setRoles("ADMIN,USER");
		userRepository.save(admin);
	}
}
