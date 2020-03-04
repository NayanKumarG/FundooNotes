/**
 * @author Nayan Kumar G
 * purpose : Dto for User
 * date    :25-02-2020
 */
package com.bridgelabz.fundoonotes.dto;
import org.springframework.stereotype.Component;
import lombok.Data;

@Component
@Data
public class UserDto {

	private String name;
	private String email;
	private String password;
	private long phoneNumber;
		
}
