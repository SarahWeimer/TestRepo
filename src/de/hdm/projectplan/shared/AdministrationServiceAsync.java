package de.hdm.projectplan.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.projectplan.shared.AdministrationService;
import de.hdm.projectplan.shared.bo.Project;
import de.hdm.projectplan.shared.bo.User;

/**
 * Das asynchrone Gegenstueck des Interface {@link AdministrationService} fuer
 * RPCs, um die Geschaeftsobjekte zu verwalten. Es wird semiautomatisch durch das
 * Google Plugin erstellt und gepflegt. Daher erfolgt hier keine weitere
 * Dokumentation. Fuer weitere Informationen: siehe das synchrone Interface
 * {@link AdministrationService}
 * 
 */
public interface AdministrationServiceAsync {

	void init(AsyncCallback<Void> callback) throws IllegalArgumentException;

	void createProject(Project newProject, AsyncCallback<Project> callback);
	
//	void createUser(User newUser, AsyncCallback<Void> callback);
	
	void getUserByName(String lastName, AsyncCallback<Vector<User>> getUserByNameCallback);
	
	void getUserById(int id, AsyncCallback<User> getUserByIdCallback);
	
	void getUser(String email, AsyncCallback<User> callback);
	
	void getUserByEmail(String email, AsyncCallback<User> callback);

	void getAllUsers(AsyncCallback<Vector<User>> getAllUsersCallback);

	void getAllProjects(AsyncCallback<Vector<Project>> getAllProjectsCallback);

	void createUser(User u, AsyncCallback<User> createUserCallback);

	void editProject(Project currentProject, AsyncCallback<Void> updateProjectCallback);

	void deleteProject(Project currentProject, AsyncCallback<Void> deleteProjectCallback);

}
