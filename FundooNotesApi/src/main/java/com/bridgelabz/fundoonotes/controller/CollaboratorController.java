/**
 * @author Nayan Kumar G
 * purpose : Controller to collaborater
 * date    :12-03-2020
 */
package com.bridgelabz.fundoonotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.entity.NoteEntity;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.CollaboratorService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
public class CollaboratorController {

	@Autowired
	private CollaboratorService collaboratorService;

	/**
	 * Api to add collaborator to note
	 * @param noteId to get note
	 * @param email to collaborate
	 * @param token to identify user
	 * 
	 */
	@PostMapping("/collaborators/addCollaborator")
	public ResponseEntity<Response> addCollaborator(@RequestParam long noteId , @RequestParam String email , @RequestHeader String token)
	{
		NoteEntity collaboratedNote = collaboratorService.addCollaborator(noteId , email , token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Collaborator added" , collaboratedNote));
	}

	/**
	 * Api to delete collaborator
	 * @param noteId to get note
	 * @param email to identify collaborator
	 * @param token to identify user
	 * 
	 */
	@DeleteMapping("/collaborators/deleteCollaborator")
	public ResponseEntity<Response> deleteCollaborator(@RequestParam long noteId , @RequestParam String email , @RequestHeader String token)
	{
		NoteEntity collaboratedNote = collaboratorService.deleteCollaborator(noteId , email , token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Collaborator deleted" , collaboratedNote));
	}
	
	/**
	 * Api to get collaborater notes
	 * @param token to identify user
	 * 
	 */
	@GetMapping("/collaborators/getCollaboratorNotes")
	public ResponseEntity<Response> getCollaboratorNotes(@RequestHeader String token)
	{
		List<NoteEntity> collaboratorNotes = collaboratorService.getCollaboratorNotes(token);
		
		return ResponseEntity.status(HttpStatus.OK).body(new Response("collaborator notes fetched" , collaboratorNotes));
		
	}


}
