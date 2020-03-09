/**
 * @author Nayan Kumar G
 * purpose :Services for label
 * date    :06-03-2020
 */
package com.bridgelabz.fundoonotes.service;
import java.util.List;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.dto.LabelUpdateDto;
import com.bridgelabz.fundoonotes.entity.LabelEntity;
import com.bridgelabz.fundoonotes.entity.NoteEntity;
public interface LabelService {

	void createLabel(LabelDto labelDto, String token);

	void updateLabel(LabelUpdateDto labelUpdateDto, String token);

	void deleteLabel(long labelId, String token);

	void removeLabel(long labelId, long noteId, String token);

	void addLabel(long labelId, long noteId, String token);

	List<LabelEntity> getLabels(String token);

	List<NoteEntity> getNotesByLabel(long labelId, String token);

	

	
}
