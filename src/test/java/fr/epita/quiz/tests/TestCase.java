package fr.epita.quiz.tests;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.datamodel.QuestionType;


/**
 * @author itsme_omkar
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class TestCase {

	private static final Logger LOGGER = LogManager.getLogger(TestCase.class);

	@Inject
	SessionFactory sf;

	@Test
	public void testMethod() {
		// given
		Assert.assertNotNull(sf);

		final Question question = new Question();
		question.setQuestion("How to configure Hibernate?");
		question.setType(QuestionType.MCQ);

		final Session session = sf.openSession();

		// when
		final Transaction tx = session.beginTransaction();
		session.saveOrUpdate(question);
		tx.commit();
		// then
		// TODO

	}

}
