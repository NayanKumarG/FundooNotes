/**
 * @author Nayan Kumar G
 * purpose : Controller for Users
 * date    :25-02-2020
 */
package com.bridgelabz.fundoonotes.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.dto.UserLoginDto;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;


	/**
	 * Api for user registration
	 * @param userDto creates object request by user
	 * @return response to the user
	 */
	@PostMapping(value = "/users/register")
	public ResponseEntity<String> userRegistration(@RequestBody UserDto userDto)
	{

		userService.addUser(userDto);
		return new ResponseEntity<String>("Registration success", HttpStatus.OK);

	}

	/**
	 * 
	 * @param userLogindto creates object request by user
	 * @return response to the user
	 */
	@PostMapping("/users/login")
	public ResponseEntity<String> userLogin(@RequestBody UserLoginDto userLoginDto)
	{
		
		if(userService.verifyPassword(userLoginDto))
		
		return new ResponseEntity<String>("Login success", HttpStatus.OK);
			
		return new ResponseEntity<String>("Enter correct credentials", HttpStatus.OK);
	}
	/**
	 * 
	 * @param token taken by the user to verify mail
	 * @return response to the user
	 */
	@PostMapping("/users/verifyMail/{token}")
	public ResponseEntity<String> userVerification(@PathVariable String token)
	{
		if(userService.updateMailVerification(token)) {

			return new ResponseEntity<String>("Verification success", HttpStatus.OK);
		}
			
		return new ResponseEntity<String>("Verification failed", HttpStatus.OK);
	}

	/**
	 * 
	 * @param userId given by the user to get the detail
	 * @return returns user object
	 */
	@GetMapping(value = "/user/{userId}")
	public Optional<User> getUser(@PathVariable long userId)
	{
		return userService.getUser(userId);
	}

	/**
	 * 
	 * @param userId given by the user to delete user
	 */
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

