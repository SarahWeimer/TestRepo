package de.hdm.projectplan.client.editor;

import java.util.Comparator;
import java.util.Vector;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.projectplan.client.editor.AllProjectsView;
import de.hdm.projectplan.client.editor.ProjectplanForm;
import de.hdm.projectplan.client.ClientsideSettings;
import de.hdm.projectplan.shared.AdministrationServiceAsync;
import de.hdm.projectplan.shared.bo.Project;

public class AllProjectsView extends ProjectplanForm{
	
	/**
	 * Instanziierung des Tabellen Widgets zur Darstellung von Notizbuechern.
	 */
	private CellTable<Project> cellTableProject = new CellTable<Project>();

	/**
	 * Instanziierung des DataProviders, der die Projektwerte fuer das Tabellen Widget bereit haelt
	 */
	private ListDataProvider<Project> dataProviderProject = new ListDataProvider<Project>();
	
	/**
	 * Instanziierung des Handlers, der die Projectwerte fuer das Tabellen Widget sortiert
	 */
	private ListHandler<Project> sortHandlerProject = new ListHandler<Project>(dataProviderProject.getList());

	/**
	 * Instanziierung des SelectionModel, welches die Auswahl von Projektwerten im Tabellen Widget unterstuetzt
	 */
	private final MultiSelectionModel<Project> selectionModelProject = new MultiSelectionModel<Project>(null);
	
	/**
	 * Instanziierung des Pagers, der die Kontrolle ueber das Tabellen Widget unterstuetzt
	 */
	SimplePager.Resources pagerResourcesProject = GWT.create(SimplePager.Resources.class);
	SimplePager pagerProject = new SimplePager (TextLocation.CENTER, pagerResourcesProject, false, 0, true);
	
	
	/**
	 * Die Instanz des aktuellen Projekts ermoeglicht den schnellen Zugriff auf
	 * dessen Projekte.
	 */
	private Project currentProject = null;
	
	
	
	@Override
	protected String getHeadlineText() {
		// TODO Auto-generated method stub
		return "Projekte Übersicht";
	}
	


	@Override
	protected void run() {
		// TODO Auto-generated method stub
		
		AdministrationServiceAsync adminService = ClientsideSettings.getAdministration();
		
		adminService.getAllProjects(new GetAllProjectsCallback(this));
				
		
		/**
		 * Formatierung der Panels und Widgets fuer die Ansicht
		 */
		cellTableProject.setWidth("100%", true);
		
		/*
		 * Keine erneute Aktualisierung der Header und Footer bei einer Wertaenderung
		 */
		cellTableProject.setAutoHeaderRefreshDisabled(true);
		
		/*
		 * Anhaengen eines Handlers zur Spaltensortierung an den DataProvider,
		 * um die Tabelle sortieren zu koennen.
		 */
		cellTableProject.addColumnSortHandler(sortHandlerProject);
		cellTableProject.setSelectionModel(selectionModelProject, DefaultSelectionEventManager.<Project>createCheckboxManager());
		
		/*
		 * Initialisierung der Tabellenspalten
		 */
		initTableColumnsProject(selectionModelProject, sortHandlerProject);
		
		/*
		 * Hinzufuegen eines DatenAdapters zur Tabelle
		 */
		addDataDisplayProject(cellTableProject);
		
		pagerProject.setDisplay(cellTableProject);
		
		/**
		 * Vertikales Panel fuer die Inhalte der Projekte
		 */
		VerticalPanel vpDetails = new VerticalPanel();
		this.add(vpDetails);
		
		// Hinzufuegen der Tabelle zum RootPanel
		RootPanel.get("Details").add(cellTableProject);
				
	}
	
	
	
	
	/**
	 * Fuegt einen Datenadapter hinzu. Die aktuelle Anzeige wird mit Werten befuellt.
	 */
	public void addDataDisplayProject(HasData<Project> display) {
		dataProviderProject.addDataDisplay(display);
	}
	
