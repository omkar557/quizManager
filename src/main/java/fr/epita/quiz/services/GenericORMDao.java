package fr.epita.quiz.services;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class GenericORMDao<T> {
	
	@Autowired
	SessionFactory sf;
	
	public final void create(T entity) {
		if (!beforeCreate(entity)) {
			return;
		}

		final Session session = sf.openSession();
		session.saveOrUpdate(entity);
	}

	protected boolean beforeCreate(T entity) {
		return entity != null;
	}

	public final void update(T entity) {
		final Session session = sf.openSession();
		session.saveOrUpdate(entity);
	}

	public final void delete(T entity) {
		final Session session = sf.openSession();
		session.delete(entity);
	}

	public final List<T> search(T entity) {
		final Session session = sf.openSession();
		final WhereClauseBuilder<T> wcb = new WhereClauseBuilder<T>();
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

		final Map<String, Object> parameters = new LinkedHashMap<>();

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
	 * The string creates the where clause for the generateHqlString
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
				e.printStackTrace();
			}
		}
		return parameters;
	}
}