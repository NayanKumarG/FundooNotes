/**
 * @author Nayan Kumar G
 * purpose : Configuration for BCryptPasswordEncoder
 * date    :25-02-2020
 */

package com.bridgelabz.fundoonotes.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PasswordSecurityConfiguration {
	
	/**
	 * 
	 * @return Object of BCryptPasswordEncoder
	 */
	@Bean
	public BCryptPasswordEncoder bcryptPassword()
	{
	 
		return new BCryptPasswordEncoder();
		
	}
}
