package de.hdm.projectplan.client.editor;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.projectplan.shared.bo.User;

public class DetailsUserView extends ProjectplanForm{

	/**
	 * Die Instanz des aktuellen Projekts ermoeglicht den schnellen Zugriff auf
	 * dessen Projekteigenschaften.
	 */
	private User currentUser = null;
	private String originView = null;
	
	/**
	 * Parametrisierter Konstruktor der View
	 * 
	 * @param object
	 *            das aktuelle Project, das vom Benutzer ausgewaehlt wurde
	 * @param string
	 *            die urspruengliche View, aus der der Benutzer in diese
	 *            gesprungen ist
	 */
	public DetailsUserView(User object, String string) {
		this.currentUser = object;
		this.originView = string;
	}
	
	@Override
	protected String getHeadlineText() {
		// TODO Auto-generated method stub
		return "Profil von " + this.currentUser.getVorname() + " " + this.currentUser.getNachname();
	}

	@Override
	protected void run() {
		// TODO Auto-generated method stub
		
		// Panels angelegt für die Ansicht
		VerticalPanel vpDetails = new VerticalPanel();
		this.add(vpDetails);
		HorizontalPanel hpDetails = new HorizontalPanel();
		vpDetails.add(hpDetails);
				
		/**
		 * Zurueck-Button zur Übersicht		
		 */
		final Button btnBack = new Button ("Zurück");
		btnBack.setStylePrimaryName("button5");
		hpDetails.add(btnBack);
		
		btnBack.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
				btnBack.setEnabled(false);
				ProjectplanForm abbrechen = new AllProjectsView();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").setWidth("65%");
				RootPanel.get("Details").add(abbrechen);
			}
			
		});
				
	}

}
