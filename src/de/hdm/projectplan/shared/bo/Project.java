package de.hdm.projectplan.shared.bo;

import java.util.Date;

public class Project extends BusinessObject {
	
	/**
	 * Deklaration der serialVersionUID zur Serialisierung der Objekte
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Attribute
	 */
		
		/**
		 * Die Project-ID, die die Freigabe erhaelt
		 */
		private int projectId;
		
		/**
		 * Der Titel des Projekts
		 */
		private String title;
		
		private int PLuserId;
		
		private String projectleader;
		
		private String participant;
		
		/**
		 * Das Startdatum des Projekts
		 */
		private Date startdatum;
		
		/**
		 * Das Enddatum des Projekts
		 */
		private Date enddatum;
		
		private String content;
		
		/**
		 * Die Beschreibung des Projekts
		 */
		private String beschreibung;
		
		
	/**
	 * Methoden
	 */
		
		/**
		 * Rueckgeben der porjectId, fuer die die Freigabe gilt
		 * 
		 * @return projectId, die freigegeben wird.
		 */
		public int getProjectId() {
			return this.projectId;
		}
		
		/**
		 * Setzen der projectId, fuer die die Freigabe gilt
		 * 
		 * @return projectId,
		 */
		public void setProjectId(int userId) {
			this.projectId = userId;
		}

		/**
		 * Rueckgeben des Titel des Projekts
		 * 
		 * @return title
		 */
		public String getTitle() {
			return title;
		}

		/**
		 * Setzen des Titel des Projekts
		 * 
		 * @param title
		 */
		public void setTitle(String title) {
			this.title = title;
		}


		/**
		 * Rueckgeben des Startdatum des Projekts
		 * 
		 * @return startdatum
		 */
		public Date getStartdatum() {
			return startdatum;
		}

		/**
		 * Setzen des Startdatums des Projekts
		 * 
		 * @param startdatum
		 */
		public void setStartdatum(Date startdatum) {
			this.startdatum = startdatum;
		}

		/**
		 * Rueckgeben des Enddatum des Projekts
		 * 
		 * @return enddatum
		 */
		public Date getEnddatum() {
			return enddatum;
		}

		/**
		 * Setzen des Enddatum des Projekts
		 * 
		 * @param enddatum
		 */
		public void setEnddatum(Date enddatum) {
			this.enddatum = enddatum;
		}

		/**
		 * Rueckgeben der Beschreibung des Projekts
		 * 
		 * @return beschreibung
		 */
		public String getBeschreibung() {
			return beschreibung;
		}

		/**
		 * Setzen der Beschreibung des Projekts
		 * 
		 * @param beschreibung
		 */
		public void setBeschreibung(String beschreibung) {
			this.beschreibung = beschreibung;
		}


		public void setOwner(String emailAddress) {
			// TODO Auto-generated method stub
			
		}

		public int getPLuserId() {
			return PLuserId;
		}

		public void setPLuserId(int projectleader) {
			this.PLuserId = projectleader;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getParticipant() {
			return participant;
		}

		public void setParticipant(String participant) {
			this.participant = participant;
		}

		public String getProjectleader() {
			return projectleader;
		}

		public void setProjectleader(String projectleader) {
			this.projectleader = projectleader;
		}
		


}
