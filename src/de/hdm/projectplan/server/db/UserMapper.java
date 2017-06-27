package de.hdm.projectplan.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Logger;

import de.hdm.projectplan.shared.bo.User;
import de.hdm.projectplan.server.db.DBConnection;

import java.util.Vector;

/**
 * Die Mapper-Klasse UserMapper stellt eine Schnittstelle zwischen Applikation
 * und Datenbank dar. Die zu persistierenden User werden hier auf eine
 * relationale Ebene projiziert. Die abzurufenden Notizen werden aus den
 * relationalen Tabellen zusammengestellt.
 * 
 */
public class UserMapper {

	/**
	 * Instanziieren des Mappers
	 */
	private static UserMapper userMapper = null;

	/**
	 * Mithilfe des <code>protected</code>-Attributs im Konstruktor wird
	 * verhindert, dass von anderen Klassen eine neue Instanz der Klasse
	 * geschaffen werden kann.
	 */
	protected UserMapper() {
	}

	/**
	 * Aufruf eines User-Mappers fuer Klassen, die keinen Zugriff auf den
	 * Konstruktor haben.
	 * 
	 * @return Einzigartige Mapper-Instanz zur Benutzung in der
	 *         Applikationsschicht
	 */
	public static UserMapper userMapper() {
		if (userMapper == null) {
			userMapper = new UserMapper();
		}

		return userMapper;
	}
	
	
	 /**
	   * Suchen eines Nutzers mit vorgegebener ID. Da diese eindeutig ist,
	   * wird genau ein Objekt zurueckgegeben.
	   * 
	   * @param id Primärschlüsselattribut (->DB)
	   * @return User-Objekt, das dem übergebenen Schlüssel entspricht, null bei
	   *         nicht vorhandenem DB-Tupel.
	   */
	  public User findByKey(int id) {
	    // DB-Verbindung holen
	    Connection con = DBConnection.connection();

	    try {
	      // Leeres SQL-Statement (JDBC) anlegen
	      Statement stmt = con.createStatement();

	      // Statement ausfüllen und als Query an die DB schicken
	      ResultSet rs = stmt
	          .executeQuery("SELECT userId, firstname, lastname, email, workarea, interest FROM user "
	              + "WHERE userId=" + id);

	      /*
	       * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
	       * werden. Prüfe, ob ein Ergebnis vorliegt.
	       */
	      if (rs.next()) {
	        // Ergebnis-Tupel in Objekt umwandeln
	    	  User u = new User();
	    	  u.setID(rs.getInt("userId"));
	    	  u.setVorname(rs.getString("firstname"));
	    	  u.setNachname(rs.getString("lastname"));
	    	  u.setEmail(rs.getString("email"));
	    	  u.setArbeitsgebiet(rs.getString("workarea"));
	    	  u.setInteresse(rs.getString("interest"));
	       
	        return u;
	      }
	    }
	    catch (SQLException e) {
	      e.printStackTrace();
	      return null;
	    }

	    return null;
	  }
	  
	  
	  public User getIdByName(String name){
		  Connection con = DBConnection.connection();
		  
		  try{
			  Statement stmt = con.createStatement();
			  ResultSet rs = stmt.executeQuery("SELECT userId, lastname FROM user WHERE lastname=\"" + name + "\"");
			  if (rs.next()) {

			    	  User u = new User();
			    	  u.setID(rs.getInt("userId"));
			    	  u.setNachname(rs.getString("lastname"));
			    	  
			        return u;
			      }
		  }
		  catch (SQLException e) {
		      e.printStackTrace();
		      return null;
		    }
		  
		return null;
		  
	  }
	  
	  /**
	   * Auslesen aller User.
	   * 
	   * @return Ein Vektor mit User-Objekten, die sämtliche User
	   *         repräsentieren. Bei evtl. Exceptions wird ein partiell gefuellter
	   *         oder ggf. auch leerer Vetor zurückgeliefert.
	   */
	  public Vector<User> findAll() {
	    Connection con = DBConnection.connection();
	    // Ergebnisvektor vorbereiten
	    Vector<User> result = new Vector<User>();

	    try {
	      Statement stmt = con.createStatement();

	      ResultSet rs = stmt.executeQuery("SELECT userId, firstname, lastname, email, workarea, interest "
	          + "FROM user " + "ORDER BY lastname");

	      // Für jeden Eintrag im Suchergebnis wird nun ein User-Objekt
	      // erstellt.
	      while (rs.next()) {
	    	  User u = new User();
	    	  u.setID(rs.getInt("userId"));
	    	  u.setVorname(rs.getString("firstname"));
	    	  u.setNachname(rs.getString("lastname"));
	    	  u.setEmail(rs.getString("email"));
	    	  u.setArbeitsgebiet(rs.getString("workarea"));
	    	  u.setInteresse(rs.getString("interest"));

	        // Hinzufügen des neuen Objekts zum Ergebnisvektor
	        result.addElement(u);
	      }
	    }
	    catch (SQLException e) {
	      e.printStackTrace();
	    }

	    // Ergebnisvektor zurückgeben
	    return result;
	  }

