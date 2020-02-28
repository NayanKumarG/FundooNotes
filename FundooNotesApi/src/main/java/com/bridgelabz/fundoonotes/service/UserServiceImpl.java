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

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.configuration.PasswordSecurityConfiguration;
import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.utility.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private User user;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private EntityManager entityManager;

	@Transactional
	@Override
	public void addUser(UserDto userDto) {
		BeanUtils.copyProperties(userDto , user);
		user.setDateTime(LocalDateTime.now());
		user.setPassword(bcryptPasswordEncoder.encode((userDto.getPassword())));
		String token = jwtUtil.generateToken(userDto);
		log.info("Generated token:"+token);
		Session session = entityManager.unwrap(Session.class);
		session.save(user);
		
		//userRepository.save(user);
	}
	
	
	@Transactional
	@Override
	public List<User> getUsers() {
	
		Session session = entityManager.unwrap(Session.class);
		Query<User> query = session.createQuery("from User" , User.class);
		List<User> users = query.getResultList();
		//List<User> users = new ArrayList<>();
		//userRepository.findAll().forEach(users::add);
		return users;
	}

	@Transactional
	@Override
	public Optional<User> getUser(long userId) {
		
		return userRepository.findById(userId);
		
	}

	@Transactional
	@Override
	public void deleteUser(long userId) {
		Session session = entityManager.unwrap(Session.class);
		User user = session.get(User.class, userId);
		session.delete(user);
	}


	

}
