package de.hdm.projectplan.shared.bo;

import java.io.Serializable;

/**
 * <p>
 * Die Klasse <code>BusinessObject</code> stellt die Basisklasse aller in diesem
 * Projekt für die Umsetzung des Fachkonzepts relevanten Klassen dar.
 * </p>
 * <p>
 * Zentrales Merkmal ist, dass jedes <code>BusinessObject</code> eine Nummer
 * besitzt, die man in einer relationalen Datenbank auch als Primärschlüssel
 * bezeichnen würde. Fernen ist jedes <code>BusinessObject</code> als
 * {@link Serializable} gekennzeichnet. Durch diese Eigenschaft kann jedes
 * <code>BusinessObject</code> automatisch in eine textuelle Form überführt und
 * z.B. zwischen Client und Server transportiert werden. Bei GWT RPC ist diese
 * textuelle Notation in JSON (siehe http://www.json.org/) kodiert.
 * </p>
 * 
 */
public class BusinessObject implements Serializable{

	/**
	 * Deklaration der serialVersionUID zur Serialisierung der Objekte
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Eindeutiger Identifikationsschluessel
	 */
	private int id;
	
	/**
	 * Rueckgeben der eindeutigen ID
	 * @return Die eindeutige ID des Kontaktsperren-Objekts
	 */
	public int getID() {
		return this.id;
	}
	
	/**
	 * Setzen der eindeutigen ID
	 * @param id - die zu setzende ID des Kontaktsperren-Objekts
	 */
	public void setID(int id) {
		this.id = id;
	}
	
}
