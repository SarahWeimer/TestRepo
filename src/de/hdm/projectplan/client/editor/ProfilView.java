package de.hdm.projectplan.client.editor;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;

import de.hdm.projectplan.client.editor.AllProjectsView;
import de.hdm.projectplan.client.editor.ProjectplanForm;
import de.hdm.projectplan.client.ClientsideSettings;
import de.hdm.projectplan.shared.AdministrationServiceAsync;
import de.hdm.projectplan.shared.bo.Project;
import de.hdm.projectplan.shared.bo.User;

public class ProfilView extends ProjectplanForm{

	
//	/**
//	 * Die Instanz des aktuellen Users ermoeglicht den schnellen Zugriff auf
//	 * dessen Eigenschaften.
//	 */
//	private User currentUser = null;
//	private String originView = null;
//	
//	/**
//	 * Parametrisierter Konstruktor der View
//	 * 
//	 * @param object
//	 *            das aktuelle Project, das vom Benutzer ausgewaehlt wurde
//	 * @param string
//	 *            die urspruengliche View, aus der der Benutzer in diese
//	 *            gesprungen ist
//	 */
//	public ProfilView(User object, String string) {
//		this.currentUser = object;
//		this.originView = string;
//	}
	
	
	
	/**
	 * Buttons fuer die Seite angelegt
	 */
	final Button btnAbbrechen = new Button("Abbrechen");
	final Button btnSave = new Button("Änd. speichern");
	
	
	/////////////////////////////////////////////////////////
	
	@Override
	protected String getHeadlineText() {
		// TODO Auto-generated method stub
		return "Profilansicht"; // + currentUser.getVorname() + " " + currentUser.getNachname();
	}
	

	@Override
	protected void run() {
				
		/**
		 * Auslesen des aktuellen Users aus der Datenbank.
		 */
		AdministrationServiceAsync adminService = ClientsideSettings.getAdministration();
		adminService.getUser(ClientsideSettings.getLoginInfo().getEmailAddress(), new UseUser(this));
		
//		adminService.getUserByEmail(ClientsideSettings.getLoginInfo().getEmailAddress(), new UseUser(this));
		
//		adminService.getUserById(1, new UseUser(this));
		
		

		// Panels fuer die Details Uebersicht des Nutzers
		VerticalPanel vpDetails = new VerticalPanel();
		this.add(vpDetails);
		HorizontalPanel hpDetails = new HorizontalPanel();
		vpDetails.add(hpDetails);
				
		// Tabelle fuer die gerade Anordnung der Labels und Textboxes
		Grid grid = new Grid(5, 2);
		vpDetails.add(grid);
		
		/**
		 * Panels, Labels und Textboxen angelegt (Userdaten)
		 */
		Label lbVorname = new Label("Vorname:");
		lbVorname.setStylePrimaryName("labelName");
		final TextBox tbVorname = new TextBox();
		tbVorname.setStylePrimaryName("textbox");
//		tbVorname.setText(currentUser.getVorname());
//		tbVorname.setText(ClientsideSettings.getLoginInfo().getFirstName());
		
		grid.setWidget(0, 0, lbVorname);
		grid.setWidget(0, 1, tbVorname);
	
		Label lbNachname = new Label("Nachname:");
		lbNachname.setStylePrimaryName("labelName");
		final TextBox tbNachname = new TextBox();
		tbNachname.setStylePrimaryName("textbox");
//		tbNachname.setText(currentUser.getNachname());
		
		grid.setWidget(1, 0, lbNachname);
		grid.setWidget(1, 1, tbNachname);
		
		Label lbEmail = new Label("Email:");
		lbEmail.setStylePrimaryName("labelName");
		final TextBox tbEmail = new TextBox();
		tbEmail.setStylePrimaryName("textbox");
		tbEmail.setText(ClientsideSettings.getLoginInfo().getEmailAddress());
		tbEmail.setEnabled(false);
		
		grid.setWidget(2, 0, lbEmail);
		grid.setWidget(2, 1, tbEmail);

		Label lbTaetigkeit = new Label("Tätigkeit:");
		lbTaetigkeit.setStylePrimaryName("labelName");
		final TextBox tbTaetigkeit = new TextBox();
		tbTaetigkeit.setStylePrimaryName("textbox");
//		tbTaetigkeit.setText(currentUser.getArbeitsgebiet());
		
		grid.setWidget(3, 0, lbTaetigkeit);
		grid.setWidget(3, 1, tbTaetigkeit);
	
		Label lbInteresse = new Label("Interesse an:");
		lbInteresse.setStylePrimaryName("labelName");
		final TextBox tbInteresse = new TextBox();
		tbInteresse.setStylePrimaryName("textbox");
//		tbInteresse.setText(currentUser.getInteresse());
		
		grid.setWidget(4, 0, lbInteresse);
		grid.setWidget(4, 1, tbInteresse);
		

		// Panel für die Buttons hinzugefuegt und dem Details Panel zugewiesen
		HorizontalPanel hpButtons = new HorizontalPanel();
		vpDetails.add(hpButtons);
		
		/**
		 * Buttons der Seite Style hinzugefuegt und dem Panel zugeordnet
		 */
		btnAbbrechen.setStylePrimaryName("button5");
		hpButtons.add(btnAbbrechen);
		
		btnSave.setStylePrimaryName("button");
		hpButtons.add(btnSave);
		
		
		/**
		 * Click-Methode fuer Abbrechen Button
		 * wenn dieser betaetigt wird kommt er zurueck zur Projekt-Uebersicht
		 */
		btnAbbrechen.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub

				btnAbbrechen.setEnabled(false);
				btnAbbrechen.setStylePrimaryName("tngly-disabledButton");
				ProjectplanForm abbrechen = new AllProjectsView();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").setWidth("65%");
				RootPanel.get("Details").add(abbrechen);
			}
			
		});	
		
		
		// fuer "createUserCallback" WICHTIG
		final ProjectplanForm projectplanForm = this;
		
		btnSave.addClickHandler(new ClickHandler(){
					
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				User u = new User();
				u.setVorname(tbVorname.getText());
				u.setNachname(tbNachname.getText());
				u.setEmail(tbEmail.getText());
				u.setArbeitsgebiet(tbTaetigkeit.getText());
				u.setInteresse(tbInteresse.getText());
				
				AdministrationServiceAsync adminService = ClientsideSettings.getAdministration();
				adminService.createUser(u, new CreateUserCallback(projectplanForm));
				
				ProjectplanForm update = new AllProjectsView();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
			}
	
		});
		
		
