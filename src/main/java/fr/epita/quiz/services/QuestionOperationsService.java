/**
 * Ce fichier est la propriété de Thomas BROUSSARD
 * Code application :
 * Composant :
 */
package fr.epita.quiz.services;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.datamodel.Question;

/**
 * <h3>Description</h3>
 * <p>
 * This class allows to ...
 * </p>
 *
 * <h3>Usage</h3>
 * <p>
 * This class should be used as follows:
 * 
 * <pre>
 * <code>${type_name} instance = new ${type_name}();</code>
 * </pre>
 * </p>
 *
 * @since $${version}
 * @see See also $${link}
 * @author ${user}
 *
 *         ${tags}
 */
public class QuestionOperationsService {

	@Named("allQuestionQuery")
	@Inject
	String queryString;

	@Inject
	QuestionDAO questndao;

	Question questions = new Question();

	@Inject
	private MCQChoiceDAO mcqChoicedao;

	@Inject
	private QuestionDAO questiondao;

	@Inject
	private SessionFactory factory;

	/**
	 * 
	 * 
	 * @param question
	 */
	public void deleteQuestion(Question question) {
		final Transaction transaction = factory.openSession().beginTransaction();
		final MCQChoice criteria = new MCQChoice();
		criteria.setQuestion(question);
		final List<MCQChoice> choicesList = mcqChoicedao.search(criteria);
		for (final MCQChoice choice : choicesList) {
			mcqChoicedao.delete(choice);
		}
		questiondao.delete(question);
		transaction.commit();
	}

	public List<Question> listAllQuestions(Question questions) {

		List<Question> questionList = questndao.getListOfAllQuestions(questions);
		return questionList;
	}

	public List<Question> getAllQuestions(Question question){
		
		List<Question> lstQuestion=questndao.getListOfRecord(question, queryString);
		return lstQuestion;
	}
}
