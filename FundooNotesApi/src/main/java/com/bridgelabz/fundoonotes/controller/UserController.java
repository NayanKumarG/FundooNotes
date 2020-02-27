/**
 * @author Nayan Kumar G
 * purpose : Controller for Users
 * 
 */
package com.bridgelabz.fundoonotes.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.service.UserService;

@RestController
public class UserController {

	private UserService userService;

	/**
	 * 
	 * @param userService to initialize
	 */
	@Autowired
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@PostMapping(value = "/users/register")
	public String userRegistration(@RequestBody UserDto userDto)
	{

		userService.addUser(userDto);
		return "Registartion svuccess";

	}
	
	@GetMapping(value = "/users")
	public List<User> getUsers()
	{
		
		return userService.getUsers();
	}
	
	@GetMapping(value = "/user/{userId}")
	public Optional<User> getUser(@PathVariable long userId)
	{
		return userService.getUser(userId);
	}
	
	
}

