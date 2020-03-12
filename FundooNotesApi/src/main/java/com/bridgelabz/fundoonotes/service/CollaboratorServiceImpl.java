/**
 * @author Nayan Kumar G
 * purpose :service implementation to collaborator
 * date    :12-03-2020
 */
package com.bridgelabz.fundoonotes.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.entity.NoteEntity;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.exception.MailNotFoundException;
import com.bridgelabz.fundoonotes.exception.UserAlreadyExistException;
import com.bridgelabz.fundoonotes.exception.UserNotFoundException;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.utility.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CollaboratorServiceImpl implements CollaboratorService {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private NoteRepository noteRepository;

	/**
	 * provide service to add collaborator
	 */
	@Override
	@Transactional
	public NoteEntity addCollaborator(long noteId, String email, String token) {

		long userId = jwtUtil.parseToken(token);

		User user = userRepository.findById(userId);
		if(user!=null)
		{

			User collaborator = userRepository.findByMail(email);
			if(collaborator!=null)
			{

				NoteEntity note = noteRepository.fetchById(noteId);

				if(note.getCollaborators().contains(collaborator)!=true)
				{
					collaborator.getCollaboratorNotes().add(note);
					return note;
				}
				else

					throw new UserAlreadyExistException("User already collaborated", HttpStatus.ALREADY_REPORTED);
			}
			else
				throw new MailNotFoundException("User not found with mail:"+email, HttpStatus.NOT_FOUND);

		}
		else
			throw new UserNotFoundException("user Not Found", HttpStatus.NOT_FOUND);

	}

	/**
	 * provide service to delete collaborator
	 */
	@Override
	@Transactional
	public NoteEntity deleteCollaborator(long noteId, String email, String token) {

		long userId = jwtUtil.parseToken(token);

		User user = userRepository.findById(userId);

		if(user!=null)
		{
			User collaborator = userRepository.findByMail(email);
			if(collaborator!=null)
			{
				NoteEntity note = noteRepository.fetchById(noteId);
				note.getCollaborators().remove(collaborator);
				return note;
			}
			else
				throw new MailNotFoundException("user not found with mail:"+email, HttpStatus.NOT_FOUND);

		}
		else

			throw new UserNotFoundException("user not found", HttpStatus.NOT_FOUND);

	}

	/**
	 * provide service to get collaborated notes
	 */
	@Override
	@Transactional
	public List<NoteEntity> getCollaboratorNotes(String token) {

		long userId = jwtUtil.parseToken(token);

		User user = userRepository.findById(userId);

		if(user!=null)
		{

			List<NoteEntity> collaboratorNotes = user.getCollaboratorNotes();
			log.info("notes:"+collaboratorNotes);
			return collaboratorNotes;
		}
		else
			throw new UserNotFoundException("user not found", HttpStatus.NOT_FOUND);

	}
}
