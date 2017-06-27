package de.hdm.projectplan.client.editor;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

import de.hdm.projectplan.client.ClientsideSettings;
import de.hdm.projectplan.client.editor.NewProjectView.GetAllUsersCallback;
import de.hdm.projectplan.shared.AdministrationServiceAsync;
import de.hdm.projectplan.shared.bo.Project;
import de.hdm.projectplan.shared.bo.User;

public class DetailsProjectView extends ProjectplanForm{

	
	/**
	 * Die Instanz des aktuellen Projekts ermoeglicht den schnellen Zugriff auf
	 * dessen Projekteigenschaften.
	 */
	private Project currentProject = null;
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
	public DetailsProjectView(Project object, String string) {
		this.currentProject = object;
		this.originView = string;
	}
		
	MultiWordSuggestOracle oracleProjecleader = new MultiWordSuggestOracle();
	
	@Override
	protected String getHeadlineText() {
		// TODO Auto-generated method stub
		return "Projekt Details von: " + this.currentProject.getTitle(); 
	}

	@Override
	protected void run() {
		// TODO Auto-generated method stub
		AdministrationServiceAsync adminService = ClientsideSettings.getAdministration();
		adminService.getAllUsers(new GetAllUsersCallback(this));
		
		// Panels angelegt für die Ansicht
		VerticalPanel vpDetails = new VerticalPanel();
		this.add(vpDetails);
		HorizontalPanel hpDetails = new HorizontalPanel();
		vpDetails.add(hpDetails);
			
		
		/**
		 * Zurueck-Button zur Projekt-Uebersicht
		 */
		final Button btnBack = new Button ("Zurück");
		btnBack.setStylePrimaryName("button5");
		hpDetails.add(btnBack);
		
		/**
		 * Click-Methode fuer Zurueck Button
		 * wenn dieser betaetigt wird kommt er zurueck zur Projekt-Uebersicht
		 */
		btnBack.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub

				btnBack.setEnabled(false);
				btnBack.setStylePrimaryName("tngly-disabledButton");
				ProjectplanForm abbrechen = new AllProjectsView();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").setWidth("65%");
				RootPanel.get("Details").add(abbrechen);
			}
			
		});
		
		
		// Tabelle fuer die gerade Anordnung der Labels und Textboxes
		Grid grid = new Grid(3, 2);
		vpDetails.add(grid);
		
		Grid grid2 = new Grid(1, 3);
		vpDetails.add(grid2);
		
		Grid grid3 = new Grid(1, 2);
		vpDetails.add(grid3);
		
		/**
		 * Panels, Labels und Textboxen angelegt (Projektdaten)
		 */
		/**
		 * Panels und Labels angelegt
		 */
		Label lbTitel = new Label("Projektname:");
		lbTitel.setStylePrimaryName("labelName");
		final TextBox tbTitel = new TextBox();
		tbTitel.setStylePrimaryName("textbox");
//		tbTitel.setEnabled(false);
		tbTitel.setText(currentProject.getTitle());
		
		
		grid.setWidget(0, 0, lbTitel);
		grid.setWidget(0, 1, tbTitel);
		
		Label lbProjectLeader = new Label("Projektleiter:");
		lbProjectLeader.setStylePrimaryName("labelName");
		
		final SuggestBox sbProjectLeader = new SuggestBox(oracleProjecleader);
		
		sbProjectLeader.ensureDebugId("cwSuggestBoxUser");
		
//		final TextBox tbProjectLeader = new TextBox();
		sbProjectLeader.setStylePrimaryName("textbox");
//		tbProjectLeader.setEnabled(false);
		sbProjectLeader.setText(currentProject.getProjectleader());
		
		grid.setWidget(1, 0, lbProjectLeader);
		grid.setWidget(1, 1, sbProjectLeader);
		
		Label lbParticipant = new Label("Teilnehmer:");
		lbParticipant.setStylePrimaryName("labelName");
		final TextBox tbParticipant = new TextBox();	//HIER KOMMEN DIE USER REIN!
		tbParticipant.setStylePrimaryName("textbox");
