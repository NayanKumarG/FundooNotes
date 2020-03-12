/**
 * @author Nayan Kumar G
 * purpose :Controller for label
 * date    :06-03-2020
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.dto.LabelUpdateDto;
import com.bridgelabz.fundoonotes.entity.LabelEntity;
import com.bridgelabz.fundoonotes.entity.NoteEntity;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.LabelService;

@RestController
public class LabelController {

	@Autowired
	private LabelService labelService;
	
	/**
	 * Api to create label
	 * @param labelDto to hold request object
	 * @param token to identify user
	 * 
	 */
	@PostMapping("labels/create")
	public ResponseEntity<Response>  createLabel(@RequestBody LabelDto labelDto , @RequestHeader String token)
	{
		labelService.createLabel(labelDto , token);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Label created" , labelDto));
	}
	
	/**
	 * Api to update label
	 * @param labelUpdateDto to hold updating detail
	 * @param token to identify user
	 * 
	 */
	@PutMapping("/labels/update")
	public ResponseEntity<Response> updateLabel(@RequestBody LabelUpdateDto labelUpdateDto , @RequestHeader String token)
	{
		labelService.updateLabel(labelUpdateDto , token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Label created" , labelUpdateDto));
	}
	
	/**
	 * Api to delete label
	 * @param labelId to delete particular label
	 * @param token to identify user
	 * 
	 */
	@DeleteMapping("/labels/delete")
	public ResponseEntity<Response> deleteLabel(@RequestParam long labelId , @RequestHeader String token)
	{

		labelService.deleteLabel(labelId , token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Label deleted" ));
	}
	
	/**
	 * Api to add label to note
	 * @param labelId to remove label
	 * @param noteId to identify note
	 * @param token to identify user
	 * 
	 */
	@PostMapping("/labels/addLabel")
	public ResponseEntity<Response> addLabel(@RequestParam long labelId , @RequestParam long noteId , @RequestHeader String token)
	{
		labelService.addLabel(labelId , noteId , token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Label added" ));
	}
	
	/**
	 * Api to rmove label from note
	 * @param labelId to remove label
	 * @param noteId to identify note
	 * @param token to identify user
	 * 
	 */
	@PostMapping("/labels/remove")
	public ResponseEntity<Response> removeLabel(@RequestParam long labelId , @RequestParam long noteId , @RequestHeader String token)
	{
		labelService.removeLabel(labelId , noteId , token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Label removed" ));
	}
	
	/**
	 * Api to get all labels
	 * @param token to identify user
	 * @return list of labels
	 */
	@GetMapping("/labels/getLabels")
	public ResponseEntity<Response> getLabels(@RequestHeader String token)
	{
		List<LabelEntity> labels = labelService.getLabels(token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Label fetched" , labels ));
	}	
	
	/**
	 * Api to get all notes having same label
	 * @param labelId to get label
	 * @param token to identify user
	 * @return list of notes fetched
	 */
	@GetMapping("/labels/getNotesByLabel/{labelId}")
	public ResponseEntity<Response> getNotes(@PathVariable long labelId , @RequestHeader String token)
	{
		List<NoteEntity> notes = labelService.getNotesByLabel(labelId , token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Notes fetched" , notes ));
	}
}
