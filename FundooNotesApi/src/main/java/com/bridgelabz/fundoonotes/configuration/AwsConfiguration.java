/**
 * @author Nayan Kumar G
 * purpose : Configuration for AWS
 * date    :16-03-2020
 */
package com.bridgelabz.fundoonotes.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AwsConfiguration {

	@Value("${keyId}")
	private String keyId;
	
	@Value("${accessKey}")
	private String accessKey;
	
	@Value("${region}")
	private String region;
	
	@Bean
	public AmazonS3 awsClient() {
		BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey,keyId);
		return AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(region)).withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
		
	}	
}
