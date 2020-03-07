/**
 * @author Nayan Kumar G
 * purpose :Service implementation of Note
 * date    :03-03-2020
 */
package com.bridgelabz.fundoonotes.service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.NoteReminderDto;
import com.bridgelabz.fundoonotes.dto.NoteUpdateDto;
import com.bridgelabz.fundoonotes.entity.NoteEntity;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.exception.InvalidTokenException;
import com.bridgelabz.fundoonotes.exception.NoteNotFoundException;
import com.bridgelabz.fundoonotes.exception.UserNotFoundException;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.utility.JwtUtil;

@Service
public class NoteServiceImpl implements NoteService{

	private NoteEntity noteEntity = new NoteEntity();

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserServiceImpl userService;


	/**
	 * provides service to create note
	 * @param noteDto to store the data , token to identify user
	 * 
	 */
	@Override
	public boolean createNote(NoteDto noteDto, String token) {

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
				BeanUtils.copyProperties(noteDto, noteEntity);
				noteEntity.setCreatedDate(LocalDateTime.now());
				noteEntity.setArchieved(false);
				noteEntity.setTrashed(false);
				noteEntity.setPinned(false);
				noteEntity.setColor("white");
				user.getNotes().add(noteEntity);
				noteRepository.saveOrUpdate(noteEntity);
				return true;
			}
			else

				throw new UserNotFoundException("user not found");

	}

	/**
	 * provides service to update note
	 * @param noteUpdateDto to store the modified data , token to identify user
	 * 
	 */
	@Override
	public boolean updateNote(NoteUpdateDto noteUpdateDto, String token) {
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
				NoteEntity note = noteRepository.fetchById(noteUpdateDto.getId());
				if(note!=null)
				{
					note.setTitle(noteUpdateDto.getTitle());
					note.setDescription(noteUpdateDto.getDescription());
					note.setUpdateDate(LocalDateTime.now());
					noteRepository.saveOrUpdate(note);
					return true;
				}
				else

					throw  new NoteNotFoundException("Note Not Found");
			}
			else

				throw  new UserNotFoundException("user Not Found");



	}

	/**
	 * provides service to delete note
	 * @param noteDto to store the data , token to identify user 
	 * 
	 */
	@Override
	public boolean deleteNote(long noteId, String token) {
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
				note.setTrashed(!note.isTrashed());
				noteRepository.saveOrUpdate(note);
				return true;
			}
			else

				throw  new UserNotFoundException("user Not Found");	

	}

	/**
	 * provides service to delete  note permanently
	 * @param noteId to delete note permanently, token to identify user
	 * 
	 */
	@Override
	public boolean deleteNotePermanently(long noteId, String token) {
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
				if(note!=null)
				{
					noteRepository.deleteNote(noteId);
					return true;

				}
				else
					throw  new NoteNotFoundException("Note Not Found");
			}
			else
				throw  new UserNotFoundException("user Not Found");	
		}
	

	/**
	 *provides service to pin note
	 * @param noteId to pin or unpin the note ,  token to identify user
	 * 
	 */
	@Override
	public boolean pinOrUnpinNote(long noteId, String token) {
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
				note.setPinned(!note.isPinned());
				noteRepository.saveOrUpdate(note);
				return true;
			}

			else

				throw  new UserNotFoundException("user Not Found");	


	}

	/**
	 * provides service to archive note
	 * @param noteId to archieve ,  token to identify user
	 * 
	 */
	@Override
	public boolean archieveNote(long noteId, String token) {
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
				note.setArchieved(!note.isArchieved());
				noteRepository.saveOrUpdate(note);
				return true;
			}
			else

				throw  new UserNotFoundException("user Not Found");

	}

	/**
	 * provides service to get perticular note
	 * @param noteId to get note
	 * 
	 */
	@Override
	public NoteEntity getNote(long note_id)
	{
		return noteRepository.fetchById(note_id);
	}

	/**
	 * provides service to get list of notes
	 * @param token to identify user
	 * 
	 */
	@Override
	public List<NoteEntity> fetchAllNotes(String token) {
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
				List<NoteEntity> notes = noteRepository.getNotes(userId);
				notes.sort((NoteEntity note1 , NoteEntity note2)->note1.getTitle().compareTo(note2.getTitle()));            
				return notes;

			}

			throw  new UserNotFoundException("user Not Found");


	}

	/**
	 * provides service to get trashed notes
	 * @param token to identify user
	 */
	@Override
	public List<NoteEntity> fetchTrashedNote(String token) {
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
				

				List<NoteEntity> notes = noteRepository.getTrashedNotes(userId);
				notes.stream().filter(note->note.isTrashed()==true).collect(Collectors.toList());
				return notes;
				
				//return noteRepository.getTrashedNotes(userId);

			}
			throw  new UserNotFoundException("user Not Found");


	}

	/**
	 * provides service to fetch all archieved note 
	 * @param token to identify user
	 */
	@Override
	public List<NoteEntity> fetchArchievedNotes(String token) {
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
				return noteRepository.getArchievedNotes(userId);

			}

			throw  new UserNotFoundException("user Not Found");


	}

	/**
	 * provides service to fetch all pinned notes
	 * @param token to identify user
	 */
	@Override
	public List<NoteEntity> fetchPinnedNotes(String token) {
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
				return noteRepository.getpinnedNotes(userId);

			}

			throw  new UserNotFoundException("user Not Found");


	}

	/**
	 * provides service to add reminder to note
	 * @param noteReminderDto to get reminder data
	 * @param token to get user id
	 * @param noteId to get note to add reminder
	 */
	@Override
	public boolean addReminder(NoteReminderDto noteReminderDto , String token, long noteId) {
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
				LocalDateTime reminderDate = noteReminderDto.getReminder();
				NoteEntity noteEntity = noteRepository.fetchById(noteId);
				noteEntity.setReminder(reminderDate);
				noteRepository.saveOrUpdate(noteEntity);
				return true;
			}

			else

				throw  new UserNotFoundException("user Not Found");


	}

	/**
	 * provides service to delete added reminder
	 * @param token to get user detail
	 * @param noteId to get note to delete reminder
	 */
	@Override
	public boolean deleteNoteReminder(String token, long noteId) {
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
				NoteEntity noteEntity = noteRepository.fetchById(noteId);
				noteEntity.setReminder(null);
				noteRepository.saveOrUpdate(noteEntity);
				return true;
			}

			else

				throw  new UserNotFoundException("user Not Found");


	}

	/**
	 * provides service to add color to the note
	 * @param noteId to add color
	 * @param color to change color
	 */
	@Override
	public boolean addNoteColor(long noteId, String color, String token) {
		try
		{
			User user = userService.findById(jwtUtil.parseToken(token));
			if(user!=null)
			{
				NoteEntity noteEntity = noteRepository.fetchById(noteId);
				noteEntity.setColor(color);
				noteRepository.saveOrUpdate(noteEntity);
				return true;

			}
			else
				throw  new UserNotFoundException("user Not Found");
		}catch(Exception e)
		{
			throw new InvalidTokenException("invalid token");
		}
	}
}
