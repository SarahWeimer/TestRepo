package de.hdm.projectplan.client.editor;

import java.util.Comparator;
import java.util.Date;
import java.util.Vector;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ShowRangeEvent;
import com.google.gwt.event.logical.shared.ShowRangeHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;

import de.hdm.projectplan.client.editor.AllProjectsView;
import de.hdm.projectplan.client.ClientsideSettings;
import de.hdm.projectplan.shared.AdministrationService;
import de.hdm.projectplan.shared.AdministrationServiceAsync;
import de.hdm.projectplan.shared.bo.Project;
import de.hdm.projectplan.shared.bo.User;
import de.hdm.projectplan.client.editor.ProjectplanForm;

public class NewProjectView extends ProjectplanForm{

	/**
	 * Instanziierung des Tabellen Widgets zur Darstellung von Benutzerprofilen.
	 */
	private CellTable<User> cellTableUser = new CellTable<User>();
	
	/**
	 * Instanziierung des DataProviders, der die Profilwerte fuer das Tabellen
	 * Widget bereithaelt.
	 */
	private ListDataProvider<User> dataProvider = new ListDataProvider<User>();

	/**
	 * Instanziierung des Handlers, der die Profilwerte fuer das Tabellen Widget
	 * sortiert.
	 */
	private ListHandler<User> sortHandler = new ListHandler<User>(dataProvider.getList());

	/**
	 * Instanziierung des SelectionModel, welches die Auswahl von Profilwerten
	 * im Tabellen Widget unterstuetzt.
	 */
	private final MultiSelectionModel<User> selectionModel = new MultiSelectionModel<User>(null);

	/**
	 * Instanziierung des Pagers, der die Kontrolle ueber das Tabellen Widget
	 * unterstuetzt.
	 */
	SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
	SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
	
	
	MultiWordSuggestOracle oracleProjecleader = new MultiWordSuggestOracle();
	
	
	
	@Override
	protected String getHeadlineText() {
		// TODO Auto-generated method stub
		return "Neues Projekt anlegen";
	}

