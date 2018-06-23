package fr.epita.quiz.datamodel;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author itsme_omkar
 *
 */
@Entity
public class Login {

	/** The e-mail */
	@Id
	private String email;

	/** The password */
	private String password;
	
	public Login() {
	}
/**
 * Constructor for the class
 * 
 * @param email Email
 * @param password Password
 */
	public Login(String email, String password) {
		this.email = email;
		this.password = password;
	}

	/**
	 * Gets the e-mail.
	 * 
	 * @return String The e-mail
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the e-mail.
	 * 
	 * @param email The e-mail address
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the password.
	 * 
	 * @return String The password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 * 
	 * @param password Password value
	 *            
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