	public Vector<User> findByLastName(String lastName) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * die Methode insert ist fuer das Erstellen eines Nutzers in der Datenbank	
	 * 
	 * FUNKTIONIERT nur bei der Email wird aktuell "null" gespeichert und nicht die angegebene Email Adresse
	 * @param u
	 * @return
	 */
	public User insert(User u) {
		// TODO Auto-generated method stub
		Connection con = DBConnection.connection();

	    try {
	    		    	
	      Statement stmt = con.createStatement();

	      /*
	       * Zunächst schauen wir nach, welches der momentan höchste
	       * Primärschlüsselwert ist.
	       */
	      ResultSet rs = stmt.executeQuery("SELECT MAX(userId) AS maxid "
	          + "FROM user ");

	      // Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
	      if (rs.next()) {
	        /*
	         * c erhält den bisher maximalen, nun um 1 inkrementierten
	         * Primärschlüssel.
	         */
	        u.setID(rs.getInt("maxid") + 1);

	        stmt = con.createStatement();

	        // Jetzt erst erfolgt die tatsächliche Einfügeoperation
	        stmt.executeUpdate("INSERT INTO user (userId, firstname, lastname, email, workarea, interest)" 
					+ "VALUES (\""
					+ u.getID()
					+ "\",\"" 
					+ u.getVorname()  
					+ "\",\""
					+ u.getNachname()
					+ "\",\""
					+ u.getEmail()  
					+ "\",\""
					+ u.getArbeitsgebiet()  
					+ "\",\""
					+ u.getInteresse()  
					+ "\")");
	      }
	    }
	    catch (SQLException e) {
	      e.printStackTrace();
	    }

	    /*
	     * Rückgabe, des evtl. korrigierten Users.
	     * 
	     * HINWEIS: Da in Java nur Referenzen auf Objekte und keine physischen
	     * Objekte übergeben werden, wäre die Anpassung des User-Objekts auch
	     * ohne diese explizite Rückgabe au�erhalb dieser Methode sichtbar. Die
	     * explizite Rückgabe von c ist eher ein Stilmittel, um zu signalisieren,
	     * dass sich das Objekt evtl. im Laufe der Methode verändert hat.
	     */
	    return u;
	}
	
	/**
	   * Wiederholtes Schreiben eines Objekts in die Datenbank.
	   * 
	   * @param c das Objekt, das in die DB geschrieben werden soll
	   * @return das als Parameter übergebene Objekt
	   */
	  public User update(User u) {
	    Connection con = DBConnection.connection();

	    try {
	      Statement stmt = con.createStatement();

	      stmt.executeUpdate("UPDATE user " + "SET firstname=\""
	          + u.getVorname() + "\", " + "lastname=\"" + u.getNachname() 
	          + "\", " + "email=\"" + u.getEmail() 
	          + "\", " + "workarea=\"" + u.getArbeitsgebiet()
	          + "\", " + "interest=\"" + u.getInteresse() + "\" "
	          + "WHERE id=" + u.getID());
	      
	    }
	    catch (SQLException e) {
	      e.printStackTrace();
	    }

	    // Um Analogie zu insert(User u) zu wahren, geben wir u zurück
	    return u;
	  }

	public User getByEmail(String email) {
		// DB-Verbindung holen
	    Connection con = DBConnection.connection();
	    

	    try {
	      // Leeres SQL-Statement (JDBC) anlegen
	      Statement stmt = con.createStatement();

	      // Statement ausfüllen und als Query an die DB schicken
	      ResultSet rs = stmt
	          .executeQuery("SELECT idUser, firstname, lastname, email, workarea, interest FROM user "
	              + "WHERE email=" + email);

	      /*
	       * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
	       * werden. Prüfe, ob ein Ergebnis vorliegt.
	       */
	      while (rs.next()) {
	        // Ergebnis-Tupel in Objekt umwandeln
	    	  User u = new User();
	    	  u.setID(rs.getInt("id"));
	    	  u.setVorname(rs.getString("firstname"));
	    	  u.setNachname(rs.getString("lastname"));
	    	  u.setEmail(rs.getString("email"));
	    	  u.setArbeitsgebiet(rs.getString("workarea"));
	    	  u.setInteresse(rs.getString("interest"));
	       
	    	  return u;
	    	
	      }
	    }
	    catch (SQLException e) {
	      e.printStackTrace();
	    }

	    return null;
	}
	
}
