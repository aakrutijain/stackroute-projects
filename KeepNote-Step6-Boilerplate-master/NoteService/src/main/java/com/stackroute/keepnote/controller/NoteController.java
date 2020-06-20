package com.stackroute.keepnote.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.keepnote.exception.NoteNotFoundExeption;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.service.NoteService;

/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController
public class NoteController {

	/*
	 * Autowiring should be implemented for the NoteService. (Use Constructor-based
	 * autowiring) Please note that we should not create any object using the new
	 * keyword
	 */
	static Log logger = LogFactory.getLog(NoteController.class);
	
	@Autowired
	private NoteService noteService;
	

	public NoteController(NoteService noteService) {
		
		this.noteService=noteService;
	}

	/*
	 * Define a handler method which will create a specific note by reading the
	 * Serialized object from request body and save the note details in the
	 * database.This handler method should return any one of the status messages
	 * basis on different situations: 
	 * 1. 201(CREATED) - If the note created successfully. 
	 * 2. 409(CONFLICT) - If the noteId conflicts with any existing user.
	 * 
	 * This handler method should map to the URL "/api/v1/note" using HTTP POST method
	 */
	
	@PostMapping("/api/v1/note")
	public ResponseEntity<?> createNote(@RequestBody Note note) {
		
		boolean flag = noteService.createNote(note);
		if(flag) {
			logger.info("note created");
			return new ResponseEntity<Note>(HttpStatus.CREATED);
		}
		logger.error("Note already exists");
		return new ResponseEntity<Note>(HttpStatus.CONFLICT);
		
	}

	/*
	 * Define a handler method which will delete a note from a database.
	 * This handler method should return any one of the status messages basis 
	 * on different situations: 
	 * 1. 200(OK) - If the note deleted successfully from database. 
	 * 2. 404(NOT FOUND) - If the note with specified noteId is not found.
	 *
	 * This handler method should map to the URL "/api/v1/note/{id}" using HTTP Delete
	 * method" where "id" should be replaced by a valid noteId without {}
	 */
	
	@DeleteMapping("/api/v1/note/{userId}/{id}")
	public ResponseEntity<?> deleteNote(@PathVariable String userId, @PathVariable int id) {
		
		boolean flag= false;
		
		try {
			if(userId!= null) {
				flag = noteService.deleteNote(userId, id);
			}
			
			if(flag) {
				return new ResponseEntity<>(HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/api/v1/note/{userId}")
	public ResponseEntity<?> deleteAllNotes(@PathVariable String userId) {
		
		boolean flag= false;
		
		try {
			flag = noteService.deleteAllNotes(userId);
			if(flag) {
				return new ResponseEntity<>(HttpStatus.OK);
			}
		} catch (NoteNotFoundExeption e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/*
	 * Define a handler method which will update a specific note by reading the
	 * Serialized object from request body and save the updated note details in a
	 * database. 
	 * This handler method should return any one of the status messages
	 * basis on different situations: 
	 * 1. 200(OK) - If the note updated successfully.
	 * 2. 404(NOT FOUND) - If the note with specified noteId is not found.
	 * 
	 * This handler method should map to the URL "/api/v1/note/{id}" using HTTP PUT method.
	 */
	
	@PutMapping("/api/v1/note/{userId}/{noteId}")
	public ResponseEntity<Note> updateNote(@RequestBody Note note, @PathVariable String userId, @PathVariable int noteId) {
		
		try {
			Note note1 = noteService.updateNote(note, noteId, userId);
			return new ResponseEntity<>(note1, HttpStatus.OK); 
		} catch (NoteNotFoundExeption e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	/*
	 * Define a handler method which will get us the all notes by a userId.
	 * This handler method should return any one of the status messages basis on
	 * different situations: 
	 * 1. 200(OK) - If the note found successfully. 
	 * 
	 * This handler method should map to the URL "/api/v1/note" using HTTP GET method
	 */
	@GetMapping("/api/v1/note/{userId}")
	public ResponseEntity<?> getAllNotes(@PathVariable String userId) {
		
		return new ResponseEntity<>(noteService.getAllNoteByUserId(userId), HttpStatus.OK);
		
	}
	/*
	 * Define a handler method which will show details of a specific note created by specific 
	 * user. This handler method should return any one of the status messages basis on
	 * different situations: 
	 * 1. 200(OK) - If the note found successfully. 
	 * 2. 404(NOT FOUND) - If the note with specified noteId is not found.
	 * This handler method should map to the URL "/api/v1/note/{userId}/{noteId}" using HTTP GET method
	 * where "id" should be replaced by a valid reminderId without {}
	 * 
	 */
	@GetMapping("/api/v1/note/{userId}/{noteId}")
	public ResponseEntity<?> getNote(@PathVariable String userId, @PathVariable int noteId) {
		
		try {
			Note note = noteService.getNoteByNoteId(userId, noteId);
			return new ResponseEntity<>(note, HttpStatus.OK);
		} catch (NoteNotFoundExeption e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}

}