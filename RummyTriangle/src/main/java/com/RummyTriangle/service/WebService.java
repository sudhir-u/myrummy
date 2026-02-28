package com.RummyTriangle.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.RummyTriangle.domain.User;

@Service
public class WebService {

	private List<User> loggedInUsers = new ArrayList<> (Arrays.asList(
			new User("suunnikr", "9886310243", "Sudhir", "Kumar", "M"), 
			new User("shsherkat", "9902977099", "Shirin", "Sherkar", "F"), 
			new User("ajayank", "9447129862", "Ajayan", "Krishnankutty", "M")
			));

	public List<User> getLoggedInUsers() {
		return loggedInUsers;
	}
	
	public User getUserInfo(String id) {
		return loggedInUsers.stream().filter(t -> t.getUserName().equals(id)).findFirst().get();
	}

	public void addUser(User user) {
		loggedInUsers.add(user);
	}
	public void updateUser(String id, User user) {
		for (int i = 0; i < loggedInUsers.size(); i++) {
			User iUser = loggedInUsers.get(i);
			if (iUser.getUserName().equals(id)) {
				loggedInUsers.set(i, user);
				return;
			}
		}
	}
}