	@Override
	protected void run() {
		/*
		 * Abfragen von den Users aus der Datenbank
		 */
		AdministrationServiceAsync adminService = ClientsideSettings.getAdministration();
		adminService.getAllUsers(new GetAllUsersCallback(this));
		
		/*
		 * Formatierung der Panels und Widgets fuer die Ansicht
		 */
		cellTableUser.setWidth("100%", true);
		
		/*
		 * Keine erneute Aktualisierung der Header und Footer bei einer
		 * Wertaenderung.
		 */
		cellTableUser.setAutoHeaderRefreshDisabled(true);

		/*
		 * Anhaengen eines Handlers zur Spaltensortierung an den DataProvider,
		 * um die Tabelle sortieren zu koennen.
		 */
		cellTableUser.addColumnSortHandler(sortHandler);
		cellTableUser.setSelectionModel(selectionModel, DefaultSelectionEventManager.<User>createCheckboxManager());

		/*
		 * Initialisierung der Tabellenspalten
		 */
//		initTableColumnsUser(selectionModel, sortHandler);

		/*
		 * Hinzufuegen eines DatenAdapters zur Tabelle.
		 */
		addDataDisplay(cellTableUser);

		pager.setDisplay(cellTableUser);
		
		/**
		 * Vertikales Panel f�r die Inhalte des neuen Projekts
		 */
		VerticalPanel vpDetails = new VerticalPanel();
		this.add(vpDetails);
		HorizontalPanel hpDetails = new HorizontalPanel();
		vpDetails.add(hpDetails);
				
//		RootPanel.get("Details").add(cellTableUser);
//		vpDetails.add(cellTableUser);
		
		// Tabelle fuer die gerade Anordnung der Labels und Textboxes
		Grid grid = new Grid(3, 2);
		vpDetails.add(grid);
		
		/**
		 * Panels und Labels angelegt
		 */
		Label lbTitel = new Label("Projektname:");
		lbTitel.setStylePrimaryName("labelName");
		final TextBox tbTitel = new TextBox();
		tbTitel.setStylePrimaryName("textbox");
		
		grid.setWidget(0, 0, lbTitel);
		grid.setWidget(0, 1, tbTitel);
		
		Label lbProjectLeader = new Label("Projektleiter:");
		lbProjectLeader.setStylePrimaryName("labelName");
		
		
		
		final SuggestBox sbProjectLeader = new SuggestBox(oracleProjecleader);
		
		sbProjectLeader.ensureDebugId("cwSuggestBoxUser");
		
//		final TextBox tbProjectLeader = new TextBox();
		sbProjectLeader.setStylePrimaryName("textbox");
		
		grid.setWidget(1, 0, lbProjectLeader);
		grid.setWidget(1, 1, sbProjectLeader);
		
		Label lbParticipant = new Label("Teilnehmer:");
		lbParticipant.setStylePrimaryName("labelName");
		final TextBox tbParticipant = new TextBox();
//		final SuggestBox sbParticipant = new SuggestBox();	//HIER KOMMEN DIE USER REIN!
		tbParticipant.setStylePrimaryName("textbox");
		
//		final Button btnParticipants = new Button("Teilnehmer hinzufügen");
//		btnParticipants.setStylePrimaryName("buttonAdd");
//		final DialogBox dialogBox = createDialogBox();
		
//		TextBox tbParticipant = new TextBox();
//		tbParticipant.setStylePrimaryName("textbox");
		
		grid.setWidget(2, 0, lbParticipant);
		grid.setWidget(2, 1, tbParticipant);
//		vpDetails.add(lbParticipant);
//		vpDetails.add(tbParticipant);	
		
		Grid grid2 = new Grid(1, 3);
		vpDetails.add(grid2);
		
		Label lbTime = new Label("Zeitraum (Von- Bis-):");
		lbTime.setStylePrimaryName("labelName");
		final DatePicker dpTimeFrom = new DatePicker();
		final DatePicker dpTimeTo = new DatePicker();
		dpTimeFrom.setStylePrimaryName("datebox");
		dpTimeTo.setStylePrimaryName("datebox");
		
		dpTimeFrom.setYearArrowsVisible(true);
		dpTimeFrom.setYearAndMonthDropdownVisible(false);
		dpTimeFrom.setVisibleYearCount(101);
		dpTimeFrom.setYearAndMonthDropdownVisible(true);
		
		dpTimeTo.setYearArrowsVisible(true);
		dpTimeTo.setYearAndMonthDropdownVisible(false);
		dpTimeTo.setVisibleYearCount(101);
		dpTimeTo.setYearAndMonthDropdownVisible(true);
		
		/*
		 * Sperren der Eingabemoeglichkeit im DatePicker "dpTimeFrom" bei zukuenftigen Daten
		 */
		dpTimeFrom.addShowRangeHandlerAndFire(new ShowRangeHandler<java.util.Date>() {
			
			public void onShowRange(ShowRangeEvent<Date> event) {
				Date start = event.getStart();
				Date temp = CalendarUtil.copyDate(start);
				Date end = event.getEnd();
				
				Date today = new Date();
				
				while (temp.before(end)) {
					if (temp.before(today) && dpTimeFrom.isDateVisible(temp)) {
						dpTimeFrom.setTransientEnabledOnDates(false, temp);
					}
					CalendarUtil.addDaysToDate(temp, 1);
				}
			}
		});
		
		/*
		 * Sperren der Eingabemoeglichkeit im DatePicker "dpTimeTo" bei zukuenftigen Daten
		 */
		dpTimeTo.addShowRangeHandlerAndFire(new ShowRangeHandler<java.util.Date>() {
			
			public void onShowRange(ShowRangeEvent<Date> event) {
				Date start = event.getStart();
				Date temp = CalendarUtil.copyDate(start);
				Date end = event.getEnd();
				
				Date today = new Date();
				
				while (temp.before(end)) {
					if (temp.before(today) && dpTimeTo.isDateVisible(temp)) {
						dpTimeTo.setTransientEnabledOnDates(false, temp);
					}
					CalendarUtil.addDaysToDate(temp, 1);
				}
			}
		});
		
			
		grid2.setWidget(0, 0, lbTime);
		grid2.setWidget(0, 1, dpTimeFrom);
		grid2.setWidget(0, 2, dpTimeTo);

		Grid grid3 = new Grid(1, 2);
		vpDetails.add(grid3);
		
		Label lbContent = new Label("Beschreibung:");
		lbContent.setStylePrimaryName("labelName");
		final TextArea tbContent = new TextArea();
		tbContent.setStylePrimaryName("textarea");
		
		grid3.setWidget(0, 0, lbContent);
		grid3.setWidget(0, 1, tbContent);
		
			
		// Panel für die Buttons hinzugefuegt und dem Details Panel zugewiesen
		HorizontalPanel hpButtons = new HorizontalPanel();
		vpDetails.add(hpButtons);
		
		/**
		 * Buttons der Seite angelegt und dem Panel zugewiesen
		 */
		final Button btnAbbrechen = new Button("Abbrechen");
		btnAbbrechen.setStylePrimaryName("button5");
		hpButtons.add(btnAbbrechen);
	
		final Button btnSpeichern = new Button("Speichern");
		btnSpeichern.setStylePrimaryName("button");
		hpButtons.add(btnSpeichern);
		
		
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
		
//		btnParticipants.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				// TODO Auto-generated method stub
//				History.newItem("Teilnehmer hinzufügen");
//				dialogBox.center();
//				dialogBox.show();
//			}
//		});
		
		// fuer "createProjectCallback" WICHTIG
		final ProjectplanForm projectplanForm = this;
		
		btnSpeichern.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				
				// TODO Auto-generated method stub
				Project newProject = new Project();
				newProject.setTitle(tbTitel.getText());
				newProject.setProjectleader(sbProjectLeader.getText());
				newProject.setParticipant(tbParticipant.getText());
				newProject.setContent(tbContent.getText());
				newProject.setStartdatum(dpTimeFrom.getValue());
				newProject.setEnddatum(dpTimeTo.getValue());

				AdministrationServiceAsync adminService = ClientsideSettings.getAdministration();
				adminService.createProject(newProject, new createProjectCallback(projectplanForm));
				
				ProjectplanForm update = new AllProjectsView();
				RootPanel.get("Details").clear();
				RootPanel.get("Details").add(update);
			}	
		});
		
	
	}
	
	
	/**
	 * Fuegt einen Datenadapter hinzu. Die aktuelle Anzeige wird mit Werten
	 * befuellt.
	 * 
	 * @param display
	 *            a {@Link HasData}.
	 */
	public void addDataDisplay(HasData<User> display) {
		dataProvider.addDataDisplay(display);
	}

	/**
	 * Aktualisiert alle Datenadapter.
	 * 
	 * @return
	 */
	public void refreshDisplays() {
		dataProvider.refresh();
	}
	

