package com.RummyTriangle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.RummyTriangle.domain.User;

@RestController
public class HomeResource {

	@Autowired
	private WebService webservice; 
	
	@GetMapping("/")
	public String home()
	{
		return ("<html><h1>Welcome to Rummy Triangle</h1>\n"
				+ "<a href=\"/user\">Login</a>\n"
				+ " to play</html>");
	}

	@GetMapping("/admin")
	public String admin()
	{
		return ("<h1Welcome to Rummy Triangle Administration</h1>");
	}

	@GetMapping("/user")
	public String user()
	{
		return ("<h1>Welcome to Rummy Triangle User page</h1>");
	}

	@RequestMapping("/liveusers")
	public List<User> getLiveUsers()
	{
		return webservice.getLoggedInUsers();
	}
	@RequestMapping("/users/{idd}")
	public User getUser(@PathVariable("idd") String id)
	{
		return webservice.getUserInfo(id);
	}
	@RequestMapping(value="/users", method=RequestMethod.POST)
	public void addUser(@RequestBody User user)
	{
		webservice.addUser(user);
	}
	@RequestMapping(value="/users/{idd}", method=RequestMethod.PUT)
	public void updateUser(@PathVariable("idd") String id, @RequestBody User user)
	{
		webservice.updateUser(id, user);
	}
}
