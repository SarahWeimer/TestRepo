package de.hdm.projectplan.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;


import de.hdm.projectplan.server.db.DBConnection;
import de.hdm.projectplan.shared.bo.Project;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import com.google.appengine.api.utils.SystemProperty;

import java.util.Vector;

/**
 * Die Mapper-Klasse ProjectMapper stellt eine Schnittstelle zwischen
 * Applikation und Datenbank dar. Die zu persistierenden Projekte werden
 * hier auf eine relationale Ebene projiziert. Die abzurufenden Projekte
 * werden aus den relationalen Tabellen zusammengestellt.
 * 
 */
public class ProjectMapper {

	/**
	 * Instanziieren des Mappers
	 */
	private static ProjectMapper projectMapper = null;

	/**
	 * Mithilfe des <code>protected</code>-Attributs im Konstruktor wird
	 * verhindert, dass von anderen Klassen eine neue Instanz der Klasse
	 * geschaffen werden kann.
	 */
	protected ProjectMapper() {
	}
	
	/**
	 * Aufruf eines Projekt-Mappers fuer Klassen, die keinen Zugriff auf den
	 * Konstruktor haben.
	 * 
	 * @return Einzigartige Mapper-Instanz zur Benutzung in der
	 *         Applikationsschicht
	 */
	public static ProjectMapper projectMapper() {
		if (projectMapper == null) {
			projectMapper = new ProjectMapper();
		}

		return projectMapper;
	}

	
	/**
	 * Auslesen aller Projekte
	 * @return
	 */
	public Vector<Project> findAll() {
		// TODO Auto-generated method stub
		Connection con = DBConnection.connection();

	    // Ergebnisvektor vorbereiten
	    Vector<Project> result = new Vector<Project>();

	    try {
	      Statement stmt = con.createStatement();

	      //ResultSet rs = stmt.executeQuery("SELECT projectId, title, PLuserId, participant, startdate, enddate, content FROM project "
	        //  + " ORDER BY title");
	    ResultSet rs = stmt.executeQuery("SELECT * FROM projektplansystem.project INNER JOIN projektplansystem.user ON projektplansystem.project.PLuserId = projektplansystem.user.userId ");
					

	      // Für jeden Eintrag im Suchergebnis wird nun ein Projekt-Objekt erstellt.
	      while (rs.next()) {
	    	Project p = new Project();
	    	p.setID(rs.getInt("projectId"));
	    	p.setTitle(rs.getString("title"));
	    	p.setPLuserId(rs.getInt("PLuserId"));
	    	p.setProjectleader(rs.getString("lastname"));
	    	p.setParticipant(rs.getString("participant"));
	    	p.setStartdatum(rs.getDate("startdate"));
	    	p.setEnddatum(rs.getDate("enddate"));
	    	p.setContent(rs.getString("content"));
	       
	        // Hinzufügen des neuen Objekts zum Ergebnisvektor
	        result.addElement(p);
	      }
	    }
	    catch (SQLException e2) {
	      e2.printStackTrace();
	    }

	    // Ergebnisvektor zurückgeben
	    return result;
	}
	
	/**
	 * Read-Methode - Anhand einer vorgegebenen id wird das dazu gehoerige
	 * Projekt in der Datenbank gesucht.
	 * 
	 * @param id
	 *            Die id, zu der der Eintrag aus der DB gelesen werden soll
	 * @return Das durch die id referenzierte Projekt-Objekt
	 * 
	 */

	public Project findByKey(int id) {
		/**
		 * DB-Verbindung holen
		 */
		Connection con = DBConnection.connection();

		try {
			/**
			 * Leeres SQL-Statement (JDBC) anlegen
			 */
			Statement stmt = con.createStatement();

			/**
			 * Statement ausfuellen und als Query an die DB schicken
			 */
			ResultSet rs = stmt.executeQuery(
					"SELECT idProject, title, PLuserId, startdate, enddate FROM project " 
							+ "WHERE id=" + id);

			/**
			 * Da id Primaerschluessel ist, kann max. nur ein Tupel
			 * zurueckgegeben werden. Pruefe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				/**
				 * Ergebnis-Tupel in Objekt umwandeln
				 */
				Project pr = new Project();
				pr.setID(rs.getInt("id"));
				pr.setTitle(rs.getString("title"));
				pr.setPLuserId(rs.getInt("PLuserId"));
				pr.setStartdatum(rs.getDate("startdate"));
				pr.setEnddatum(rs.getDate("enddate"));

				return pr;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}


	public Project insert(Project project) {
		
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();

			/*
			 * Zunächst schauen wir nach, welches der höchste Primärschlüsel ist
			 */

			ResultSet rs = stmt.executeQuery("SELECT MAX(projectId) AS maxid " 
						+ "FROM project");
			if (rs.next()) {
				/*
				 * c erhält den bisher maximalen, nun um 1 inkrememtierten
				 * Primärschlüssel
				 */
				project.setID(rs.getInt("maxid") + 1);
										
				stmt = con.createStatement();
				
				SimpleDateFormat mySQLformat = new SimpleDateFormat("yyyy-MM-dd");
							
				String startdate = mySQLformat.format(project.getStartdatum());
				String enddate = mySQLformat.format(project.getEnddatum());

				// Jetzt erfolgt Einfügeoption

				stmt.executeUpdate("INSERT INTO project (projectId, title, PLuserId, participant, content, startdate, enddate)" 
						+ "VALUES (\""
						+ project.getID()
						+ "\",\"" 
						+ project.getTitle()  
						+ "\",\""
						+ project.getPLuserId()
						+ "\",\""
						+ project.getParticipant()  
						+ "\",\""
						+ project.getContent()  
						+ "\",\""
						+ startdate  
						+ "\",\""
						+ enddate
						+ "\")");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return project;
	}

	/**
	 * Edit-Methode - Ein Projekt wird uebergeben und die zugehoerigen Werte in
	 * ein SQL-Statement geschrieben, welches ausgefuehrt wird, um die
	 * Informationswerte des Projekts in der Datenbank zu aktualisieren.
	 * 
	 */
	public Project edit(Project p) {
		// TODO Auto-generated method stub
		
		/**
		 * DB-Verbindung holen & erzeugen eines neuen SQL-Statements
		 */
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			/*
			 * Statement ausfuellen und als Query an die DB schicken
			 */
			stmt.executeUpdate("UPDATE project SET PLuserId=\"" + p.getPLuserId() + "\", title=\"" + p.getTitle()
							+ "\", participant=\"" + p.getParticipant() + "\", content=\"" + p.getContent() 
							+ "\" WHERE projectId=" + p.getID());
		} catch (SQLException e) {
			e.printStackTrace();
			}
		
		return p;
	}

	/**
	 * Loeschen der Daten eines Projekt-Objekts aus der Datenbank
	 * @param p 
	 */
	public void delete(int id) {
		// TODO Auto-generated method stub
		Connection con = DBConnection.connection();

	    try {
	      Statement stmt = con.createStatement();

	      stmt.executeUpdate("DELETE FROM project WHERE projectId=" + id);

	    }
	    catch (SQLException e2) {
	      e2.printStackTrace();
	    }
	}

	
	
}
