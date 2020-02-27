/**
 * @author Nayan Kumar G
 * purpose : Provide services for User
 * 
 */

package com.bridgelabz.fundoonotes.service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.configuration.PasswordSecurityConfiguration;
import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
	
	private UserRepository userRepository;
	private User user;
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, User user, BCryptPasswordEncoder bcryptPasswordEncoder, PasswordSecurityConfiguration passwordSecurity) {
		super();
		this.userRepository = userRepository;
		this.user = user;
		this.bcryptPasswordEncoder= bcryptPasswordEncoder;
	
	}


	@Transactional
	@Override
	public void addUser(UserDto userDto) {
		BeanUtils.copyProperties(userDto , user);
		user.setDateTime(LocalDateTime.now());
		user.setPassword(bcryptPasswordEncoder.encode((userDto.getPassword())));
		userRepository.save(user);
	}
	
	
	@Transactional
	@Override
	public List<User> getUsers() {
	
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(users::add);
		return users;
	}


	@Override
	public Optional<User> getUser(long userId) {
		
		return userRepository.findById(userId);
	}

}
