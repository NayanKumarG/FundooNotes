/**
 * @author Nayan Kumar G
 * purpose :Service implementation of Note
 * date    :03-03-2020
 */
package com.bridgelabz.fundoonotes.service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.NoteUpdateDto;
import com.bridgelabz.fundoonotes.entity.NoteEntity;
import com.bridgelabz.fundoonotes.repository.NoteRepository;

@Service
public class NoteServiceImpl implements NoteService{

	private NoteEntity noteEntity = new NoteEntity();

	@Autowired
	private NoteRepository noteRepository;


	/**
	 * provides service to create note
	 * @param noteDto to store the data , token to identify user
	 * 
	 */
	@Override
	@Transactional
	public void createNote(NoteDto noteDto, String token) {

		BeanUtils.copyProperties(noteDto, noteEntity);
		noteEntity.setCreatedDate(LocalDateTime.now());
		noteEntity.setArchieved(false);
		noteEntity.setTrashed(false);
		noteEntity.setPinned(false);
		noteRepository.saveOrUpdate(noteEntity);


	}

	/**
	 * provides service to update note
	 * @param noteUpdateDto to store the modified data , token to identify user
	 * 
	 */
	@Override
	@Transactional
	public void updateNote(NoteUpdateDto noteUpdateDto, String token) {

		NoteEntity note = noteRepository.fetchById(noteUpdateDto.getId());
		if(note!=null)
		{
			note.setTitle(noteUpdateDto.getTitle());
			note.setDescription(noteUpdateDto.getDescription());
			note.setUpdateDate(LocalDateTime.now());
			noteRepository.saveOrUpdate(note);
		}

	}

	/**
	 * provides service to delete note
	 * @param noteDto to store the data , token to identify user 
	 * 
	 */
	@Override
	@Transactional
	public void deleteNote(long noteId, String token) {
		//Optional<NoteEntity> note = noteRepository.findById(noteId);
		//note.get().setTrashed(true);
		NoteEntity note = noteRepository.fetchById(noteId);
		note.setTrashed(!note.isTrashed());
		noteRepository.saveOrUpdate(note);

	}

	/**
	 * provides service to delete  note permanently
	 * @param noteId to delete note permanently, token to identify user
	 * 
	 */
	@Override
	@Transactional
	public void deleteNotePermanently(long noteId, String token) {
		NoteEntity note = noteRepository.fetchById(noteId);
		if(note!=null)
		{
			noteRepository.deleteNote(noteId);

		}
	}

	/**
	 *provides service to pin note
	 * @param noteId to pin or unpin the note ,  token to identify user
	 * 
	 */
	@Override
	@Transactional
	public void pinOrUnpinNote(long noteId, String token) {
		NoteEntity note = noteRepository.fetchById(noteId);
		note.setPinned(!note.isPinned());
		noteRepository.saveOrUpdate(note);

	}

	/**
	 * provides service to archive note
	 * @param noteId to archieve ,  token to identify user
	 * 
	 */
	@Override
	@Transactional
	public void archieveNote(long noteId, String token) {
		NoteEntity note = noteRepository.fetchById(noteId);
		note.setArchieved(!note.isArchieved());
		noteRepository.saveOrUpdate(note);
	}

	/**
	 * provides service to get perticular note
	 * @param noteId to get note
	 * 
	 */
	@Transactional
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
		
		return noteRepository.getNotes();
		
	}

	/**
	 * provides service to get trashed notes
	 * @param token to identify user
	 */
	@Override
	public List<NoteEntity> fetchTrashedNote(String token) {
		
		
		return noteRepository.getTrashedNotes();
	}

	@Override
	public List<NoteEntity> fetchArchievedNotes(String token) {
		
		return noteRepository.getArchievedNotes();
	}


}
