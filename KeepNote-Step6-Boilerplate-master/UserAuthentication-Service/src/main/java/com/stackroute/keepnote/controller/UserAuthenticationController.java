package com.stackroute.keepnote.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.keepnote.exception.UserNotFoundException;
import com.stackroute.keepnote.model.User;
import com.stackroute.keepnote.service.UserAuthenticationService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/*
 * As in this assignment, we are working on creating RESTful web service, hence annotate
 * the class with @RestController annotation. A class annotated with the @Controller annotation
 * has handler methods which return a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController
public class UserAuthenticationController {

    /*
	 * Autowiring should be implemented for the UserAuthenticationService. (Use Constructor-based
	 * autowiring) Please note that we should not create an object using the new
	 * keyword
	 */
	
	private UserAuthenticationService authService;

    public UserAuthenticationController(UserAuthenticationService authService) {
    	this.authService = authService;
	}

/*
	 * Define a handler method which will create a specific user by reading the
	 * Serialized object from request body and save the user details in the
	 * database. This handler method should return any one of the status messages
	 * basis on different situations:
	 * 1. 201(CREATED) - If the user created successfully. 
	 * 2. 409(CONFLICT) - If the userId conflicts with any existing user
	 * 
	 * This handler method should map to the URL "/api/v1/auth/register" using HTTP POST method
	 */
    @PostMapping("/api/v1/auth/register")
	public ResponseEntity<?>  saveUser(@RequestBody User user) {
    	try {
    		user.setUserAddedDate(new Date(System.currentTimeMillis()));
    		boolean flag = authService.saveUser(user);
    		if(flag) {
    			return new ResponseEntity<User>(user, HttpStatus.CREATED); 
    		} else {
    			return new ResponseEntity<>("User not created", HttpStatus.CREATED);
    		}
    	} catch(Exception e) {
    		return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
    	}
    }


	/* Define a handler method which will authenticate a user by reading the Serialized user
	 * object from request body containing the username and password. The username and password should be validated 
	 * before proceeding ahead with JWT token generation. The user credentials will be validated against the database entries. 
	 * The error should be return if validation is not successful. If credentials are validated successfully, then JWT
	 * token will be generated. The token should be returned back to the caller along with the API response.
	 * This handler method should return any one of the status messages basis on different
	 * situations:
	 * 1. 200(OK) - If login is successful
	 * 2. 401(UNAUTHORIZED) - If login is not successful
	 * 
	 * This handler method should map to the URL "/api/v1/auth/login" using HTTP POST method
	*/
    @PostMapping("/api/v1/auth/login")
	public ResponseEntity<?>  loginUser(@RequestBody User user) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			authService.findByUserIdAndPassword(user.getUserId(), user.getUserPassword());
			String token = getToken(user.getUserId());
			map.put("token", token);
			map.put("message", "token created");
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch(UserNotFoundException e) {
			return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    	} catch(Exception e1) {
			return new ResponseEntity<>("Server down", HttpStatus.INTERNAL_SERVER_ERROR);
    	}
	}

// Generate JWT token
	public String getToken(String username) throws Exception {
		String jwtToken = null;
		jwtToken = Jwts.builder().setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+60*60*60))
				.signWith(SignatureAlgorithm.HS256, "secretKey").compact();
        return jwtToken;        
}


}
