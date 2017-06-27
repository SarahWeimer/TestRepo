package de.hdm.projectplan.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.projectplan.shared.LoginInfo;

/**
 * Das asynchrone Gegenstueck des Interface {@link LoginService}. Es wird
 * semiautomatisch durch das Google Plugin erstellt und gepflegt. Daher erfolgt
 * hier keine weitere Dokumentation. Fuer weitere Informationen: siehe das
 * synchrone Interface {@link LoginService}
 * 
 */
public interface LoginServiceAsync {

	public void login(String requestUri, AsyncCallback<LoginInfo> async);
	
}
