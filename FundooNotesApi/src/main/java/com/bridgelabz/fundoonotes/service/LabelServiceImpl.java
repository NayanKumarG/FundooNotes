/**
 * @author Nayan Kumar G
 * purpose :Service implementation for label
 * date    :06-03-2020
 */
package com.bridgelabz.fundoonotes.service;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.dto.LabelUpdateDto;
import com.bridgelabz.fundoonotes.entity.LabelEntity;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.exception.InvalidTokenException;
import com.bridgelabz.fundoonotes.exception.LabelException;
import com.bridgelabz.fundoonotes.exception.LabelNotFoundException;
import com.bridgelabz.fundoonotes.exception.UserNotFoundException;
import com.bridgelabz.fundoonotes.repository.LabelRepository;
import com.bridgelabz.fundoonotes.utility.JwtUtil;

@Service
public class LabelServiceImpl implements LabelService{
	
	private LabelEntity labelEntity = new LabelEntity();

	@Autowired
	private JwtUtil jwtUtil ; 
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private LabelRepository labelRepository;
	
	
	/**
	 * provide service to create label
	 * @param labelDto to get label detail
	 */
	@Override
	@Transactional
	public void createLabel(LabelDto labelDto, String token) {
		long userId = 0;
		try
		{
		userId = jwtUtil.parseToken(token);
		}catch(Exception e)
		{
			throw new InvalidTokenException("invalid token");
		}
		User user = userService.findById(userId);
		
		if(user!=null)
		{
			
			LabelEntity label = labelRepository.fetchLabel(userId, labelDto.getLabelName());
			if(label==null)
			{
			BeanUtils.copyProperties(labelDto , labelEntity);
			labelEntity.setUserId(userId);
			labelRepository.save(labelEntity);
			}
			else
				throw new LabelException("label already present!!");
		}
		else
			throw new UserNotFoundException("user not found!!");
		

	}

	/**
	 * provide service to update label
	 * @param labelUpdateDto to get label detail
	 */
	@Override
	@Transactional
	public void updateLabel(LabelUpdateDto labelUpdateDto, String token) {
		long userId = 0 ;
		try
		{
		userId = jwtUtil.parseToken(token);
		}catch(Exception e)
		{
			throw new InvalidTokenException("invalid token");
		}
		User user = userService.findById(userId);
		
		if(user!=null)
		{
			LabelEntity label = labelRepository.findById(labelUpdateDto.getLabelId());
			
			if(label!=null)
			{
				label.setLabelName(labelUpdateDto.getLabelName());
				labelRepository.save(label);
			}
			else
				throw new LabelNotFoundException("label not found");
		}
		else
			throw new UserNotFoundException("user Not found");
		}
		

}
