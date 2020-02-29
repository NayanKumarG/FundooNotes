/**
 * @author Nayan Kumar G
 * purpose : Provide services for User
 * date    :25-02-2020
 */

package com.bridgelabz.fundoonotes.service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.dto.UserLoginDto;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.response.EmailModel;
import com.bridgelabz.fundoonotes.utility.EmailUtil;
import com.bridgelabz.fundoonotes.utility.JwtUtil;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

//	@Autowired
//	private User user;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private EmailModel emailModel;
	
	User user = null;

	/**
	 * logic to register for user
	 */
	@Transactional
	@Override
	public void addUser(UserDto userDto) {
		
		user = new User();
		BeanUtils.copyProperties(userDto , user);
		user.setDateTime(LocalDateTime.now());
		user.setPassword(bcryptPasswordEncoder.encode((userDto.getPassword())));
		user.setVerified(false);
		Session session = entityManager.unwrap(Session.class);
		session.save(user);
		//userRepository.save(user);

		/*String token = jwtUtil.generateToken(user.getId());
		log.info("Generated token:"+token);
		log.info("Decrypted:"+jwtUtil.parseToken(token));*/
		String link ="http://localhost:8080/users/verifyMail/"+jwtUtil.generateToken(user.getId());
		emailModel.setMessage(link);
		emailModel.setEmail(userDto.getEmail());
		emailModel.setSubject("Click link to Verify ");
		EmailUtil.sendAttachmentEmail(emailModel.getEmail(), emailModel.getSubject(), emailModel.getMessage());

	}

	/**
	 * Get the list of users
	 */
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




	/**
	 * get the user detail by userId
	 */
	@Transactional
	@Override
	public Optional<User> getUser(long userId) {

		return userRepository.findById(userId);

	}

	/**
	 * Delete the user by userId
	 */
	@Transactional
	@Override
	public void deleteUser(long userId) {
		Session session = entityManager.unwrap(Session.class);
		User user = session.get(User.class, userId);
		session.delete(user);
	}



	/**
	 * To verify the user
	 */
	@Transactional
	@Override
	public boolean updateMailVerification(String token) {
		long id = jwtUtil.parseToken(token); 
		user = new User();
		if(id!=0)
		{
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("update User set is_verified =:verified "+" where user_id =:u_id");
	    query.setParameter("verified", true);
		query.setParameter("u_id" , id);
		query.executeUpdate();
		return true;
		}
		return false;
		
//		Session session = entityManager.unwrap(Session.class);
//		
//		user.setVerified(true);
//		//session.saveOrUpdate(user);
//		userRepository.save(user);
	//	return true;
		

	}

	@Transactional
	@Override
	public boolean verifyPassword(UserLoginDto userLoginDto) {
		String password = bcryptPasswordEncoder.encode(userLoginDto.getPassword());
		String email = userLoginDto.getEmail();
		Session session = entityManager.unwrap(Session.class);
		User user = session.get(User.class, email);
		if(user!=null && password.equals(user.getPassword()))
		{
			return true;
		}
		
		
		return false;
	}






}
