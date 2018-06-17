/**
 * Ce fichier est la propriété de Thomas BROUSSARD
 * Code application :
 * Composant :
 */
package fr.epita.quiz.services;

import javax.inject.Inject;

import fr.epita.quiz.datamodel.Login;
public class AuthenticationService {

	@Inject
	private LoginDAO loginDAO;

	public AuthenticationService() {
		loginDAO= new LoginDAO();
	}

	public boolean authenticate(Login entity) {
		return (loginDAO.search(entity).size()>=1);
	}

}
