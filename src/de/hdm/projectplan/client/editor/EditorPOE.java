package de.hdm.projectplan.client.editor;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.projectplan.client.ClientsideSettings;
import de.hdm.projectplan.client.LoginService;
import de.hdm.projectplan.client.LoginServiceAsync;
import de.hdm.projectplan.shared.AdministrationService;
import de.hdm.projectplan.shared.AdministrationServiceAsync;
import de.hdm.projectplan.shared.LoginInfo;
import de.hdm.projectplan.shared.bo.Project;
import de.hdm.projectplan.client.editor.ProjectplanForm;
import de.hdm.projectplan.client.editor.AllProjectsView;
import de.hdm.projectplan.client.editor.ProfilView;
import de.hdm.projectplan.client.editor.Impressum;

/**
 * EntryPoint Klasse fuer den Editor Client. Initialisierung der Navigation und
 * Ueberpruefung des eingeloggten Users mit der Datenbank, ob der Benutzer bereits
 * in der Datenbank gespeichert ist.
 * 
 */
public class EditorPOE implements EntryPoint{

//	/**
//	 * Die AdministrationService ermoeglicht die asynchrone Kommunikation mit der
//	 * Applikationslogik.
//	 */
//	private AdministrationServiceAsync adminService = ClientsideSettings.getAdministration();

	/**
	 * Der LoginService ermoeglicht die asynchrone Kommunikation mit der
	 * Applikationslogik.
	 */
	private final LoginServiceAsync loginService = GWT.create(LoginService.class);
	
	private final AdministrationServiceAsync adminService = GWT.create(AdministrationService.class);
	
	/**
	 * Die Instanz von LoginInfo dient als Hilfsklasse fuer das Login und stellt
	 * erforderliche Variablen und Operationen bereit.
	 */
	private LoginInfo loginInfo = null;
	

//	AdministrationServiceAsync adminService = GWT.create(de.hdm.projectplan.shared.AdministrationService.class);

	/**
	 * Deklaration, Definition und Initialisierung der Widgets.
	 */
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label("Please sign in to your Google Account to access the Editor module.");
	private Anchor signInLink = new Anchor("Sign In");
	
	private VerticalPanel vPanel = new VerticalPanel();
	private final Button btnProfil = new Button("Mein Profil");
	private final Button btnProject = new Button("Projekte");
	private final Button btnNewProject = new Button("Neues Projekt");
	private final Button btnImpressum = new Button("Impressum");
	private final Button btnLogout = new Button("Ausloggen");
	private final Button btnReport = new Button("Report Generator");
	private final HorizontalPanel hpDetails = new HorizontalPanel();
	
	

	/**
	 * Die Implementierung des Interface, um der Klasse zu ermoeglichen, als
	 * EntryPoint des Modules zu laden.
	 */
	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		
		/**
		 * Der AsyncCallback fuer die Anmeldung des Benutzers. Die
		 * Benutzerinformationen werden mithilfe der LoginInfo ausgegeben.
		 */
		loginService.login(GWT.getHostPageBaseURL() + "ProjectplanTest.html", new AsyncCallback<LoginInfo>() {

			@Override
			public void onFailure(Throwable error) {
			}

			@Override
			public void onSuccess(LoginInfo result) {
				
				loginInfo = result; 
				ClientsideSettings.setLoginInfo(result);
				
				if (loginInfo.isLoggedIn()) {
					loadProjectplan();
				} else {
					loadLogin ();
				}
			}
		});
	}
			
		public void loadProjectplan () {
		
		/**
		 * Die Panels werden erzeugt und angeordnet und in das RootPanel eingefuegt.
		 */
		
		/*
		 * Zuweisung von Styles an die jeweiligen Widgets.
		 */
		btnProfil.setStylePrimaryName("button");
		btnProject.setStylePrimaryName("button");
		btnNewProject.setStylePrimaryName("button");
		btnImpressum.setStylePrimaryName("button");
		btnLogout.setStylePrimaryName("button2");
		btnReport.setStylePrimaryName("button3");
		
		
		/*
		 * Zuweisung des jeweiligen Child Widget zum Parent Widget.
		 */
		vPanel.add(btnProfil);
		vPanel.add(btnProject);
		vPanel.add(btnNewProject);
		vPanel.add(btnImpressum);
		vPanel.add(btnLogout);;
		vPanel.add(btnReport);
		RootPanel.get("Navigator").add(vPanel);
		RootPanel.get("Details").add(hpDetails);
		
		
		/*
		 * Zuweisung der neuen Ansicht zum Parent Widget. 
		 * ANSICHT DER ERSTEN SEITE BEIM STARTEN DES PROGRAMMS
		 */
		ProjectplanForm projectplan = new AllProjectsView();
		RootPanel.get("Details").clear();
		RootPanel.get("Details").add(projectplan);
		
		
		/*
		 * Zuweisung der ClickHandler an die jeweiligen Buttons.
		 */
		btnProfil.addClickHandler(new ClickHandler () {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
				ProjectplanForm profilView = new ProfilView();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(profilView);
			}
			
		});
		
		
		btnProject.addClickHandler(new ClickHandler () {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
				ProjectplanForm allProjectsView = new AllProjectsView();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(allProjectsView);
			}
			
		});
		
		
		btnNewProject.addClickHandler(new ClickHandler () {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
				ProjectplanForm newProjectView = new NewProjectView();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(newProjectView);
			}
			
		});
		
		btnImpressum.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub

				ProjectplanForm vpImpressum = new Impressum();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(vpImpressum);
			}
			
		});
		
		btnLogout.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				/*
				 * Laden der Logout-URL der Google Accounts API und Anzeige des
				 * LoginPanel.
				 */
				RootPanel.get("Details").setWidth("65%");
				Window.open(ClientsideSettings.getLoginInfo().getLogoutUrl(), "_self", "");
			}
			
		});
		
		btnReport.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				Window.alert("simsalabim Report Generator öffnen");
			}
			
		});
		
		
	}

	
	/**
	 * Laden des Login Panels fuer die Anmeldung des Benutzers
	 */
	private void loadLogin() {
		// TODO Auto-generated method stub
		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		RootPanel.get("Details").clear();
		RootPanel.get("Details").add(loginPanel);
	}
	
}
