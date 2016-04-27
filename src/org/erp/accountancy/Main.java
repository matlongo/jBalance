package org.erp.accountancy;

import java.util.Locale;
import org.erp.accountancy.configuration.ConfigurationManager;
import org.erp.accountancy.hibernate.controller.Controller;
import org.erp.accountancy.hibernate.entities.Cliente;
import org.erp.accountancy.startup.StartUp;
import org.erp.accountancy.ui.PanelManager;

public class Main {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("es", "ES"));
		
		ConfigurationManager.getInstance().updateProperty(ConfigurationManager.HONORARIOS_KEY, 200);
		
		try{
			Controller.getInstance().getSession().get(Cliente.class, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		PanelManager.getInstance().init();
		StartUp.fireActions();
	}
}
