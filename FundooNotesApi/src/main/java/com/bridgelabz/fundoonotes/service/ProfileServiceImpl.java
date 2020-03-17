/**
 * @author Nayan Kumar G
 * purpose : Provide services to user profile 
 * date    :16-03-2020
 */
package com.bridgelabz.fundoonotes.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.bridgelabz.fundoonotes.entity.ProfileEntity;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.exception.UploadFileFailureException;
import com.bridgelabz.fundoonotes.exception.UserNotFoundException;
import com.bridgelabz.fundoonotes.repository.ProfileRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.utility.JwtUtil;

@Service
public class ProfileServiceImpl implements ProfileService{

	@Autowired
	private AmazonS3 amazonS3;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Value("${bucketName}")
	private String bucketName;
	
	/**
	 * service to upload file to AWS bucket
	 */
	@Override
	public ProfileEntity uploadFileToS3(MultipartFile file, String token) {
		
		long userId = jwtUtil.parseToken(token);
		User user = userRepository.findById(userId);
		if(user!=null)
		{
			ProfileEntity profile = new ProfileEntity(file.getOriginalFilename() , user);
			profileRepository.save(profile);
			ObjectMetadata objectMetaData = new ObjectMetadata();
			objectMetaData.setContentType(file.getContentType());
			objectMetaData.setContentLength(file.getSize());
			try {
				amazonS3.putObject(bucketName , file.getOriginalFilename() , file.getInputStream() , objectMetaData);
			} catch (Exception e) {
				
				throw new UploadFileFailureException("File uploading failed", HttpStatus.NOT_ACCEPTABLE);
			}
			profileRepository.save(profile);
			return profile;
		}
		
		else
			throw new UserNotFoundException("user not exist", HttpStatus.NOT_FOUND);
	
	}

	/**
	 * service to update file
	 */
	@Override
	public ProfileEntity updateFileInS3(String token) 
	{
		long userId = jwtUtil.parseToken(token);
		User user = userRepository.findById(userId);
		ProfileEntity profile = profileRepository.findByUserId(userId);
		if(user!=null)
		{
		amazonS3.deleteObject(bucketName , profile.getProfileName());	
		profileRepository.delete(profile);
		return profile;
			
	    }else
			throw new UserNotFoundException("user not exist", HttpStatus.NOT_FOUND);
	
		
		}
	
	
	/**
	 * Service to get file from aws
	 */
	@Override
	public S3Object getProfilePic(String token) {
		long userId = jwtUtil.parseToken(token);
		User user = userRepository.findById(userId);
		if(user!=null)
		{
			ProfileEntity profile = profileRepository.findByUserId(userId);
		if(profile!=null)
		{
			S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucketName , profile.getProfileName()));
			return s3Object;	
		}
		return null;
	
		}
		else
		throw new UserNotFoundException("user not exist", HttpStatus.NOT_FOUND);
		
	}
	
}
