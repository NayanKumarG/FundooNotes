/**
 * @author Nayan Kumar G
 * purpose : Provide services of Note
 * date    :03-03-2020
 */
package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.NoteReminderDto;
import com.bridgelabz.fundoonotes.dto.NoteUpdateDto;
import com.bridgelabz.fundoonotes.entity.NoteEntity;

public interface NoteService {

	boolean createNote(NoteDto noteDto, String token);

	boolean deleteNote(long noteId, String token);

	boolean deleteNotePermanently(long noteId, String token);

	boolean pinOrUnpinNote(long noteId, String token);

	boolean archieveNote(long noteId, String token);

	boolean updateNote(NoteUpdateDto noteUpdateDto, String token);

	List<NoteEntity> fetchAllNotes(String token);

	List<NoteEntity> fetchTrashedNote(String token);

	List<NoteEntity> fetchArchievedNotes(String token);

	List<NoteEntity> fetchPinnedNotes(String token);

	boolean addReminder(NoteReminderDto noteReminderDto, String token, long noteId);

	NoteEntity getNote(long note_id);

	boolean deleteNoteReminder(String token, long noteId);

	boolean addNoteColor(long noteId, String color, String token);

	
}
