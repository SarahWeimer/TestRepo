package de.hdm.projectplan.shared;

import java.io.Serializable;


/**
 * Diese Klasse ist Bestandteil des Login-Vorgangs, um sich als Benutzer an der
 * Applikation anmelden und diese nutzen zu koennen. Siehe dazu auch
 * {@link LoginServiceImpl}
 * 
 */
public class LoginInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean loggedIn = false;
	private String loginUrl;
	private String logoutUrl;
	private String emailAddress;
	
	private String nickname;
	private String firstName;
	private String lastName;

	/**
	 * Gibt einen booleschen Wert zurueck, je nachdem, ob der Benutzer
	 * angemeldet (true) ist oder nicht (false).
	 * 
	 * @return
	 */
	public boolean isLoggedIn() {
		return loggedIn;
	}
	
	/**
	 * Gibt die Google Login-URL zurueck.
	 * 
	 * @return
	 */
	public String getLoginUrl() {
		return loginUrl;
	}
	
	/**
	 * Gibt die Google Logout-URL zurueck.
	 * 
	 * @return
	 */
	public String getLogoutUrl() {
		return logoutUrl;
	}
	
	/**
	 * Gibt die E-Mailadresse des angemeldeten Benutzers zurueck.
	 * 
	 * @return
	 */
	public String getEmailAddress() {
		return emailAddress;
	}
	

	/**
	 * Legt einen booleschen Wert fest, je nachdem, ob der Benutzer angemeldet
	 * (true) ist oder nicht (false).
	 * 
	 * @param loggedIn
	 *            true: Benutzer angemeldet, false: Benutzer nicht angemeldet
	 */
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	
	/**
	 * Legt die Google Login-URL fest.
	 * 
	 * @param loginUrl von Google Accounts API fuer Benutzer erstellte URL
	 */
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}
	
	/**
	 * Legt die Google Logout-URL fest.
	 * 
	 * @param logoutUrl von Google Accounts API fuer Benutzer erstellte URL
	 */
	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}
	
	/**
	 * Legt die E-Mailadresse des Benutzers fest.
	 * 
	 * @param emailAddress neu zu setzende E-Mailadresse
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	//////////////////////////////////////
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public void setFirstName(String firstName){
		this.firstName = firstName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public void setLastName(String lastName){
		this.lastName = lastName;
	}
	
}
