/**
 * @author Nayan Kumar G
 * purpose :Note repository which extends CrudRepository
 * date    :03-03-2020
 */
package com.bridgelabz.fundoonotes.repository;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.entity.NoteEntity;

@Repository
public class NoteRepository{ 
	
	@Autowired
	private EntityManager entityManager;
	

	/**
	 * 
	 * @param noteEntity to save into database
	 * @return noteEntity
	 */
	@Transactional
	public NoteEntity saveOrUpdate(NoteEntity noteEntity)
	{
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(noteEntity);
		return noteEntity;
	}
	
	/**
	 * 
	 * @param id to fetch perticular note
	 * @return fetched note
	 */
	@Transactional

	public NoteEntity fetchById(long id)
	{
		Session session = entityManager.unwrap(Session.class);
		Query<?> query = session.createQuery("from NoteEntity where note_id=:id");
		query.setParameter("id", id);
		return (NoteEntity) query.uniqueResult();
	}
	
	/**
	 * 
	 * @param id note id to delete
	 * @return true if deleted
	 */
	@Transactional
	public boolean deleteNote(long id )
	{
		Session session = entityManager.unwrap(Session.class);
		Query<?> query = session.createQuery("delete from NoteEntity where note_id=:id");
		query.setParameter("id", id);
		return query.executeUpdate()==1;
	}
	/**
	 * 
	 * @return list of notes
	 */
	@Transactional
	public List<NoteEntity> getNotes(long userId) {

		Session session = entityManager.unwrap(Session.class);
		Query<NoteEntity> query = session.createQuery("from NoteEntity where user_id=:userId" , NoteEntity.class);
		query.setParameter("userId", userId);
		return query.getResultList();
	
	}                  
	
	/**
	 * 
	 * @return list of trashed notes
	 */
	@Transactional
	public List<NoteEntity> getTrashedNotes(long userId)
	{
		Session session = entityManager.unwrap(Session.class);
		//Query<NoteEntity> query = session.createQuery("from NoteEntity where user_id=:userId and is_trashed=true" , NoteEntity.class);
		Query<NoteEntity> query = session.createQuery("from NoteEntity where user_id=:userId" , NoteEntity.class);
		query.setParameter("userId", userId);
		return query.getResultList();
		
	}

	/**
	 * 
	 * @return list of archieved notes
	 */
	@Transactional
	public List<NoteEntity> getArchievedNotes(long userId) {
		Session session = entityManager.unwrap(Session.class);
		Query<NoteEntity> query = session.createQuery("from NoteEntity where user_id=:userId and is_archieved=true" , NoteEntity.class);
		query.setParameter("userId", userId);
		return query.getResultList();
	}

	/**
	 * 
	 * @return list of pinned notes
	 */
	@Transactional
	public List<NoteEntity> getpinnedNotes(long userId) {
		Session session = entityManager.unwrap(Session.class);
		Query<NoteEntity> query = session.createQuery("from NoteEntity where user_id=:userId and is_pinned=true" , NoteEntity.class);
		query.setParameter("userId", userId);
		return query.getResultList();
	}

	/**
	 * 
	 * @param title to identify note
	 * @param userId to get note for user
	 * @return list of notes with same title
	 */
	@Transactional
	public List<NoteEntity> getNotesByTitle(String title, long userId) {
		Session session = entityManager.unwrap(Session.class);
		Query<NoteEntity> query = session.createQuery("from NoteEntity where title=:title and user_id=:userId" , NoteEntity.class);
		query.setParameter("title", title);
		query.setParameter("userId", userId);
		return query.getResultList();
	}



}
