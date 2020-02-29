/**
 * @author Nayan Kumar G
 * purpose : Dto for user login
 * date    :29-02-2020
 */
package com.bridgelabz.fundoonotes.dto;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Component
public class UserLoginDto {

	private String email;
	private String password;
	
}
