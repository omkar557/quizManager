package fr.epita.quiz.services;

import javax.inject.Inject;

import fr.epita.quiz.datamodel.Login;
public class AuthenticationService {

	@Inject
	private LoginDAO loginDAO;
    /**
     * 
     * @param entity
     * @return
     */
	public boolean authenticate(Login entity) {
		return (!loginDAO.search(entity).isEmpty());
	}

}
