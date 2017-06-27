package de.hdm.projectplan.client.editor;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Impressum extends ProjectplanForm{

	@Override
	protected String getHeadlineText() {
		// TODO Auto-generated method stub
		return "Impressum";
	}

	public void run() {
		/**
		 * Vertikales Panel f�r die Inhalte des Impressums
		 */
		VerticalPanel impressumPanel = new VerticalPanel();
		this.add(impressumPanel);

		/**
		 * Nachfolgend werden die Labels f�r die Ueberschrift, Anschrift der Hochschule und 
		 * den Kontakt definiert. Durch die Verwendung der Labels ist er sehr einfach
		 * m�glich die Zeilen untereinander stehen zu haben.
		 */
		Label gesetzLabel = new Label("Angaben gemäß § 5 Telemediengesetz (TMG)");
		gesetzLabel.setStyleName("labelName");
		impressumPanel.add(gesetzLabel);

		Label hdmLabel = new Label("Hochschule der Medien");
		hdmLabel.setStyleName("labelName");
		impressumPanel.add(hdmLabel);

		Label strasseLabel = new Label("Nobelstr. 10");
		strasseLabel.setStyleName("labelName");
		impressumPanel.add(strasseLabel);

		Label plzLabel = new Label("70569 Stuttgart");
		plzLabel.setStyleName("labelName");
		impressumPanel.add(plzLabel);
		
		Label abstandLabel = new Label("_______________________");
		abstandLabel.setStyleName("labelName");
		impressumPanel.add(abstandLabel);
		
		Label nameLabel = new Label("Sarah Weimer");
		nameLabel.setStyleName("labelName");
		impressumPanel.add(nameLabel);
		
		Label emailLabel = new Label("sw155@hdm-stuttgart.de");
		emailLabel.setStyleName("labelName");
		impressumPanel.add(emailLabel);

		}
	
}
