package fr.epita.quiz.services;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import fr.epita.quiz.datamodel.Question;

public class QuestionDAO extends GenericORMDao<Question>{
	
	@Inject
	@Named("questionQuery")
	String query;
	
	@Inject
	@Named("allQuestionQuery")
	String getAllQuestionsQuery;
//
//	@Override
//	protected WhereClauseBuilder<Question> getWhereClauseBuilder(Question entity) {
//		final WhereClauseBuilder<Question> wcb = new WhereClauseBuilder<>();
//		wcb.setQueryString(query);
//
//		// TODO as bonus : let the whereclausebuilder generate this map thanks to introspection
//		final Map<String, Object> parameters = new LinkedHashMap<>();
//		parameters.put("type", entity.getType());
//		parameters.put("question", entity.getQuestion());
//		wcb.setParameters(parameters);
//		return wcb;
//
//	}

	// @Override
	// protected String getSearchQuery(Question question) {
	// return query;
	// }
	//
	// /*
	// * (non-Javadoc)
	// * @see fr.epita.quiz.services.GenericHibernateDao#completeQuery(org.hibernate.query.Query)
	// */
	// @Override
	// protected void completeQuery(Question question, Query toBeCompleted) {
	//
	// toBeCompleted.setParameter("type", question.getType());
	// toBeCompleted.setParameter("question", question.getQuestion());
	// }

	/*
	 * (non-Javadoc)
	 * @see fr.epita.quiz.services.GenericHibernateDao#getWhereClauseBuilder(java.lang.Object)
	 */
	
    public List<Question> getListOfAllQuestions(Question question){
		
		List<Question> studentList = this.getListOfRecord(question, getAllQuestionsQuery);
		return studentList;
	}

}
