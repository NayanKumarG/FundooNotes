/**
 * @author Nayan Kumar G
 * purpose : Provide services of Note
 * date    :03-03-2020
 */
package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.NoteUpdateDto;
import com.bridgelabz.fundoonotes.entity.NoteEntity;

public interface NoteService {

	void createNote(NoteDto noteDto, String token);

	void deleteNote(long noteId, String token);

	void deleteNotePermanently(long noteId, String token);

	void pinOrUnpinNote(long noteId, String token);

	void archieveNote(long noteId, String token);

	void updateNote(NoteUpdateDto noteUpdateDto, String token);

	List<NoteEntity> fetchAllNotes(String token);

	List<NoteEntity> fetchTrashedNote(String token);

	List<NoteEntity> fetchArchievedNotes(String token);

	
}
