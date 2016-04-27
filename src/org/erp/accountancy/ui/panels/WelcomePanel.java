package org.erp.accountancy.ui.panels;

import java.awt.event.ActionEvent;

import org.erp.accountancy.configuration.ConfigurationManager;
import org.erp.accountancy.hibernate.controller.Controller;
import org.erp.accountancy.ui.PanelManager;
import org.erp.accountancy.utils.Constants;

public class WelcomePanel extends ButtonsPanel {

	private static final long serialVersionUID = -3400702402160274421L;
	
	public WelcomePanel(){
		super(getButtonsText(), Constants.APP_NAME);
	}
	
	private static String[] getButtonsText(){
		String[] buttonsText = {
			"Balance", "Movimientos", "Clientes", "Cuentas", "Honorarios", 
			"Impuestos", "Opciones", "Salir"
		};
		return buttonsText;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttons[0]){ 		 //Balance
			PanelManager.getInstance().getBalancePanel().refresh();
			PanelManager.getInstance().switchPanel(PanelManager.BALANCE_PANEL);
		} else if (e.getSource() == buttons[1]){ //Movimientos
			PanelManager.getInstance().switchPanel(PanelManager.MOVIMIENTOS_PANEL);
		} else if (e.getSource() == buttons[2]){ //Clientes
			PanelManager.getInstance().switchPanel(PanelManager.CLIENTES_PANEL);
		} else if (e.getSource() == buttons[3]){ //Cuentas
			PanelManager.getInstance().switchPanel(PanelManager.CUENTAS_PANEL);
		} else if (e.getSource() == buttons[4]){ //Honorarios
			PanelManager.getInstance().switchPanel(PanelManager.HONORARIOS_PANEL);
		} else if (e.getSource() == buttons[5]){ //Impuestos
			PanelManager.getInstance().switchPanel(PanelManager.IMPUESTOS_PANEL);
		} else if (e.getSource() == buttons[6]){ //Opciones
			PanelManager.getInstance().switchPanel(PanelManager.OPCIONES_PANEL);
		} else if (e.getSource() == buttons[7]){ //Salir
			PanelManager.getInstance().getMainFrame().dispose();
	        Controller.getInstance().close();
	        ConfigurationManager.getInstance().storeFile();
		}
	}

}
