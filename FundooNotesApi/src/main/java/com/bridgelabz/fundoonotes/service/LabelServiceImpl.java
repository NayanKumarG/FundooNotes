/**
 * @author Nayan Kumar G
 * purpose :Service implementation for label
 * date    :06-03-2020
 */
package com.bridgelabz.fundoonotes.service;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.dto.LabelUpdateDto;
import com.bridgelabz.fundoonotes.entity.LabelEntity;
import com.bridgelabz.fundoonotes.entity.NoteEntity;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.exception.InvalidTokenException;
import com.bridgelabz.fundoonotes.exception.LabelAlreadyExistException;
import com.bridgelabz.fundoonotes.exception.LabelNotFoundException;
import com.bridgelabz.fundoonotes.exception.UserNotFoundException;
import com.bridgelabz.fundoonotes.repository.LabelRepository;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
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
	
	@Autowired
	private NoteRepository noteRepository;


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
				throw new LabelAlreadyExistException("label already present!!");
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

	/**
	 * provide service to delete label
	 * @param labelId to delete label
	 * @param token to identify user
	 */
	@Override
	@Transactional
	public void deleteLabel(long labelId, String token) {

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

			LabelEntity label = labelRepository.findById(labelId);

			if(label!=null)
			{
				labelRepository.delete(label);
			}			
			else
				throw new LabelNotFoundException("label not found");
		}
		else
			throw new UserNotFoundException("user Not found");
	}

	/**
	 * provides service to remove label from note
	 */
	@Override
	@Transactional
	public void removeLabel(long labelId, long noteId, String token) {
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
			NoteEntity note = noteRepository.fetchById(noteId);
			LabelEntity label =labelRepository.findById(labelId);
			note.getLabels().remove(label);
			noteRepository.saveOrUpdate(note);
		
	}else
		throw new UserNotFoundException("user Not found");
}

	/**
	 * provide services to add label to the notes
	 */
	@Override
	@Transactional
	public void addLabel(long labelId, long noteId, String token) {
		long userId = 0;
		try
		{
			userId = jwtUtil.parseToken(token);
		}catch(Exception e)
		{
			throw new InvalidTokenException("token is not valid");
		}
		
		User user = userService.findById(userId);
		if(user!=null)
		{
		
		NoteEntity note = noteRepository.fetchById(noteId);
		LabelEntity label = labelRepository.findById(labelId);
		label.getNotes().add(note);
		labelRepository.save(label);
		
		}
		else
			throw new UserNotFoundException("user not exist");
	}

	/**
	 * provides service to get labels
	 * @return list of labels
	 */
	@Override
	@Transactional
	public List<LabelEntity> getLabels(String token) {
		long userId = 0 ;
		try
		{
			userId = jwtUtil.parseToken(token);
		}catch(Exception e)
		{
			throw new InvalidTokenException("token invalid");
		}
		
		User user = userService.findById(userId);
		if(user!=null)
		{
			return labelRepository.fetchAllLabels(userId);
			
			
		}else
			throw new UserNotFoundException("user not Exist");
	}

	/**
	 * provides services to get notes by label
	 * @return list of notes fetched
	 */
	@Override
	@Transactional
	public List<NoteEntity> getNotesByLabel(long labelId, String token) {
		long userId = 0 ;
		try
		{
			userId = jwtUtil.parseToken(token);
		}catch(Exception e)
		{
			throw new InvalidTokenException("token invalid");
		}
		
		User user = userService.findById(userId);
		if(user!=null)
		{
			LabelEntity label = labelRepository.findById(labelId);
		
			 return label.getNotes();
		
	}else
		throw new UserNotFoundException("user not Exist");
	}
}



