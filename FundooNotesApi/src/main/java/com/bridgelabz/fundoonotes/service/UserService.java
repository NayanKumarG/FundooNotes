/**
 * @author Nayan Kumar G
 * purpose : Interface for user Service
 * 
 */
package com.bridgelabz.fundoonotes.service;
import java.util.List;
import java.util.Optional;

import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.entity.User;


public interface UserService {
	
	void addUser(UserDto userDto);
	List<User> getUsers();
	Optional<User> getUser(long userId);

}
