package com.stackroute.keepnote.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.model.Note;

/*
 * This class is implementing the NoteDAO interface. This class has to be annotated with @Repository
 * annotation.
 * @Repository - is an annotation that marks the specific class as a Data Access Object, thus 
 * 				 clarifying it's role.
 * @Transactional - The transactional annotation itself defines the scope of a single database 
 * 					transaction. The database transaction happens inside the scope of a persistence 
 * 					context.  
 * */

@Repository
@Transactional
public class NoteDAOImpl implements NoteDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.
	 */

	private final SessionFactory sessionFactory;
	
	public NoteDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * Save the note in the database(note) table.
	 */

	public boolean saveNote(Note note) {
		
		Note existingNote = sessionFactory.getCurrentSession().get(Note.class, note.getNoteId());
		
		if(existingNote==null) {
			sessionFactory.getCurrentSession().save(note);
			return true;			
		}
		
		return false;
	}

	/*
	 * Remove the note from the database(note) table.
	 */

	public boolean deleteNote(int noteId) {
		Note existingNote = getNoteById(noteId);
		
		if(existingNote!=null) {
			sessionFactory.getCurrentSession().delete(existingNote);
			return true;			
		}
		
		return false;
	}

	/*
	 * retrieve all existing notes sorted by created Date in descending
	 * order(showing latest note first)
	 */
	public List<Note> getAllNotes() {
		return sessionFactory.getCurrentSession().createQuery("from Note").list();
	}

	/*
	 * retrieve specific note from the database(note) table
	 */
	public Note getNoteById(int noteId) {
		return sessionFactory.getCurrentSession().get(Note.class, noteId);
	}

	/* Update existing note */

	public boolean UpdateNote(Note note) {
		Note existingNote = sessionFactory.getCurrentSession().get(Note.class, note.getNoteId());
		
		if(existingNote!=null) {
			sessionFactory.getCurrentSession().update(note);
			return true;			
		}
		
		return false;
	}

}
