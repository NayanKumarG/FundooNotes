/**
 * @author Nayan Kumar G
 * purpose :Dto to update password
 * date    :2-03-2020
 */
package com.bridgelabz.fundoonotes.dto;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class UpdatePasswordDto {

	private String email;
	private String password;
	private String confirmPassword;
	
}
