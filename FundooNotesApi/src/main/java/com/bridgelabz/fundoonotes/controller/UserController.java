/**
 * @author Nayan Kumar G
 * purpose : Controller for Users
 * date    :25-02-2020
 */
package com.bridgelabz.fundoonotes.controller;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.S3Object;
import com.bridgelabz.fundoonotes.dto.UpdatePasswordDto;
import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.dto.UserLoginDto;
import com.bridgelabz.fundoonotes.entity.NoteEntity;
import com.bridgelabz.fundoonotes.entity.ProfileEntity;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.ProfileService;
import com.bridgelabz.fundoonotes.service.UserService;

import org.springframework.cache.annotation.Cacheable;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ProfileService profileService;
	/**
	 * Api for user registration
	 * @param userDto creates object request by user
	 * @return response for user registration
	 */
	@PostMapping("/users/register")
	public ResponseEntity<Response> userRegistration(@Valid @RequestBody UserDto userDto)
	{

		User user = userService.addUser(userDto);
		if(user!=null)
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Registration success" ,userDto));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Registration not success" ));

	}

	/**
	 * Api to user login
	 * @param userLogindto creates object request by user
	 * @return response of user login
	 */
	@PostMapping("/users/login")
	public ResponseEntity<Response> userLogin(@RequestBody UserLoginDto userLoginDto)
	{

		userService.verifyLogin(userLoginDto);

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("Login Success"));

		
	}
	/**
	 * Api to verify mail
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
	 * Api for forgot password
	 * @param email taken from url
	 * @return response of user verification
	 */
	@PostMapping("/users/forgotPassword")
	public ResponseEntity<Response> forgotPassword(@RequestParam("email") String email)
	{
		userService.confirmMail(email);

		return ResponseEntity.status(HttpStatus.FOUND).body(new Response("User verified"));


	}

	/**
	 * Api for update password
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
	 * Api to get perticular user
	 * @param userId given by the user to get the detail
	 * @return returns user object
	 */
	//@Cacheable(value = "user" , key = "#token")
	@GetMapping("/users/getUser/{token}")
	public ResponseEntity<Response> getUser(@PathVariable String token)
	{

		User user = userService.getUser(token);


		if(user!=null)

		return ResponseEntity.status(HttpStatus.OK).body(new Response("User found" , user));
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("User Not found"));
	}

	/**
	 * Api to get all users
	 * @return list of users
	 */
	@GetMapping("/users/getUsers")
	public ResponseEntity<Response> getUsers()
	{
		List<User> user = userService.getUsers();
		return ResponseEntity.status(HttpStatus.OK).body(new Response("users found" , user));
	}
	
	/**
	 * Api to add collaborator to note
	 * @param noteId to get note
	 * @param email to collaborate
	 * @param token to identify user
	 * 
	 */
	@PostMapping("/users/addCollaborator")
	public ResponseEntity<Response> addCollaborator(@RequestParam long noteId , @RequestParam String email , @RequestHeader String token)
	{
		NoteEntity collaboratedNote = userService.addCollaborator(noteId , email , token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Collaborator added" , collaboratedNote));
	}

	/**
	 * Api to delete collaborator
	 * @param noteId to get note
	 * @param email to identify collaborator
	 * @param token to identify user
	 * 
	 */
	@DeleteMapping("/users/deleteCollaborator")
	public ResponseEntity<Response> deleteCollaborator(@RequestParam long noteId , @RequestParam String email , @RequestHeader String token)
	{
		NoteEntity collaboratedNote = userService.deleteCollaborator(noteId , email , token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Collaborator deleted" , collaboratedNote));
	}
	
	/**
	 * Api to get collaborater notes
	 * @param token to identify user
	 * 
	 */
	@GetMapping("/users/getCollaboratorNotes")
	public ResponseEntity<Response> getCollaboratorNotes(@RequestHeader String token)
	{
		List<NoteEntity> collaboratorNotes = userService.getCollaboratorNotes(token);
		
		return ResponseEntity.status(HttpStatus.OK).body(new Response("collaborator notes fetched" , collaboratorNotes));
		
	}
	
	/**
	 * Api to upload file to aws S3
	 * @param file to upload
	 * @param token to identify user
	 * 
	 */
	@PostMapping("users/uploadFile")
	public ResponseEntity<Response> uploadFile(@ModelAttribute MultipartFile file,@RequestHeader String token) {
		ProfileEntity profile = profileService.uploadFileToS3(file,token);

		if(profile!=null)

			return ResponseEntity.status(HttpStatus.OK).body(new Response("profile added succussefully", profile));

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("profile not added"));
	}
	
	/**
	 * Api to update file
	 * @param token to identify user
	 * 
	 */
	@PutMapping("users/updateFile")
	public ResponseEntity<Response> updateProfile(@RequestHeader String token) {
		ProfileEntity profile = profileService.updateFileInS3(token);

		if(profile!=null)

			return ResponseEntity.status(HttpStatus.OK).body(new Response("profile updated succussefully", profile));

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("profile not updated"));
	}
	
	/**
	 * Api to get file from aws
	 * @param token to identify user
	 * 
	 */
	@GetMapping("/getFile")
	public ResponseEntity<Response> getProfilePic(@RequestHeader String token)
	{
		S3Object profilePic = profileService.getProfilePic(token);
		if(profilePic!=null)

			return ResponseEntity.status(HttpStatus.OK).body(new Response("profile added succussefully", profilePic));

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("profile not added"));
	}
	
}

