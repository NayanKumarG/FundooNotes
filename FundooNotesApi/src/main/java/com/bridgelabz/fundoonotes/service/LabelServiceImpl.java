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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.dto.LabelUpdateDto;
import com.bridgelabz.fundoonotes.entity.LabelEntity;
import com.bridgelabz.fundoonotes.entity.NoteEntity;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.exception.LabelAlreadyExistException;
import com.bridgelabz.fundoonotes.exception.LabelNotFoundException;
import com.bridgelabz.fundoonotes.exception.UserNotFoundException;
import com.bridgelabz.fundoonotes.repository.LabelRepository;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.utility.JwtUtil;

@Service
public class LabelServiceImpl implements LabelService{

	private LabelEntity labelEntity = new LabelEntity();

	@Autowired
	private JwtUtil jwtUtil ; 

	@Autowired
	private UserRepository userService;

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
		long userId = jwtUtil.parseToken(token);
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
				throw new LabelAlreadyExistException("label already present!!" , HttpStatus.FOUND);
		}
		else
			throw new UserNotFoundException("user not found!!",HttpStatus.NOT_FOUND);


	}

	/**
	 * provide service to update label
	 * @param labelUpdateDto to get label detail
	 */
	@Override
	@Transactional
	public void updateLabel(LabelUpdateDto labelUpdateDto, String token) {
		long userId = jwtUtil.parseToken(token);
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
				throw new LabelNotFoundException("label not found" , HttpStatus.NOT_FOUND);
		}
		else
			throw new UserNotFoundException("user Not found",HttpStatus.NOT_FOUND);
	}

	/**
	 * provide service to delete label
	 * @param labelId to delete label
	 * @param token to identify user
	 */
	@Override
	@Transactional
	public void deleteLabel(long labelId, String token) {
		long userId = jwtUtil.parseToken(token);
		User user = userService.findById(userId);

		if(user!=null)
		{

			LabelEntity label = labelRepository.findById(labelId);

			if(label!=null)
			{
				labelRepository.delete(label);
			}			
			else
				throw new LabelNotFoundException("label not found" , HttpStatus.NOT_FOUND);
		}
		else
			throw new UserNotFoundException("user Not found",HttpStatus.NOT_FOUND);
	}

	/**
	 * provides service to remove label from note
	 */
	@Override
	@Transactional
	public void removeLabel(long labelId, long noteId, String token) {
		long userId = jwtUtil.parseToken(token);
		User user = userService.findById(userId);

		if(user!=null)
		{
			NoteEntity note = noteRepository.fetchById(noteId);
			LabelEntity label =labelRepository.findById(labelId);
			note.getLabels().remove(label);
			noteRepository.saveOrUpdate(note);
		
	}else
		throw new UserNotFoundException("user Not found",HttpStatus.NOT_FOUND);
}

	/**
	 * provide services to add label to the notes
	 */
	@Override
	@Transactional
	public void addLabel(long labelId, long noteId, String token) {
		long userId = jwtUtil.parseToken(token);
		
		User user = userService.findById(userId);
		if(user!=null)
		{
		
		NoteEntity note = noteRepository.fetchById(noteId);
		LabelEntity label = labelRepository.findById(labelId);
		label.getNotes().add(note);
		labelRepository.save(label);
		
		}
		else
			throw new UserNotFoundException("user not exist",HttpStatus.NOT_FOUND);
	}

	/**
	 * provides service to get labels
	 * @return list of labels
	 */
	@Override
	@Transactional
	public List<LabelEntity> getLabels(String token) {
		long userId = jwtUtil.parseToken(token);
		
		User user = userService.findById(userId);
		if(user!=null)
		{
			return labelRepository.fetchAllLabels(userId);
			
			
		}else
			throw new UserNotFoundException("user not Exist",HttpStatus.NOT_FOUND);
	}

	/**
	 * provides services to get notes by label
	 * @return list of notes fetched
	 */
	@Override
	@Transactional
	public List<NoteEntity> getNotesByLabel(long labelId, String token) {
		
		long userId = jwtUtil.parseToken(token);
		User user = userService.findById(userId);
		if(user!=null)
		{
			LabelEntity label = labelRepository.findById(labelId);
		
			 return label.getNotes();
		
	}else
		throw new UserNotFoundException("user not Exist",HttpStatus.NOT_FOUND);
	}
}



