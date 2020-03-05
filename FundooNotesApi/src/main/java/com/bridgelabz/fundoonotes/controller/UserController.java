/**
 * @author Nayan Kumar G
 * purpose : Controller for Users
 * date    :25-02-2020
 */
package com.bridgelabz.fundoonotes.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.UpdatePasswordDto;
import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.dto.UserLoginDto;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;


	/**
	 * Api for user registration
	 * @param userDto creates object request by user
	 * @return response for user registration
	 */
	@PostMapping("/users/register")
	public ResponseEntity<Response> userRegistration(@RequestBody UserDto userDto)
	{

		if(userService.addUser(userDto))

			return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Registration success" ,userDto));


		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new Response("User already exist with this mail Id"));	
	}

	/**
	 * 
	 * @param userLogindto creates object request by user
	 * @return response of user login
	 */
	@PostMapping("/users/login")
	public ResponseEntity<Response> userLogin(@RequestBody UserLoginDto userLoginDto)
	{

		if(userService.verifyLogin(userLoginDto))

			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("Login Success"));

		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Response("incorrect mail or password"));
	}
	/**
	 * 
	 * @param token taken by the url to verify mail
	 * @return response of verification
	 */
	@GetMapping("/users/verifyMail/{token}")
	public ResponseEntity<Response> userVerification(@PathVariable String token)
	{
		if(userService.updateMailVerification(token)) 

			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("verification success"));

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Verification failed:"));

	}


	/**
	 * 
	 * @param email taken from url
	 * @return response of user verification
	 */
	@PostMapping("/users/forgotPassword")
	public ResponseEntity<Response> forgotPassword(@RequestParam("email") String email)
	{
		if(userService.confirmMail(email))

			return ResponseEntity.status(HttpStatus.FOUND).body(new Response("User verified"));

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("User account not present"));

	}

	/**
	 * 
	 * @param updatePasswordDto creates object for updatePasswordDto
	 * @param token taken from url
	 * @return response for password updation
	 */
	@PutMapping("/users/updatePassword/{token}")
	public ResponseEntity<Response> updatePassword( @RequestBody UpdatePasswordDto updatePasswordDto , @PathVariable("token") String token){

		if(userService.updatePassword(updatePasswordDto , token))

			return ResponseEntity.status(HttpStatus.OK).body(new Response("Password updated"));


		return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(new Response("Confirm mail verification"));	

	}


	/**
	 * 
	 * @param userId given by the user to get the detail
	 * @return returns user object
	 */
	@GetMapping("/users/getUser/{token}")
	public ResponseEntity<Response> getUser(@PathVariable String token)
	{
		User user = userService.getUser(token);
		if(user!=null)
			
		return ResponseEntity.status(HttpStatus.OK).body(new Response("User found" , user));
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("User Not found"));
	}

	/**
	 * 
	 * @return list of users
	 */
	@GetMapping("/users/getUsers")
	public ResponseEntity<Response> getUsers()
	{
		List<User> user = userService.getUsers();
		return ResponseEntity.status(HttpStatus.OK).body(new Response("users found" , user));
	}





}

