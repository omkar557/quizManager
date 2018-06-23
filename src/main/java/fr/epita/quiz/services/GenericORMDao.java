package fr.epita.quiz.services;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
/**
 * 
 * @author itsme_omkar
 *
 * @param <T> Generic Entity
 */
public abstract class GenericORMDao<T> {
	
	@Autowired
	SessionFactory sf;
	
	private static final Logger LOGGER = LogManager.getLogger(GenericORMDao.class);
	
	/**
	 * 
	 * @param entity Credentials to create
	 */
	public final void create(T entity) {
		if (!beforeCreate(entity)) {
			return;
		}

		final Session session = sf.openSession();
		Transaction tr = session.beginTransaction();
		session.saveOrUpdate(entity);
		tr.commit();
	}

	protected boolean beforeCreate(T entity) {
		return entity != null;
	}
    
	/**
	 * 
	 * @param entity Credentials to update
	 */
	public final void update(T entity) {
		final Session session = sf.openSession();
		Transaction tr = session.beginTransaction();
		session.saveOrUpdate(entity);
		tr.commit();
	}
    
	/**
	 * 
	 * @param entity Credentials to delete
	 */
	public final void delete(T entity) {
		final Session session = sf.openSession();
		session.delete(entity);
	}
    
	/**
	 * 
	 * @param entity 
	 * @return List of the search query
	 */
	public final List<T> search(T entity) {
		final Session session = sf.openSession();
		final WhereClauseBuilder wcb = new WhereClauseBuilder();
		wcb.setQueryString(generateHqlString(entity));
		wcb.setParameters(linkedListBuilder(entity));
		final Query searchQuery = session.createQuery(wcb.getQueryString());
		
		for (final Entry<String, Object> parameterEntry : wcb.getParameters().entrySet()) {
			searchQuery.setParameter(parameterEntry.getKey(), parameterEntry.getValue());
		}

		return searchQuery.list();
	}
	
	/**
	 * The method reads a class and generates a hql query based on the attributes
	 * of the class except for the class attribute.
	 * 
	 * @param entity The generic class entity
	 * @return 
	 */
	public String generateHqlString(T entity) {

		final BeanWrapper sourceBean = new BeanWrapperImpl(entity.getClass());
		final PropertyDescriptor[] propertyDescriptors = sourceBean.getPropertyDescriptors();

		int i = 0;

		String simpleName = entity.getClass().getSimpleName();

		String baseName = "from " + simpleName + " as " + simpleName.toLowerCase() + " where ";
		for (final PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			if (propertyDescriptor.getName() != null && !propertyDescriptor.getName().contains("class")) {
				baseName += whereClauseBuilder(simpleName, propertyDescriptor.getName(), i);
				i++;
			}
		}
		return baseName;
	}

	/**
	 * Dynamic where clause builder
	 * 
	 * @param simpleName The simple name
	 * @param attributeName The attribute name
	 * @param i The count
	 * @return String The where clause
	 */
	private String whereClauseBuilder(String simpleName, String attributeName, int i) {

		String str = null;

		if (i == 0) {
			str = simpleName.toLowerCase() + "." + attributeName + " = :" + attributeName;
		}

		else {
			str = " and " + simpleName.toLowerCase() + "." + attributeName + " = :" + attributeName;
		}
		return str;
	}
    
	/**
	 * Link list builder 
	 *   
	 * @param entity Entities for the Linked list
	 * @return parameters Parameters
	 */
	public Map linkedListBuilder(T entity) {

		final BeanWrapper sourceBean = new BeanWrapperImpl(entity.getClass());
		final PropertyDescriptor[] propertyDescriptors = sourceBean.getPropertyDescriptors();
		final Map<String, Object> parameters = new LinkedHashMap<>();

		for (final PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			Method method = propertyDescriptor.getReadMethod();

			try {

				Object o = method.invoke(entity);

				if (o != null && !method.getName().contains("Class")) {
					parameters.put(propertyDescriptor.getDisplayName(), o);
					System.out.println("Inserting Key: " + propertyDescriptor.getName() + " with Value: " + o);
				}

			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				LOGGER.debug("Error while insertion");		
				}
		}
		return parameters;
	}
	
	
	 /**
	  * A generic method to query all records from a defined database on the bases of the
	  * object that is passed to it as a parameter
	  * @param entity object that is passed
	  * @param queryString string to be passed and executed
	  * @return
	  */
	public List<T> getListOfRecord(T entity, String queryString) {
		
		final Session sessions = sf.openSession();
		Transaction transactions = null;
		List<T> listOfRecord = null;
		try {
			
			transactions = sessions.beginTransaction();
			Query query = sessions.createQuery(queryString); 
			listOfRecord = query.list();

			transactions.commit();		
		} catch (Exception e) {

			if (sf.isOpen()) {
				sf.close();
			}
			transactions.rollback();
			
			listOfRecord = null;
		}
				
		return listOfRecord;
	}	
}