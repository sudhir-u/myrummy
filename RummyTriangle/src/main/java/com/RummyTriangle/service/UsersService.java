package com.RummyTriangle.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.RummyTriangle.domain.IUserRepository;
import com.RummyTriangle.domain.User;

@Service
public class UsersService implements UserDetailsService{

	@Autowired
	IUserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//UsersNDetails usersNDetails = new UsersNDetails(username);
		//System.out.println("User with " + usersNDetails.getUsername() + "/" + usersNDetails.getPassword() +" has authorities" + usersNDetails.getAuthorities().toString());
		
		Optional<User> user = userRepository.findByUserName(username);
		
		user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
		
		return user.map(UsersNDetails::new).get();
		
		//return usersNDetails; 

	}
	
}
