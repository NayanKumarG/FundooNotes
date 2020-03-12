/**
 * @author Nayan Kumar G
 * purpose :service to collaborator
 * date    :12-03-2020
 */
package com.bridgelabz.fundoonotes.service;
import java.util.List;

import com.bridgelabz.fundoonotes.entity.NoteEntity;

public interface CollaboratorService {

	NoteEntity addCollaborator(long noteId, String email, String token);

	NoteEntity deleteCollaborator(long noteId, String email, String token);

	List<NoteEntity> getCollaboratorNotes(String token);
}
