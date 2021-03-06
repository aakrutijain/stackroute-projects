package com.stackroute.keepnote.controller;

import com.stackroute.keepnote.proxy.NoteServiceProxy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.keepnote.exceptions.UserAlreadyExistsException;
import com.stackroute.keepnote.exceptions.UserNotFoundException;
import com.stackroute.keepnote.model.User;
import com.stackroute.keepnote.service.UserService;

/*
 * As in this assignment, we are working on creating RESTful web service, hence annotate
 * the class with @RestController annotation. A class annotated with the @Controller annotation
 * has handler methods which return a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized
 * format. Starting from Spring 4 and above, we can use @RestController annotation which
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController
public class UserController {

	@Value("${msg:could not load}")
	private String message;

	/*
	 * Autowiring should be implemented for the UserService. (Use Constructor-based
	 * autowiring) Please note that we should not create an object using the new
	 * keyword
	 */
	static Log logger = LogFactory.getLog(UserController.class);

//	@Autowired
//	private NoteServiceProxy noteServiceProxy;

	@Autowired
	private UserService userService;


	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public String getMessage() {
		return message;
	}

//	@GetMapping("/{userId}/{noteId}")
//	public ResponseEntity<?> getDetails(@PathVariable String userId, @PathVariable int noteId) {
//		return noteServiceProxy.getNotes(userId, noteId);
//	}

	/*
	 * Define a handler method which will create a specific user by reading the
	 * Serialized object from request body and save the user details in the
	 * database. This handler method should return any one of the status messages
	 * basis on different situations:
	 * 1. 201(CREATED) - If the user created successfully.
	 * 2. 409(CONFLICT) - If the userId conflicts with any existing user
	 *
	 * This handler method should map to the URL "/user" using HTTP POST method
	 */

	@PostMapping(value="/api/v1/user")
	public ResponseEntity<User>  registerUser(@RequestBody User user) {

		try {
			User usr = userService.registerUser(user);
			logger.info("user registered");
			return new ResponseEntity<>(usr, HttpStatus.CREATED);
		} catch (UserAlreadyExistsException e) {
			logger.error("User already exists");
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

	}
	/*
	 * Define a handler method which will update a specific user by reading the
	 * Serialized object from request body and save the updated user details in a
	 * database. This handler method should return any one of the status messages
	 * basis on different situations:
	 * 1. 200(OK) - If the user updated successfully.
	 * 2. 404(NOT FOUND) - If the user with specified userId is not found.
	 *
	 * This handler method should map to the URL "/api/v1/user/{id}" using HTTP PUT method.
	 */
	@PutMapping("/api/v1/user/{id}")
	public ResponseEntity<?>  updateUser( @RequestBody User user, @PathVariable String id) {
		User usr= null;
		try {

			usr = userService.updateUser(id, user);

			if(usr!=null) {
				return new ResponseEntity<>(usr, HttpStatus.OK);
			}

		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	/*
	 * Define a handler method which will delete a user from a database.
	 * This handler method should return any one of the status messages basis on
	 * different situations:
	 * 1. 200(OK) - If the user deleted successfully from database.
	 * 2. 404(NOT FOUND) - If the user with specified userId is not found.
	 *
	 * This handler method should map to the URL "/api/v1/user/{id}" using HTTP Delete
	 * method" where "id" should be replaced by a valid userId without {}
	 */

	@DeleteMapping("/api/v1/user/{id}")
	public ResponseEntity<?>  delete(@PathVariable String id) {

		System.out.println("delete() User ID: "+id);

		boolean flag = false;
		try {
			flag = userService.deleteUser(id);
			if(flag) {
				return new ResponseEntity<>(HttpStatus.OK);
			}
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}
	/*
	 * Define a handler method which will show details of a specific user. This
	 * handler method should return any one of the status messages basis on
	 * different situations:
	 * 1. 200(OK) - If the user found successfully.
	 * 2. 404(NOT FOUND) - If the user with specified userId is not found.
	 * This handler method should map to the URL "/api/v1/user/{id}" using HTTP GET method where "id" should be
	 * replaced by a valid userId without {}
	 */

	@GetMapping("/api/v1/user/{id}")
	public ResponseEntity<?>  getUser(@PathVariable String id) {

		User usr = null;
		try {
			System.out.println("getUser() User ID: "+id);
			usr  = userService.getUserById(id);

			if(usr!=null) {
				return new ResponseEntity<>(usr, HttpStatus.OK);
			}

		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}
}