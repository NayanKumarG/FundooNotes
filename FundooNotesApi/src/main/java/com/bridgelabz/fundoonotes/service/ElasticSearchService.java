/**
 * @author Nayan Kumar G
 * purpose :Provide elastic search service
 * date    :15-03-2020
 */
package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.entity.NoteEntity;

public interface ElasticSearchService {

	NoteEntity createNote(NoteEntity note);
	NoteEntity updateNote(NoteEntity note);
	List<NoteEntity> searchByTitle(String title);

}
