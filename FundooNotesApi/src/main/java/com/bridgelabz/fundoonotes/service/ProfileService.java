/**
 * @author Nayan Kumar G
 * purpose : Provide services to user profile 
 * date    :16-03-2020
 */
package com.bridgelabz.fundoonotes.service;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.S3Object;
import com.bridgelabz.fundoonotes.entity.ProfileEntity;

public interface ProfileService {

	ProfileEntity uploadFileToS3(MultipartFile file, String originalFilename);

	ProfileEntity updateFileInS3(String token);

	S3Object getProfilePic(String token);

}
