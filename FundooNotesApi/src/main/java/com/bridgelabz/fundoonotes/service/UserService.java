/**
 * @author Nayan Kumar G
 * purpose : Interface for user Service
 * date    :25-02-2020
 */
package com.bridgelabz.fundoonotes.service;
import java.util.List;
import java.util.Optional;

import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.dto.UserLoginDto;
import com.bridgelabz.fundoonotes.entity.User;


public interface UserService {
	
	void addUser(UserDto userDto);
	List<User> getUsers();
	Optional<User> getUser(long userId);
	void deleteUser(long userId);
	boolean updateMailVerification(String token);
	boolean verifyPassword(UserLoginDto userLoginDto);

}
