package com.stackroute.keepnote.dao;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.exception.NoteNotFoundException;
import com.stackroute.keepnote.model.Category;
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
public class CategoryDAOImpl implements CategoryDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */
	private final SessionFactory sessionFactory;
	
	public CategoryDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * Create a new category
	 */
	public boolean createCategory(Category category) {
		Category categoryExisting = getCurrentSession().get(Category.class, category.getCategoryId());
		if(categoryExisting!=null) {
			return false;
		}
		getCurrentSession().save(category);
		return true;
	}

	/*
	 * Remove an existing category
	 */
	public boolean deleteCategory(int categoryId) {
		Category categoryExisting = getCurrentSession().get(Category.class, categoryId);
		if(categoryExisting==null) {
			return false;
		}
		getCurrentSession().delete(categoryExisting);
		return true;

	}
	/*
	 * Update an existing category
	 */

	public boolean updateCategory(Category category) {
		Category categoryExisting = getCurrentSession().get(Category.class, category.getCategoryId());
		if(categoryExisting==null) {
			return false;
		}
		getCurrentSession().update(category);
		return true;
	}
	/*
	 * Retrieve details of a specific category
	 */

	public Category getCategoryById(int categoryId) throws CategoryNotFoundException {
		try {
			return getCurrentSession().get(Category.class, categoryId);
		}catch(Exception e) {
			throw new CategoryNotFoundException("Category does not exist");
		}	

	}

	/*
	 * Retrieve details of all categories by userId
	 */
	public List<Category> getAllCategoryByUserId(String userId) {
		Query query = getCurrentSession().createQuery("from Category where categoryCreatedBy = ?1");
		query.setParameter(1, userId);
		return (List<Category>) query.getResultList();

	}
	
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

}