	/**
	 * Aktualisiert alle Datenadapter
	 */
	public void refreshDisplaysProject() {
		dataProviderProject.refresh();
	}
	
	
	
	class GetAllProjectsCallback implements AsyncCallback<Vector<Project>> {

		private ProjectplanForm projectplanForm = null;
		
		public GetAllProjectsCallback(ProjectplanForm pf) {
			this.projectplanForm = pf;
		}
		
		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			this.projectplanForm.append("Fehler bei der Abfrage " + caught.getMessage());
		}

		@Override
		public void onSuccess(Vector<Project> project) {
			// TODO Auto-generated method stub
			for (Project p : project) {
				dataProviderProject.getList().add(p);
			}
			
		}
		
	}
	
	
	/**
	 * Fuegt die Spalten in die Tabelle Projekt.
	 * 
	 * @param selectionModel
	 * 			SelectionModel, welches die Auswahl von Projektwerten im Tabellen Widget unterstuetzt
	 * @param sortHandler
	 * 			Handler, der die Projektwerte fuer das Tabellen Widget sortiert
	 */
	private void initTableColumnsProject(final SelectionModel<Project> selectionModel, ListHandler<Project> sortHandler) {
		
		//Titel
		Column<Project, String> clickableTextColumn = new Column<Project, String>(new ClickableTextCell()) {

			@Override
			public String getCellStyleNames(Cell.Context context, Project object) {
				return "cellTable";
			}
			
			@Override
			public String getValue(Project object) {
				// TODO Auto-generated method stub
				return object.getTitle();
			}
		};
		
		clickableTextColumn.setSortable(true);
		clickableTextColumn.setDefaultSortAscending(true);
		sortHandler.setComparator(clickableTextColumn, new Comparator<Project>() {

			@Override
			public int compare(Project o1, Project o2) {
				// TODO Auto-generated method stub
				return o1.getTitle().compareTo(o2.getTitle());
			}
			
		});
		
		clickableTextColumn.setFieldUpdater(new FieldUpdater<Project, String>() {

			@Override
			public void update(int index, Project object, String value) {
				// TODO Auto-generated method stub
				ProjectplanForm update = new DetailsProjectView(object, "DetailsProjectView");
				RootPanel.get("Details").clear();
				RootPanel.get("Details").setWidth("65%");
				RootPanel.get("Details").add(update);
			}
			
		});
		cellTableProject.addColumn(clickableTextColumn, "Titel");
		cellTableProject.setColumnWidth(clickableTextColumn, 110, Unit.PX);
		
		//Projektleiter
		Column<Project, String> clickableTextColumn2 = new Column<Project, String>(new ClickableTextCell()) {

			@Override
			public String getCellStyleNames(Cell.Context context, Project object) {
				return "cellTable";
			}
			
			@Override
			public String getValue(Project object) {
				// TODO Auto-generated method stub
				return object.getProjectleader();
			}
			
		};
		clickableTextColumn2.setSortable(true);
		clickableTextColumn2.setDefaultSortAscending(true);
		sortHandler.setComparator(clickableTextColumn2, new Comparator<Project>() {

			@Override
			public int compare(Project o1, Project o2) {
				// TODO Auto-generated method stub
				return o1.getProjectleader().compareTo(o2.getProjectleader());
			}
			
		});
		
		clickableTextColumn2.setFieldUpdater(new FieldUpdater<Project, String>() {
			@Override
			public void update(int index, Project object, String value) {
				ProjectplanForm update = new OtherPersonView(object, "OtherPersonView", null); 
				RootPanel.get("Details").clear();
				RootPanel.get("Details").setWidth("65%");
				RootPanel.get("Details").add(update);
			}
		});
		cellTableProject.addColumn(clickableTextColumn2, "Projektleiter");
		cellTableProject.setColumnWidth(clickableTextColumn2, 110, Unit.PX);
	}
	

}
