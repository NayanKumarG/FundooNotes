/**
 * @author Nayan Kumar G
 * purpose :Controller for Notes
 * date    :04-04-2020
 */
package com.bridgelabz.fundoonotes.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.NoteUpdateDto;
import com.bridgelabz.fundoonotes.entity.NoteEntity;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.NoteService;

@RestController
public class NoteController {

	@Autowired
	private NoteService noteService;
	

	/**
	 * Api to create note
	 * @param noteDto to map with request object
	 * @param token to get user id
	 * 
	 */
	@PostMapping("/notes/create")
	public ResponseEntity<Response> createNotes(@RequestBody NoteDto noteDto , @RequestHeader String token)
	{
		noteService.createNote(noteDto , token);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Note created ", noteDto));

	}

	/**
	 * Api to delete note
	 * @param noteId to delete perticular note
	 * @param token to get user detail
	 * 
	 */
	@DeleteMapping("/notes/delete/{noteId}")
	public ResponseEntity<Response> deleteNotes(@PathVariable long noteId , @RequestHeader String token)
	{
		noteService.deleteNote(noteId , token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("note deleted saved into trash"));
	}
	
	/**
	 * Api to delete note permanently
	 * @param noteId to delete note
	 * @param token to get user id
	 * 
	 */
	@DeleteMapping("/notes/deletePermanently/{noteId}")
	public ResponseEntity<Response> deleteNoteParmanently(@PathVariable long noteId , @RequestHeader String token)
	{
		noteService.deleteNotePermanently(noteId , token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("note deleted success"));
	}
	
	/**
	 * Api to pin the note
	 * @param noteId to pin the note
	 * @param token to get user id
	 * 
	 */
	@PostMapping("/notes/pin/{noteId}")
	public ResponseEntity<Response> pinNote(@PathVariable long noteId , @RequestHeader String token)
	{
		noteService.pinOrUnpinNote(noteId , token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response(" pin note or unpin",200));
	}
	
	/**
	 * Api to archieve the note
	 * @param noteId to archieve note
	 * @param token to get user id
	 * 
	 */
	@PostMapping("/notes/archieve/{noteId}")
	public ResponseEntity<Response> archieve(@PathVariable long noteId , @RequestHeader String token)
	{
		noteService.archieveNote(noteId , token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response(" archieve note",200));
	}
	
	/**
	 * Api to update note
	 * @param noteUpdateDto to bind the request object
	 * @param token to get user id
	 * 
	 */
	@PutMapping("/notes/update")
	public ResponseEntity<Response> updateNote(@RequestBody NoteUpdateDto noteUpdateDto , @RequestHeader String token)
	{
		noteService.updateNote(noteUpdateDto , token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("update success"));
	}
	
	/**
	 * Api to get all notes
	 * @param token to identify user
	 * @return list of notes
	 */
	@GetMapping("/notes/getNotes")
	public ResponseEntity<Response> getAllNotes(@RequestHeader String token)
	{
		List<NoteEntity> notes = noteService.fetchAllNotes(token);
		
		if(notes.size()>0)
			
		return ResponseEntity.status(HttpStatus.OK).body(new Response("fetched all notes",notes));
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("notes not found!! create note"));
	}
	

	/**
	 * Api to get trashed notes
	 * @param token to identify user
	 * @return list of trashed notes if found
	 */
	@GetMapping("/notes/trashedNotes")
	public ResponseEntity<Response> getTrashedNotes(@RequestHeader String token)
	{
		List<NoteEntity> notes = noteService.fetchTrashedNote(token);
		if(notes.size()>0)
			
		return ResponseEntity.status(HttpStatus.OK).body(new Response("fetched trashed note",notes));
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Trashed note not found"));
	}
	
	/**
	 * 
	 * @param token
	 * @return list of notes
	 */
	@GetMapping("/notes/archievedNotes")
	public ResponseEntity<Response> getArchievedNotes(@RequestHeader String token)
	{
		List<NoteEntity> notes = noteService.fetchArchievedNotes(token);
		if(notes.size()>0)
			
			return ResponseEntity.status(HttpStatus.OK).body(new Response("fetched archieved note",notes));
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("archieved note not found"));
	}
	
	
	

}
