/**
 * @author Nayan Kumar G
 * purpose : Provide services for User
 * date    :25-02-2020
 */

package com.bridgelabz.fundoonotes.service;
import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.UpdatePasswordDto;
import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.dto.UserLoginDto;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.exception.InvalidUserCredentialException;
import com.bridgelabz.fundoonotes.exception.UserAlreadyExistException;
import com.bridgelabz.fundoonotes.exception.UserNotFoundException;
import com.bridgelabz.fundoonotes.repository.UserRepository;
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
	private EmailModel emailModel;
	
	@Autowired
	private UserRepository userRepository;


	private User user = new User();

	/**
	 * method to register for user
	 */
	@Transactional
	@Override
	public User addUser(UserDto userDto) {


		if(!isMailExist(userDto.getEmail()))
		{
			BeanUtils.copyProperties(userDto , user);
			user.setDateTime(LocalDateTime.now());
			
			user.setPassword(bcryptPasswordEncoder.encode((userDto.getPassword())));
			user.setVerified(false);
			userRepository.save(user);
			emailModel.setMessage(EmailUtil.createLink("http://localhost:8080/users/verifyMail/", jwtUtil.generateToken(user.getId())));
			emailModel.setEmail(userDto.getEmail());
			emailModel.setSubject("Click link to Verify ");
			EmailUtil.sendAttachmentEmail(emailModel.getEmail(), emailModel.getSubject(), emailModel.getMessage());
			return user;
		}
		else

			throw new UserAlreadyExistException("User already exist with given mail:"+userDto.getEmail() , HttpStatus.NOT_FOUND );


	}

	/**
	 * @param userLoginDto object for login
	 * @return true if password match
	 */
	@Override
	public boolean verifyLogin(UserLoginDto userLoginDto) {
		String mail = userLoginDto.getEmail();
		if(userRepository.findByMail(mail)!=null)
		{
		User user = userRepository.findByMail(mail);
			if(user.isVerified()==true && bcryptPasswordEncoder.matches(userLoginDto.getPassword(), user.getPassword()))
				return true;

		}
		throw new UserNotFoundException("user not found",HttpStatus.NOT_FOUND);
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
				long userId = jwtUtil.parseToken(token);
				log.info("my id:"+userId);
				User user = userRepository.findById(userId);
				if(user!=null && user.getEmail().equals(updatePasswordDto.getEmail()))
				{
					String password = bcryptPasswordEncoder.encode(updatePasswordDto.getConfirmPassword());
					return userRepository.updatePassword(password , userId);
		
				}
				else
				throw new UserNotFoundException("User Not found!!!",HttpStatus.NOT_FOUND);
				
			}
		
		else
			throw new InvalidUserCredentialException("Invalid credential!!" , HttpStatus.BAD_REQUEST);
		
	

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
		
		
		return userRepository.isMailExist(email_id);


	}

	/**
	 * @param emailId to check user is present or not
	 * @return true if user present
	 */
	@Override
	public boolean confirmMail(String emailId) {

		User user = userRepository.findByMail(emailId);
		if(user!=null)
		{
			emailModel.setMessage(EmailUtil.createLink("http://localhost:8080/users/updatePassword/", jwtUtil.generateToken(user.getId())));
			emailModel.setEmail(emailId);
			emailModel.setSubject("click link to get new password");
			EmailUtil.sendAttachmentEmail(emailModel.getEmail(), emailModel.getSubject(), emailModel.getMessage());
			return true;
		}
		
		throw new UserNotFoundException("user not found",HttpStatus.NOT_FOUND);
	}
	/**
	 * @param token to find user
	 * @return true if user present
	 */
	@Transactional
	@Override
	public boolean updateMailVerification(String token) {
		long id = jwtUtil.parseToken(token); 
		User user = userRepository.findById(id);
		if(user!=null)
		{
		
			return userRepository.updateMailVerification(token, id);

		}
		throw new UserNotFoundException("user not found",HttpStatus.NOT_FOUND);

	}
	/**
	 * Get the list of users
	 */
	@Transactional
	@Override
	public List<User> getUsers() {
		
		List<User> users = userRepository.getUsers();
		users.sort((User user1 , User user2)->user1.getName().compareTo(user2.getName()));
		return users;
	}   

	/**
	 * get the user detail by userId.
	 */
	@Transactional
	@Override
	public User getUser(String token) {

		long userId = jwtUtil.parseToken(token);
		log.info("My id:"+userId);
		User user = userRepository.findById(userId);
		if(user==null)
		{
		return user;
		}
		else
		throw new UserNotFoundException("user Not found",HttpStatus.NOT_FOUND);

	}



}
