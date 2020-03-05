/**
 * @author Nayan Kumar G
 * purpose : Provide services for User
 * date    :25-02-2020
 */

package com.bridgelabz.fundoonotes.service;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.UpdatePasswordDto;
import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.dto.UserLoginDto;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.exception.UserNotFoundException;
import com.bridgelabz.fundoonotes.response.EmailModel;
import com.bridgelabz.fundoonotes.utility.EmailUtil;
import com.bridgelabz.fundoonotes.utility.JwtUtil;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class UserServiceImpl implements UserService {


	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;   

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private EmailModel emailModel;


	private User user = new User();

	/**
	 * method to register for user
	 */
	@Transactional
	@Override
	public boolean addUser(UserDto userDto) {

		if(!isMailExist(userDto.getEmail()))
		{
			BeanUtils.copyProperties(userDto , user);
			user.setDateTime(LocalDateTime.now());
			
			user.setPassword(bcryptPasswordEncoder.encode((userDto.getPassword())));
			user.setVerified(false);
			Session session = entityManager.unwrap(Session.class);
			session.save(user);
			
			/*userRepository.save(user);
			String token = jwtUtil.generateToken(user.getId());
			log.info("Generated token:"+token);
			log.info("Decrypted:"+jwtUtil.parseToken(token));*/

			emailModel.setMessage(EmailUtil.createLink("http://localhost:8080/users/verifyMail/", jwtUtil.generateToken(user.getId())));
			emailModel.setEmail(userDto.getEmail());
			emailModel.setSubject("Click link to Verify ");
			EmailUtil.sendAttachmentEmail(emailModel.getEmail(), emailModel.getSubject(), emailModel.getMessage());
			return true;
		}
		else

			throw new UserException("User already exist");


	}

	/**
	 * @param userLoginDto object for login
	 * @return true if password match
	 */
	@Override
	public boolean verifyLogin(UserLoginDto userLoginDto) {
		String mail = userLoginDto.getEmail();
		if(findByMail(mail)!=null)
		{
		User user = findByMail(mail);
			if(user.isVerified()==true && bcryptPasswordEncoder.matches(userLoginDto.getPassword(), user.getPassword()))
				return true;

		}
		throw new UserNotFoundException("user not found");
	}

	/**
	 * @param token to get the userId
	 * @return true if update success
	 */
	@Transactional
	@Override
	public boolean updatePassword(UpdatePasswordDto updatePasswordDto, String token) {
		if(updatePasswordDto.getPassword().equals(updatePasswordDto.getConfirmPassword()))
		{
			try
			{
				long userId = jwtUtil.parseToken(token);
				log.info("my id:"+userId);
				User user = findById(userId);
				if(user!=null && user.getEmail().equals(updatePasswordDto.getEmail()))
				{
					
					Session session = entityManager.unwrap(Session.class);
					Query<?> query = session.createQuery("update User set password=:password , update_date_time=:date"+" where user_id =:userId ");
					query.setParameter("password", bcryptPasswordEncoder.encode(updatePasswordDto.getConfirmPassword()));
					query.setParameter("userId", userId);
					query.setParameter("date", LocalDateTime.now());
					return query.executeUpdate()==1;		
				}
				
				return false;
				
			}catch(Exception e)
			{
				throw new UserNotFoundException("User Not found!!!");	
			}
		}
		else
			throw new UserException("Invalid credential!!");



	}
	/**
	 * 
	 * @param email_id to check user
	 * @return true if user exist
	 */
	@Transactional
	@Override
	public boolean isMailExist(String email_id)
	{
		Session session = entityManager.unwrap(Session.class);
		Query<?> query = session.createQuery("from User where email=:email_id");
		query.setParameter("email_id", email_id);
		return query.uniqueResult()!=null;

	}

	/**
	 * 
	 * @param email_id to find user by email
	 * @return optional user if found
	 */
	@Transactional
	public User findByMail(String email_id)
	{
		Session session = entityManager.unwrap(Session.class);
		Query<?> query = session.createQuery("from User where email=:email_id");
		query.setParameter("email_id", email_id);
		log.info("user"+query.uniqueResult());
		return (User) query.uniqueResult();
	}

	@Transactional
	public User findById(long id)
	{
		Session session = entityManager.unwrap(Session.class);
		Query<?> query = session.createQuery("from User where user_id=:user_id");
		query.setParameter("user_id", id);
		return (User) query.uniqueResult();
		
	}
	/**
	 * @param emailId to check user is present or not
	 * @return true if user present
	 */
	@Override
	public boolean confirmMail(String emailId) {

		User user = findByMail(emailId);
		if(user!=null)
		{
			emailModel.setMessage(EmailUtil.createLink("http://localhost:8080/users/updatePassword/", jwtUtil.generateToken(user.getId())));
			emailModel.setEmail(emailId);
			emailModel.setSubject("click link to get new password");
			EmailUtil.sendAttachmentEmail(emailModel.getEmail(), emailModel.getSubject(), emailModel.getMessage());
			return true;
		}
		
		throw new UserException("user not found");
	}
	/**
	 * @param token to find user
	 * @return true if user present
	 */
	@Transactional
	@Override
	public boolean updateMailVerification(String token) {
		long id = jwtUtil.parseToken(token); 
		if(id!=0)
		{
			Session session = entityManager.unwrap(Session.class);
			Query<?> query = session.createQuery("update User set is_verified =:verified "+" where user_id =:u_id");
			query.setParameter("verified", true);
			query.setParameter("u_id" , id);
			query.executeUpdate();
			return true;
		}
		return false;

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
	public User getUser(String token) {

		long userId = jwtUtil.parseToken(token);
		User user = findById(userId);
		return user;

	}






}
