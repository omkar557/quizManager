/**
 * 
 */
package fr.epita.quiz.services;

import javax.inject.Inject;
import javax.inject.Named;

import fr.epita.quiz.datamodel.Login;
import fr.epita.quiz.datamodel.Question;

/**
 * @author itsme_omkar
 *
 */
public class LoginDAO extends GenericORMDao<Login> {
	
	@Inject
	@Named("loginQuery")
	String query;

//	@Override
//	protected WhereClauseBuilder getWhereClauseBuilder(Login entity) {
//		final WhereClauseBuilder<Question> wcb = new WhereClauseBuilder<>();
//		wcb.setQueryString(query);
//		return wcb;
//	}
}
