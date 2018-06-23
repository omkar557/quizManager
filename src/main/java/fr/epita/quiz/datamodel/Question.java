package fr.epita.quiz.datamodel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private String questionObject;

	private QuestionType type;

	/**
	 *
	 */
	public Question() {
	}

	public Question(int id, String questionObject, QuestionType type) {
		this.id = id;
		this.type = type;
		this.questionObject = questionObject;
	}

	/**
	 * @return the question
	 */
	public String getQuestion() {
		return questionObject;
	}

	/**
	 * @param question
	 *            the question to set
	 */
	public void setQuestion(String question) {
		this.questionObject = question;
	}

	/**
	 * @return the type
	 */
	public QuestionType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(QuestionType type) {
		this.type = type;
	}

}
