package com.stackroute.keepnote.dao;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.exception.UserNotFoundException;
import com.stackroute.keepnote.model.User;

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
public class UserDaoImpl implements UserDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */
	private final SessionFactory sessionFactory;

	public UserDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * Create a new user
	 */

	public boolean registerUser(User user) {
		User userExisting = getCurrentSession().get(User.class, user.getUserId());
		if(userExisting!=null) {
			return false;
		}
		getCurrentSession().save(user);
		return true;
	}

	/*
	 * Update an existing user
	 */

	public boolean updateUser(User user) {
		User userExisting = getCurrentSession().get(User.class, user.getUserId());
		if(userExisting==null) {
			return false;
		}
		getCurrentSession().update(user);
		return true;
	}

	/*
	 * Retrieve details of a specific user
	 */
	public User getUserById(String userId) {
		return getCurrentSession().get(User.class, userId);
	}

	/*
	 * validate an user
	 */

	public boolean validateUser(String userId, String password) throws UserNotFoundException {
		try {
			User user = getUserById(userId);
			if(user== null) {
				throw new UserNotFoundException("User Not Found.");
			}
			
			if(user.getUserPassword().equalsIgnoreCase(password)) {
				return true;
			}
			return false;
		}catch (Exception e) {
			throw new UserNotFoundException("User Not Found.");
		}
	}

	/*
	 * Remove an existing user
	 */
	public boolean deleteUser(String userId) {
		User userExisting = getCurrentSession().get(User.class, userId);
		if(userExisting==null) {
			return false;
		}
		getCurrentSession().delete(userExisting);;
		return true;
	}
	
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

}
