/**
 * @author Nayan Kumar G
 * purpose :Controller for User profile
 * date    :16-03-2020
 */
package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotes.entity.ProfileEntity;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.ProfileService;

@RestController
public class ProfileController {

	@Autowired
	private ProfileService profileService;

	/**
	 * Api to upload file to aws S3
	 * @param file to upload
	 * @param token to identify user
	 * 
	 */
	@PostMapping("/uploadFile")
	public ResponseEntity<Response> uploadFile(@ModelAttribute MultipartFile file,@RequestHeader String token) {
		ProfileEntity profile = profileService.uploadFileToS3(file,token);

		if(profile!=null)

			return ResponseEntity.status(HttpStatus.OK).body(new Response("profile added succussefully", profile));

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("profile not added"));
	}
	
	
	@PutMapping("/updateFile")
	public ResponseEntity<Response> updateProfile(@ModelAttribute MultipartFile file,@RequestHeader String token) {
		ProfileEntity profile = profileService.updateFileInS3(file,token);

		if(profile!=null)

			return ResponseEntity.status(HttpStatus.OK).body(new Response("profile updated succussefully", profile));

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("profile not updated"));
	}
	
}
