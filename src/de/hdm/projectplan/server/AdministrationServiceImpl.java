package de.hdm.projectplan.server;

import java.util.Vector;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.projectplan.server.db.ProjectMapper;
import de.hdm.projectplan.server.db.UserMapper;
import de.hdm.projectplan.shared.AdministrationService;
import de.hdm.projectplan.shared.bo.Project;
import de.hdm.projectplan.shared.bo.User;

public class AdministrationServiceImpl extends RemoteServiceServlet implements AdministrationService {

	
	
	/**
	 * Eindeutige SerialVersion ID. Wird zum Serialisieren der Klasse benoetigt.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Referenz auf den DatenbankMapper, der Nutzerobjekte mit der Datenbank
	 * abgleicht.
	 */
	private UserMapper userMapper = null;
	
	/**
	 * Referenz auf den DatenbankMapper, der Projektobjekte mit der Datenbank
	 * abgleicht.
	 */
	private ProjectMapper projectMapper = null;

	/**
	 * No-Argument Konstruktor
	 * 
	 * @throws IllegalArgumentException
	 *             Benoetigt fuer RPC-Core
	 */
	public AdministrationServiceImpl() throws IllegalArgumentException {

	}
	
	
	
	
	/**
	 * Initialisiert die Implementierung des Interface AdministrationService.
	 * Diese Methode muss fuer jede Instanz von
	 * <code>AdministrationServiceImpl</code> aufgerufen werden.
	 * 
	 */
	@Override
	public void init() throws IllegalArgumentException {
		/**
		 * Ganz wesentlich ist, dass die Administration einen vollstaendigen
		 * Satz von Mappern besitzt, mit deren Hilfe sie dann mit der Datenbank
		 * kommunizieren kann.
		 */
		this.projectMapper = ProjectMapper.projectMapper();
		this.userMapper = UserMapper.userMapper();
	}
	
	 /*
	   * ***************************************************************************
	   * ABSCHNITT, Ende: Initialisierung
	   * ***************************************************************************
	   */

	  /*
	   * ***************************************************************************
	   * ABSCHNITT, Beginn: Methoden für Customer-Objekte
	   * ***************************************************************************
	   */
	
	
	/**
	 * Erstellen eines Projekt-Objekts in der Datenbank
	 */
	public Project createProject(Project newProject) {
						
		/*
		 * Uebergibt die User ID an das Projekt-Objekt zum Einfuegen in die Datenbank an den Mapper
		 */
		User u = this.userMapper.getIdByName(newProject.getProjectleader());
		newProject.setPLuserId(u.getID());
		
		return this.projectMapper.insert(newProject);
	}
	

	  /**
	   * Auslesen aller Kunden, die den übergebenen Nachnamen besitzen.
	   */
	  @Override
	public Vector<User> getUserByName(String lastName)
	      throws IllegalArgumentException {

	    return this.userMapper.findByLastName(lastName);
	  }

	  /**
	   * Auslesen eines Users anhand seiner Kundennummer.
	   */
	  @Override
	public User getUserById(int id) throws IllegalArgumentException {
	    return this.userMapper.findByKey(id);
	  }
	  
	  
	  /**
	   * Auslesen des Users anhand der Email
	   * @param email
	   * @return
	   * @throws IllegalArgumentException
	   */
	  public User getUserByEmail(String email) throws IllegalArgumentException {
		  return this.userMapper.getByEmail(email);
	  }
	  
	  
	  /**
	   * Auslesen des Users ....
	   */
	  public User getUser(String email) throws IllegalArgumentException {
		  User u = userMapper.getByEmail(email);
		  if(u != null) {
			  return u;
		  } else {
			  User newUser = new User();
			  newUser.setVorname(newUser.getVorname());
			  newUser.setNachname(newUser.getNachname());
			  newUser.setArbeitsgebiet(newUser.getArbeitsgebiet());
			  newUser.setInteresse(newUser.getInteresse());
			  
			  this.createUser(newUser);		 
		  }
		  
		  return u;
	  }

	  /**
	   * Auslesen aller Kunden.
	   */
	  @Override
	public Vector<User> getAllUsers() throws IllegalArgumentException {
	    return this.userMapper.findAll();
	  }
	  
	  
	  /**
	   * Auslesen aller Projekte aus der Datenbank
	   */
	  public Vector<Project> getAllProjects() throws IllegalArgumentException {
		  return this.projectMapper.findAll();
	  }
	

	




	@Override
	public User createUser(User newUser) throws IllegalArgumentException {
		// TODO Auto-generated method stub		
		User u = new User();
		u.setVorname(newUser.getVorname());
	    u.setNachname(newUser.getNachname());
	    u.setEmail(newUser.getEmail());
	    u.setArbeitsgebiet(newUser.getArbeitsgebiet());
	    u.setInteresse(newUser.getInteresse());

	    /*
	     * Setzen einer vorläufigen Kundennr. Der insert-Aufruf liefert dann ein
	     * Objekt, dessen Nummer mit der Datenbank konsistent ist.
	     */
	    u.setID(1);

	    // Objekt in der DB speichern.
	    return this.userMapper.insert(u);
	    
//	    return this.userMapper.insert(newUser);
	}
	

	
	public void editProject (Project editedProject) {
		
		/*
		 * Uebergibt die User ID an das Projekt-Objekt zum Einfuegen in die Datenbank an den Mapper
		 */
		User u = this.userMapper.getIdByName(editedProject.getProjectleader());
		editedProject.setPLuserId(u.getID());
		
		this.projectMapper.edit(editedProject);
	}
	
	
	public void deleteProject(Project currentProject) {
		this.projectMapper.delete(currentProject.getID());
	}
	
}
