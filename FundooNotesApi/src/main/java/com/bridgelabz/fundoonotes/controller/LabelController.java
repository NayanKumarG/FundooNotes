/**
 * @author Nayan Kumar G
 * purpose :Controller for label
 * date    :06-03-2020
 */
package com.bridgelabz.fundoonotes.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.dto.LabelUpdateDto;
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
	
	@PutMapping("/labels/update")
	public ResponseEntity<Response> updateLabel(@RequestBody LabelUpdateDto labelUpdateDto , @RequestHeader String token)
	{
		labelService.updateLabel(labelUpdateDto , token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Label created" , labelUpdateDto));
	}
	
}
