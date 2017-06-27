package de.hdm.projectplan.shared.bo;

public class User extends BusinessObject{

	/**
	 * Deklaration der serialVersionUID zur Serialisierung der Objekte
	 */
	private static final long serialVersionUID = 1L;
	
	
/**
 * Attribute
 */
	
	/**
	 * Die Email Adresse, mit der sich der Nutzer anmeldet
	 */
	private String email;

	/**
	 * Der Vorname des Nutzers
	 */
	private String vorname;
	
	/**
	 * Der Nachname des Nutzers
	 */
	private String nachname;
	
	/**
	 * Das Arbeitsgebiet des Nutzers
	 */
	private String arbeitsgebiet;
	
	/**
	 * Das Interesse (an Projekten) des Nutzers
	 */
	private String interesse;
	
	
/**
 * Methoden
 */

	/**
	 * Rueckgeben der Email Adresse mit der sich der Nutzer angemeldet hat 
	 * 
	 * @return email mit der sich der Nutzer angemeldet hat
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Setzen der Email mit der sich der Nutzer angemeldet hat
	 * 
	 * @param email - die zu setzende email, mit der sich der Nutzer angemeldet hat
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Rueckgeben des Vorname des Nutzers
	 * 
	 * @return vorname, der dem Nutzer zugewiesen wurde
	 */
	public String getVorname() {
		return this.vorname;
	}

	/**
	 * Setzen des Vorname des Nutzers
	 * 
	 * @param vorname - der fuer den Nutzer zu setzende vorname 
	 */
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	
	/**
	 * Rueckgeben des Nachname des Nutzers
	 * 
	 * @return nachname, der dem Nutzer zugewiesen wurde
	 */
	public String getNachname() {
		return this.nachname;
	}

	/**
	 * Setzen des Nachname des Nutzers
	 * 
	 * @param nachname - der fuer den Nutzer zu setzende nachname 
	 */
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	/**
	 * Rueckgeben des Arbeitsgebiet des Nutzers
	 * 
	 * @return arbeitsgebiet
	 */
	public String getArbeitsgebiet() {
		return arbeitsgebiet;
	}

	/**
	 * Setzen des Arbeitsgebiets des Nutzers
	 * 
	 * @param arbeitsgebiet
	 */
	public void setArbeitsgebiet(String arbeitsgebiet) {
		this.arbeitsgebiet = arbeitsgebiet;
	}

	/**
	 * Rueckgeben des Interesses des Nutzers
	 * 
	 * @return interesse
	 */
	public String getInteresse() {
		return interesse;
	}

	/**
	 * Setzen des Interesses des Nutzers
	 * 
	 * @param interesse
	 */
	public void setInteresse(String interesse) {
		this.interesse = interesse;
	}
	
}
