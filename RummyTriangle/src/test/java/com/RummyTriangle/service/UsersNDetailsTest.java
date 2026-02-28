package com.RummyTriangle.service;

import static org.junit.Assert.*;

import org.junit.Test;

import com.RummyTriangle.domain.User;

public class UsersNDetailsTest {

	@Test
	public void testNullRolesDefaultsToUserAuthority() {
		User user = new User("testuser");
		user.setPassword("pwd");
		user.setActive(true);
		user.setRoles(null);

		UsersNDetails details = new UsersNDetails(user);

		assertTrue(details.getAuthorities().stream()
				.anyMatch(a -> "ROLE_USER".equals(a.getAuthority())));
		assertEquals(1, details.getAuthorities().size());
	}

	@Test
	public void testEmptyRolesDefaultsToUserAuthority() {
		User user = new User("testuser");
		user.setPassword("pwd");
		user.setActive(true);
		user.setRoles("");

		UsersNDetails details = new UsersNDetails(user);

		assertTrue(details.getAuthorities().stream()
				.anyMatch(a -> "ROLE_USER".equals(a.getAuthority())));
		assertEquals(1, details.getAuthorities().size());
	}

	@Test
	public void testRolesWithAdminAndUser() {
		User user = new User("admin");
		user.setPassword("pwd");
		user.setActive(true);
		user.setRoles("ADMIN,USER");

		UsersNDetails details = new UsersNDetails(user);

		assertEquals(2, details.getAuthorities().size());
		assertTrue(details.getAuthorities().stream()
				.anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority())));
		assertTrue(details.getAuthorities().stream()
				.anyMatch(a -> "ROLE_USER".equals(a.getAuthority())));
	}

}
