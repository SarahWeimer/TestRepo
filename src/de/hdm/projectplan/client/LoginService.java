package de.hdm.projectplan.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.projectplan.shared.LoginInfo;
import de.hdm.projectplan.client.LoginServiceAsync;
import de.hdm.projectplan.server.LoginServiceImpl;

/**
 * Das synchrone Gegenstueck des Interface {@link LoginServiceAsync}. Es erfolgt
 * hier keine weitere Dokumentation. Fuer weitere Informationen: siehe die
 * Implementierung des Interface {@link LoginServiceImpl}
 * 
 */
@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService {

	public LoginInfo login(String requestUri);
	
}
