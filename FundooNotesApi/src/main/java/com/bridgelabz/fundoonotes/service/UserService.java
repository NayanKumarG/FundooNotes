/**
 * @author Nayan Kumar G
 * purpose : Interface for users Service
 * date    :25-02-2020
 */
package com.bridgelabz.fundoonotes.service;
import java.util.List;
import com.bridgelabz.fundoonotes.dto.UpdatePasswordDto;
import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.dto.UserLoginDto;
import com.bridgelabz.fundoonotes.entity.User;


public interface UserService {
	
	boolean addUser(UserDto userDto);
	boolean updateMailVerification(String token);
	boolean verifyLogin(UserLoginDto userLoginDto);
	boolean isMailExist(String email);
	boolean confirmMail(String emailId);
	boolean updatePassword(UpdatePasswordDto updatePasswordDto, String token);
	List<User> getUsers();
	User getUser(String token);
	
}
