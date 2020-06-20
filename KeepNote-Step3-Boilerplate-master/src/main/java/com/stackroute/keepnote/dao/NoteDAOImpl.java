package com.stackroute.keepnote.dao;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.exception.NoteNotFoundException;
import com.stackroute.keepnote.model.Note;

/*
 * This class is implementing the UserDAO interface. This class has to be annotated with 
 * @Repository annotation.
 * @Repository - is an annotation that marks the specific class as a Data Access Object, 
 * thus clarifying it's role.
 * @Transactional - The transactional annotation itself defines the scope of a single database 
 * 					transaction. The database transaction happens inside the scope of a persistence 
 * 					context.  
 * */
@Repository
@Transactional
public class NoteDAOImpl implements NoteDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */
	private final SessionFactory sessionFactory;
	
	public NoteDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * Create a new note
	 */
	
	public boolean createNote(Note note) {
		Note noteExisting = getCurrentSession().get(Note.class, note.getNoteId());
		if(noteExisting!=null) {
			return false;
		}
		getCurrentSession().save(note);
		return true;
	}

	/*
	 * Remove an existing note
	 */
	
	public boolean deleteNote(int noteId) {
		Note noteExisting = getCurrentSession().get(Note.class, noteId);
		if(noteExisting==null) {
			return false;
		}
		getCurrentSession().delete(noteExisting);
		return true;
	}

	/*
	 * Retrieve details of all notes by userId
	 */
	
	public List<Note> getAllNotesByUserId(String userId) {
		Query query = getCurrentSession().createQuery("from Note where createdBy = ?1");
		query.setParameter(1, userId);
		return (List<Note>) query.getResultList();

	}

	/*
	 * Retrieve details of a specific note
	 */
	
	public Note getNoteById(int noteId) throws NoteNotFoundException {
		try {
			return getCurrentSession().get(Note.class, noteId);
		}catch(Exception e) {
			throw new NoteNotFoundException("Note does not exist");
		}
	}

	/*
	 * Update an existing note
	 */

	public boolean UpdateNote(Note note) {
		Note noteExisting = getCurrentSession().get(Note.class, note.getNoteId());
		if(noteExisting==null) {
			return false;
		}
		getCurrentSession().update(note);
		return true;

	}
	
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

}
