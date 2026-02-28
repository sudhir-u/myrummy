package com.RummyTriangle.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.RummyTriangle.domain.User;

public class UsersNDetails implements UserDetails{

	private String userName;
	private String password;
	private boolean active;
	private List<GrantedAuthority> authorities;

	public UsersNDetails(User user) {
		this.userName = user.getUserName();
		this.password = user.getPassword();
		this.active = user.getActive();
		this.userName = user.getUserName();
		String roles = user.getRoles();
		this.authorities = (roles == null || roles.isEmpty())
				? List.of(new SimpleGrantedAuthority("ROLE_USER"))
				: Arrays.stream(roles.split(","))
						.map(String::trim)
						.map(r -> r.startsWith("ROLE_") ? r : "ROLE_" + r)
						.map(SimpleGrantedAuthority::new)
						.collect(Collectors.toList());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		//TODO
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		//TODO
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		return active;
	}


}
