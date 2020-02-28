/**
 * @author Nayan Kumar G
 * purpose : Controller for Users
 * 
 */
package com.bridgelabz.fundoonotes.controller;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@PostMapping(value = "/users/register")
	public ResponseEntity<String> userRegistration(@RequestBody UserDto userDto)
	{

		userService.addUser(userDto);
		return new ResponseEntity<String>("Registration success", HttpStatus.OK);

	}
	

	@GetMapping(value = "/user/{userId}")
	public Optional<User> getUser(@PathVariable long userId)
	{
		return userService.getUser(userId);
	}
	
	@GetMapping("/deleteUser/{userId}")
	public void deleteUser(@PathVariable long userId)
	{
		userService.deleteUser(userId);
	}
	@GetMapping(value = "/users")
	public List<User> getUsers()
	{

		return userService.getUsers();
	}
	
	
	
	
	
}

