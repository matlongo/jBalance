package org.erp.accountancy.startup;

import java.util.Calendar;

import javax.swing.JOptionPane;

import org.erp.accountancy.configuration.ConfigurationManager;
import org.erp.accountancy.ui.PanelManager;
import org.erp.accountancy.utils.Utils;

public class MonthlyAction implements StartUpAction {

	@Override
	public void activate() {
		if (hasToActivate()){
			int actualMonth = Utils.getCurrentDate().get(Calendar.MONTH);
			promptMessage(Utils.getMonthByInt(actualMonth));
		}		
	}

	public void promptMessage(String month){
		JOptionPane.showMessageDialog(PanelManager.getInstance().getMainFrame(), 
				"Debe realizar la copia de seguridad correspondiente al mes "+month+"!");
	}
	
	public boolean hasToActivate(){
		int actualMonth = Utils.getCurrentDate().get(Calendar.MONTH);
		return ConfigurationManager.getInstance().getProperty(ConfigurationManager.MES_KEY) != actualMonth;
	}
	
}