//	/**
//	 * Erstellt eine DialogBox als Auswahlliste für Teilnehmer und gibt diese zurueck.
//	 * @return
//	 */
//	private DialogBox createDialogBox() {
//		
//		/*
//		 * Abfragen von den Users aus der Datenbank
//		 */
//		AdministrationServiceAsync adminService = ClientsideSettings.getAdministration();
//		adminService.getAllUsers(new GetAllUsersCallback(this));
//		
//		/*
//		 * Keine erneute Aktualisierung der Header und Footer bei einer
//		 * Wertaenderung.
//		 */
//		cellTableUser.setAutoHeaderRefreshDisabled(true);
//
//		/*
//		 * Anhaengen eines Handlers zur Spaltensortierung an den DataProvider,
//		 * um die Tabelle sortieren zu koennen.
//		 */
//		cellTableUser.addColumnSortHandler(sortHandler);
//		cellTableUser.setSelectionModel(selectionModel, DefaultSelectionEventManager.<User>createCheckboxManager());
//
//		/*
//		 * Initialisierung der Tabellenspalten
//		 */
//		initTableColumnsUser(selectionModel, sortHandler);
//
//		/*
//		 * Hinzufuegen eines DatenAdapters zur Tabelle.
//		 */
//		addDataDisplay(cellTableUser);
//
//		pager.setDisplay(cellTableUser);
//		
//		/*
//		 * Instanziierung einer DialogBox und Setzung der Ueberschrift dieser
//		 * DialogBox.
//		 */
//		final DialogBox dialogBox = new DialogBox();
//		dialogBox.ensureDebugId("cwDialogBox");
//		dialogBox.setText("Teilnehmer auswählen:");
//		
//		VerticalPanel dialogContents = new VerticalPanel();
//		HorizontalPanel dialogContents2 = new HorizontalPanel();
//		dialogContents.setSpacing(4);
//		dialogBox.setWidget(dialogContents);
//		dialogBox.setWidget(dialogContents2);
//		
//		/*
//		 * Setzung des cellTableUser in die DialogBox.
//		 */
//		dialogContents.add(cellTableUser);
//		dialogContents.add(pager);
//		
//		final Button btnClose = new Button("Schließen");
//		btnClose.addClickHandler(new ClickHandler() {
//
//			@Override
//			public void onClick(ClickEvent event) {
//				// TODO Auto-generated method stub
//				dialogBox.hide();
//			}
//			
//		});
//		
//		final Button btnSave = new Button("Speichern");
//		btnSave.addClickHandler(new ClickHandler() {
//
//			@Override
//			public void onClick(ClickEvent event) {
//				// TODO Auto-generated method stub
//				
//				dialogBox.hide();
//			}
//			
//		});
//		
//		
//		/*
//		 * Hinzufuegen der "schliessen", "speichern" Schaltfläche am Ende der DialogBox
//		 */
//		dialogContents2.add(btnClose);
//		dialogContents2.add(btnSave);
//		
//		return dialogBox;
//	}



