/**
 * @author Nayan Kumar G
 * purpose : Entity for User
 * 
 */
package com.bridgelabz.fundoonotes.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Email;

import org.springframework.stereotype.Component;
import lombok.Data;
@Data
@Component
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "email" , nullable = false, unique = true)
	@Email(message = "Please provide a valid e-mail")
	private String email;
	
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "phone_number")
	private long phoneNumber;
	
	@Column(name = "date")
	private LocalDateTime dateTime;
	
	@Column(name = "is_verified")
	private boolean isVerified;
		
	
}
