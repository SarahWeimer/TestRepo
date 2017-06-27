package de.hdm.projectplan.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.projectplan.server.AdministrationServiceImpl;
import de.hdm.projectplan.shared.bo.User;
import de.hdm.projectplan.shared.AdministrationServiceAsync;
import de.hdm.projectplan.shared.bo.Project;

/**
 * Das synchrone Gegenstueck des Interface {@link AdministrationServiceAsync}
 * fuer RPCs, um die Geschaeftsobjekte zu verwalten. Es erfolgt hier keine weitere
 * Dokumentation. Fuer weitere Informationen: siehe die Implementierung des
 * Interface {@link AdministrationServiceImpl}
 * 
 */
@RemoteServiceRelativePath("projectplantest")
public interface AdministrationService extends RemoteService{

	public void init() throws IllegalArgumentException;

	public Project createProject(Project newProject);

//	void createUser(User newUser); 

	Vector<User> getUserByName(String lastName) throws IllegalArgumentException;

	User getUserById(int id) throws IllegalArgumentException;
	
	User getUser(String email) throws IllegalArgumentException;
	
	User getUserByEmail(String email) throws IllegalArgumentException;

	Vector<User> getAllUsers() throws IllegalArgumentException;
	
	public Vector<Project> getAllProjects() throws IllegalArgumentException;
	
	User createUser(User newUser) throws IllegalArgumentException;
	
	void editProject (Project currentProject);
	
	void deleteProject (Project currentProject);
	
	
}