//	private void initTableColumnsUser(final SelectionModel<User> selectionModel,
//			ListHandler<User> sortHandler) {
//		// TODO Auto-generated method stub
//		
//		// CheckBox.
//		Column<User, Boolean> checkColumn = new Column<User, Boolean>(new CheckboxCell(true, false)) {
//			@Override
//			public Boolean getValue(User object) {
//				return selectionModel.isSelected(object);
//				}
//			};
//		cellTableUser.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
//		cellTableUser.setColumnWidth(checkColumn, 40, Unit.PX);
//		
//		// Vorname
//		Column<User, String> firstnameColumn = new Column<User, String>(new TextCell()) {
//			@Override
//			public String getValue(User object) {
//				// TODO Auto-generated method stub
//				return object.getVorname();
//			}
//			
//		};
//		firstnameColumn.setSortable(true);
//		firstnameColumn.setDefaultSortAscending(true);
//		sortHandler.setComparator(firstnameColumn, new Comparator<User>() {
//			@Override
//			public int compare(User o1, User o2) {
//				// TODO Auto-generated method stub
//				return o1.getVorname().compareTo(o2.getVorname());
//			}
//		});
//		cellTableUser.addColumn(firstnameColumn, "Vorname");
//		cellTableUser.setColumnWidth(firstnameColumn, 110, Unit.PX);
//		
//		//Nachname
//		Column<User, String> lastnameColumn = new Column<User, String>(new TextCell()) {
//			@Override
//			public String getValue(User object) {
//				// TODO Auto-generated method stub
//				return object.getNachname();
//			}
//			
//		};
//		lastnameColumn.setSortable(true);
//		lastnameColumn.setDefaultSortAscending(true);
//		sortHandler.setComparator(lastnameColumn, new Comparator<User>() {
//			@Override
//			public int compare(User o1, User o2) {
//				// TODO Auto-generated method stub
//				return o1.getNachname().compareTo(o2.getNachname());
//			}
//		});
//		cellTableUser.addColumn(lastnameColumn, "Nachname");
//		cellTableUser.setColumnWidth(lastnameColumn, 110, Unit.PX);
//		
//		//Email
//		Column<User, String> clickableEmailColumn = new Column<User, String>(new ClickableTextCell()) {
//			@Override
//			public String getCellStyleNames(Cell.Context context, User object) {
//				return "userColumn";
//			}
//			@Override
//			public String getValue(User object) {
//				// TODO Auto-generated method stub
//				return object.getEmail();
//			}
//			
//		};
//		clickableEmailColumn.setSortable(true);
//		clickableEmailColumn.setDefaultSortAscending(true);
//		sortHandler.setComparator(clickableEmailColumn, new Comparator<User>() {
//			@Override
//			public int compare(User o1, User o2) {
//				// TODO Auto-generated method stub
//				return o1.getEmail().compareTo(o2.getEmail());
//			}
//		});
//		
//		clickableEmailColumn.setFieldUpdater(new FieldUpdater<User, String>() {
//
//			@Override
//			public void update(int index, User object, String value) {
//				// TODO Auto-generated method stub
//				History.newItem("Nutzer Details");
//				ProjectplanForm update = new DetailsUserView(object, "DetailsUserView");
//				RootPanel.get("Details").clear();
//				RootPanel.get("Details").setWidth("65%");
//				RootPanel.get("Details").add(update);
//			}
//			
//			
//		});
//		cellTableUser.addColumn(clickableEmailColumn, "Email");
//		cellTableUser.setColumnWidth(clickableEmailColumn, 110, Unit.PX);
//	}



	class createProjectCallback implements AsyncCallback<Project> {
		
		private ProjectplanForm projectplanForm = null;
		
		public createProjectCallback (ProjectplanForm pf) {
			//Window.alert("createProjectCallback weiter ned");
			this.projectplanForm = pf;
			
		}
		
		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub			
			this.projectplanForm.append("Fehler bei der Abfrage " + caught.getMessage());
			
		}

		@Override
		public void onSuccess(Project result) {
			// TODO Auto-generated method stub
			if (result != null) {
				 this.projectplanForm.append("Projekt " + result.getTitle()
		            + " wurde angelegt.");
			}
			
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
