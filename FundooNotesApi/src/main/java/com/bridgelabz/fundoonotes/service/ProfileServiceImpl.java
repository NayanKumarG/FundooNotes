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
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.bridgelabz.fundoonotes.entity.ProfileEntity;
import com.bridgelabz.fundoonotes.entity.User;
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
		
		//long userId = jwtUtil.parseToken(token);
		User user = userRepository.findById(3);
		if(user!=null)
		{
			ProfileEntity profile = new ProfileEntity(file.getOriginalFilename() , user);
			profileRepository.save(profile);
           // saveOrUpdate(file , profile);
            return profile;
		}
		
		else
			throw new UserNotFoundException("user not exist", HttpStatus.NOT_FOUND);
	
	}

	/**
	 * service to update file
	 */
	@Override
	public ProfileEntity updateFileInS3(MultipartFile file, String token) 
	{
		long userId = 3;//jwtUtil.parseToken(token);
		User user = userRepository.findById(userId);
		ProfileEntity profile = profileRepository.findByUserId(userId);
		if(user!=null)
		{
		amazonS3.deleteObject(bucketName , profile.getProfileName());	
		//profileRepository.delete(profile);
		//saveOrUpdate(file , profile);
		return profile;
			
	    }else
			throw new UserNotFoundException("user not exist", HttpStatus.NOT_FOUND);
	
		
		}
	
	public ProfileEntity saveOrUpdate(MultipartFile file ,ProfileEntity profile)
	{
		ObjectMetadata objectMetaData = new ObjectMetadata();
		objectMetaData.setContentType(file.getContentType());
		objectMetaData.setContentLength(file.getSize());
		try {
			System.out.println("in ");
		
			amazonS3.putObject(bucketName , file.getOriginalFilename() , file.getInputStream() , objectMetaData);
		} catch (SdkClientException | IOException e) {
			
			e.printStackTrace();
		}
		profileRepository.save(profile);
		return profile;
	}
	
}