//		tbParticipant.setEnabled(false);
		tbParticipant.setText(currentProject.getParticipant());
		
		grid.setWidget(2, 0, lbParticipant);
		grid.setWidget(2, 1, tbParticipant);
		
		Label lbTime = new Label("Zeitraum (Von- Bis-):");
		lbTime.setStylePrimaryName("labelName");
		final TextBox tbTimeFrom = new TextBox();
		final TextBox tbTimeTo = new TextBox();
		tbTimeFrom.setStylePrimaryName("textbox");
		tbTimeTo.setStylePrimaryName("textbox");
		tbTimeFrom.setEnabled(false);
		tbTimeTo.setEnabled(false);
		tbTimeFrom.setText(currentProject.getStartdatum().toString());
		tbTimeTo.setText(currentProject.getEnddatum().toString());
		
		grid2.setWidget(0, 0, lbTime);
		grid2.setWidget(0, 1, tbTimeFrom);
		grid2.setWidget(0, 2, tbTimeTo);

		Label lbContent = new Label("Beschreibung:");
		lbContent.setStylePrimaryName("labelName");
		final TextArea tbContent = new TextArea();
		tbContent.setStylePrimaryName("textarea");
//		tbContent.setEnabled(false);
		tbContent.setText(currentProject.getContent());
		
		grid3.setWidget(0, 0, lbContent);
		grid3.setWidget(0, 1, tbContent);
		
		
		/**
		 * Bearbeiten + Loeschen-Button zum bearbeiten und loeschen des Projekts
		 */
		// Panel für die Buttons hinzugefuegt und dem Details Panel zugewiesen
		HorizontalPanel hpButtons = new HorizontalPanel();
		vpDetails.add(hpButtons);
		
		final Button btnSave = new Button ("Speichern");
		btnSave.setStylePrimaryName("button5");
		hpButtons.add(btnSave);
		
		final Button btnDelete = new Button("Projekt Löschen");
		btnDelete.setStylePrimaryName("button");
		hpButtons.add(btnDelete);
		
		
		// fuer "updateProjectCallback" WICHTIG
		final ProjectplanForm projectplanForm = this;
		
		/**
		 * onClick Methoden zum Bearbeiten und Loeschen des Projekts
		 */
		btnSave.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub

				/*
				 * Speichern der eingegebenen Werte blockieren, um
				 * Mehrfach-Klicks und daraus entstehende, unnoetige Eintraege in
				 * der Datenbank zu verhindern.
				 */
				btnSave.setEnabled(false);

				currentProject.setTitle(tbTitel.getText());
				currentProject.setProjectleader(sbProjectLeader.getText());
				currentProject.setParticipant(tbParticipant.getText());
				currentProject.setContent(tbContent.getText());
				
//				Window.alert(tbTitel.getText() + "HALLOOOOO");
				
				AdministrationServiceAsync adminService = ClientsideSettings.getAdministration();
				adminService.editProject(currentProject, new updateProjectCallback(projectplanForm));
			}
			
		});
		
		btnDelete.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub

//				Window.alert("Projekt Löschen, ... sind Sie sich sicher?! ...");
				btnDelete.setEnabled(false);
				AdministrationServiceAsync adminService = ClientsideSettings.getAdministration();
				adminService.deleteProject(currentProject, new deleteProjectCallback(projectplanForm));
				
			}
			
		});
		
	}
	
	class updateProjectCallback implements AsyncCallback<Void> {

		private ProjectplanForm projectplanForm = null;
		
		public updateProjectCallback (ProjectplanForm pf) {
			this.projectplanForm = pf;	
		}
		
		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			this.projectplanForm.append("Fehler bei der Abfrage " + caught.getMessage());
		}

		@Override
		public void onSuccess(Void result) {
			// TODO Auto-generated method stub
			this.projectplanForm.append("Projekt erfolgreich überarbeitet");
			
			ProjectplanForm update = new AllProjectsView();
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(update);
		}
		
	}
	
	class deleteProjectCallback implements AsyncCallback<Void> {

		private ProjectplanForm projectplanForm = null;
		
		public deleteProjectCallback (ProjectplanForm pf) {
			this.projectplanForm = pf;	
		}
		
		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			this.projectplanForm.append("Fehler bei der Abfrage " + caught.getMessage());
		}

		@Override
		public void onSuccess(Void result) {
			// TODO Auto-generated method stub
			this.projectplanForm.append("Projekt erfolgreich gelöscht");
			
			ProjectplanForm update = new AllProjectsView();
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(update);			
		}
		
	}
	
	class GetAllUsersCallback implements AsyncCallback<Vector<User>> {

		private ProjectplanForm projectplanForm = null;
		
		public GetAllUsersCallback(ProjectplanForm pf) {
			this.projectplanForm = pf;
		}
		
		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			this.projectplanForm.append("Fehler bei der Abfrage " + caught.getMessage());
		}

		@Override
		public void onSuccess(Vector<User> user) {
			// TODO Auto-generated method stub
			for(User u : user) {
				oracleProjecleader.add(u.getNachname());
			}			
		}	
	}

}