//		adminService.getUserById(id, new UseCustomer(this));
		
		
	}
	
	class UseUser implements AsyncCallback<User> {

		private ProjectplanForm projectplanForm = null;
		
		public UseUser (ProjectplanForm pf) {
			this.projectplanForm = pf;
		}
		
		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			this.projectplanForm.append("Fehler bei der Abfrage " + caught.getMessage());
		}

		@Override
		public void onSuccess(User user) {
			// TODO Auto-generated method stub
//			if (user == null) {
//				AdministrationServiceAsync adminService = ClientsideSettings.getAdministration();
//				adminService.createUser(user, new CreateUserCallback(projectplanForm));
//			}
		}
		
	}
	
	class CreateUserCallback implements AsyncCallback<User> {

		private ProjectplanForm projectplanForm = null;
			
		public CreateUserCallback (ProjectplanForm pf) {
			this.projectplanForm = pf;
		}
		
		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			this.projectplanForm.append("Fehler bei der Abfrage " + caught.getMessage());
		}

		@Override
		public void onSuccess(User user) {
			// TODO Auto-generated method stub
			if (user != null) {
				this.projectplanForm.append("Nutzer " + user.getNachname() + " wurde angelegt." );
			}
			
		}
		
	}
	
///**
// * AsyncCallback fuer das Auslesen des aktuellen Nutzers aus der Datenbank	
// * @return
// */
//	private AsyncCallback<User> getUserByIdCallback() {
//			// TODO Auto-generated method stub
//		
//		AsyncCallback<User> asyncCallback = new AsyncCallback<User>() {
//
//			@Override
//			public void onFailure(Throwable caught) {
//				// TODO Auto-generated method stub
//			}
//
//			@Override
//			public void onSuccess(User result) {
//				// TODO Auto-generated method stub
//			}
//		};
//		return asyncCallback;
//	}
//	
	

////////////////////////////////////////////////////////////////////////////////
//	/**
//	 * Fuegt einen Datenadapter hinzu. Die aktuelle Anzeige wird mit Werten
//	 * befuellt.
//	 */
//	public void addDataDisplayUser(HasData<User> display) {
//		dataProviderUser.addDataDisplay(display);
//	}
//
////////////////////////////////////////////////////////////////////////////////
//	/**
//	 * Aktualisieren des Datenadapters
//	 */
//	public void refreshDisplaysUser() {
//		dataProviderUser.refresh();
//	}
//
////////////////////////////////////////////////////////////////////////////////


}
