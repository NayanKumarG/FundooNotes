/**
 * @author Nayan Kumar G
 * purpose :Service implementation for elastic search
 * date    :15-03-2020
 */
package com.bridgelabz.fundoonotes.service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.configuration.ElasticSearchConfiguration;
import com.bridgelabz.fundoonotes.entity.NoteEntity;
import com.bridgelabz.fundoonotes.exception.RequestFailureException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {

	@Autowired
	private ElasticSearchConfiguration elasticConfig;

	@Autowired
	private ObjectMapper objectMapper;

	private final String INDEX = "fundoo";

	private final String TYPE = "note_service";  

	/**
	 * Elastic service to create note
	 */
	@Override
	public NoteEntity createNote(NoteEntity note) {

	Map<?, ?> dataMap = objectMapper.convertValue(note , Map.class);
	
	IndexRequest indexRequest = new IndexRequest(INDEX , TYPE , String.valueOf(note.getNoteId())).source(dataMap);
	
	try
	{
	elasticConfig.client().index(indexRequest , RequestOptions.DEFAULT);
	}catch(Exception e)
	{
	throw new RequestFailureException("request rejected", HttpStatus.NOT_ACCEPTABLE);
	}
	
	return note;
	}

	/**
	 * Service to update note
	 */
	@Override
	public NoteEntity updateNote(NoteEntity note) {
		Map<?, ?> dataMap = objectMapper.convertValue(note , Map.class);
		UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, String.valueOf(note.getNoteId())).doc(dataMap);
		try {
			elasticConfig.client().update(updateRequest, RequestOptions.DEFAULT);
			
		}catch(IOException e)
		{
			throw new RequestFailureException("request rejected", HttpStatus.NOT_ACCEPTABLE);
			}
		return note;
	}
	
	/**
	 * service to delete note
	 */
	@Override
	public boolean deleteNote(NoteEntity note) {
		
		DeleteRequest deleteRequest = new DeleteRequest(INDEX , TYPE , String.valueOf(note.getNoteId()));
		try
		{
			elasticConfig.client().delete(deleteRequest, RequestOptions.DEFAULT);
			return true;
		}catch(IOException e)
		{
			throw new RequestFailureException("request rejected", HttpStatus.NOT_ACCEPTABLE);
		}
	
	}

	/**
	 * service provide search note by title
	 */
	@Override
	public List<NoteEntity> searchByTitle(String title) {
		
		SearchRequest searchRequest = new SearchRequest("fundoo");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchQuery("title", title));
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = null;
		try
		{
			searchResponse = elasticConfig.client().search(searchRequest, RequestOptions.DEFAULT);
		}catch (Exception e) {
			throw new RequestFailureException("request rejected", HttpStatus.NOT_ACCEPTABLE);
		}
		return getResult(searchResponse);
	}

	private List<NoteEntity> getResult(SearchResponse searchResponse) {
		SearchHit[] searchhits = searchResponse.getHits().getHits();
		List<NoteEntity> notes = new ArrayList<>();
		if (searchhits.length > 0) {
			Arrays.stream(searchhits)
					.forEach(hit -> notes.add(objectMapper.convertValue(hit.getSourceAsMap(), NoteEntity.class)));
		}
		return notes;
	}
	
}
