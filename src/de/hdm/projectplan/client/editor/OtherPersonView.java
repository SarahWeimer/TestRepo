package de.hdm.projectplan.client.editor;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.projectplan.client.ClientsideSettings;
import de.hdm.projectplan.client.editor.AllProjectsView.GetAllProjectsCallback;
import de.hdm.projectplan.shared.AdministrationServiceAsync;
import de.hdm.projectplan.shared.bo.Project;
import de.hdm.projectplan.shared.bo.User;

public class OtherPersonView extends ProjectplanForm{

	private Project currentProject;
	
	private User currentUser = new User();
	
	public OtherPersonView(Project object, String string, User user) {
		this.currentProject = object;
		this.currentUser = user;
	
	}
	
	@Override
	protected String getHeadlineText() {
		// TODO Auto-generated method stub
		return "Personenansicht";
	}

	/**
	 * Buttons fuer die Seite angelegt
	 */
	final Button btnBack = new Button("Zurück");
	
	@Override
	protected void run() {
		// TODO Auto-generated method stub
		AdministrationServiceAsync adminService = ClientsideSettings.getAdministration();
		
		//Nutze die in dem object gespeicherte UserId um aus der DB die Infos zu holen ( GetUserById)
		adminService.getUserById(currentProject.getPLuserId(), new GetUserByIdCallback(this));
//		Window.alert("gib mir zeit");
			
	}
	
	public void information () {
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
		tbVorname.setText(currentUser.getVorname());
		tbVorname.setEnabled(false);
		
		grid.setWidget(0, 0, lbVorname);
		grid.setWidget(0, 1, tbVorname);
	
		Label lbNachname = new Label("Nachname:");
		lbNachname.setStylePrimaryName("labelName");
		final TextBox tbNachname = new TextBox();
		tbNachname.setStylePrimaryName("textbox");
		tbNachname.setText(currentUser.getNachname());
		tbNachname.setEnabled(false);
		
		grid.setWidget(1, 0, lbNachname);
		grid.setWidget(1, 1, tbNachname);
		
		Label lbEmail = new Label("Email:");
		lbEmail.setStylePrimaryName("labelName");
		final TextBox tbEmail = new TextBox();
		tbEmail.setStylePrimaryName("textbox");
		tbEmail.setText(currentUser.getEmail());
		tbEmail.setEnabled(false);
		
		grid.setWidget(2, 0, lbEmail);
		grid.setWidget(2, 1, tbEmail);

		Label lbTaetigkeit = new Label("Tätigkeit:");
		lbTaetigkeit.setStylePrimaryName("labelName");
		final TextBox tbTaetigkeit = new TextBox();
		tbTaetigkeit.setStylePrimaryName("textbox");
		tbTaetigkeit.setText(currentUser.getArbeitsgebiet());
		tbTaetigkeit.setEnabled(false);
		
		grid.setWidget(3, 0, lbTaetigkeit);
		grid.setWidget(3, 1, tbTaetigkeit);
	
		Label lbInteresse = new Label("Interesse an:");
		lbInteresse.setStylePrimaryName("labelName");
		final TextBox tbInteresse = new TextBox();
		tbInteresse.setStylePrimaryName("textbox");
		tbInteresse.setText(currentUser.getInteresse());
		tbInteresse.setEnabled(false);
		
		grid.setWidget(4, 0, lbInteresse);
		grid.setWidget(4, 1, tbInteresse);
		

		// Panel für die Buttons hinzugefuegt und dem Details Panel zugewiesen
		HorizontalPanel hpButtons = new HorizontalPanel();
		vpDetails.add(hpButtons);
		
		/**
		 * Buttons der Seite Style hinzugefuegt und dem Panel zugeordnet
		 */
		btnBack.setStylePrimaryName("button5");
		hpButtons.add(btnBack);
		
		/**
		 * Click-Methode fuer Back Button
		 * wenn dieser betaetigt wird kommt er zurueck zur Projekt-Uebersicht
		 */
		btnBack.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				ProjectplanForm back = new AllProjectsView();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").setWidth("65%");
				RootPanel.get("Details").add(back);
			}
			
		});	
	}
	
	class GetUserByIdCallback implements AsyncCallback<User> {

		private ProjectplanForm projectplanForm = null;
		
		public GetUserByIdCallback(ProjectplanForm pf) {
			this.projectplanForm = pf;
		}
		
		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			this.projectplanForm.append("Fehler bei der Abfrage " + caught.getMessage());
		}

		@Override
		public void onSuccess(User result) {
			// TODO Auto-generated method stub
			currentUser = result;
			information();
	
			
			
		}
		
	}

}
