package org.erp.accountancy.ui.panels;

import java.awt.event.ActionEvent;
import java.util.Calendar;
import javax.swing.JOptionPane;

import org.erp.accountancy.hibernate.entities.Cliente;
import org.erp.accountancy.mov.filters.MF2Fechas;
import org.erp.accountancy.mov.filters.MFMes;
import org.erp.accountancy.mov.filters.MFYear;
import org.erp.accountancy.mov.filters.MovFilter;
import org.erp.accountancy.ui.PanelManager;
import org.erp.accountancy.ui.dialogs.ClientePickDialog;
import org.erp.accountancy.ui.dialogs.DatesPickerDialog;
import org.erp.accountancy.ui.tables.CuentaTablePanel;
import org.erp.accountancy.utils.Utils;

public class CuentasPanel extends ButtonsPanel {

	private static final long serialVersionUID = 6777188691550328732L;
	private static final String TITLE = "RESUMEN DE CUENTAS";
	
	public CuentasPanel() {
		super(getButtosText(), TITLE);
	}
	
	public static String[] getButtosText(){
		String[] buttons = { "Total", "De un mes determinado", "Entre dos fechas", "Volver" };
		return buttons;
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttons[3]){ //Volver
			PanelManager.getInstance().switchPanel(PanelManager.WELCOME_PANEL);
			return;
		}
		
		Cliente cliente = ClientePickDialog.getCliente();
		if (cliente == null) return;
		
		MovFilter filter = null;
		String period = "";
		
		if (e.getSource() == buttons[0]){ 		 //Total
			PanelManager.getInstance().getBalancePanel().refresh();
			PanelManager.getInstance().switchPanel(PanelManager.BALANCE_PANEL);
			filter = new MFYear();
			period = "" + Utils.getCurrentYear();
		} 
		else if (e.getSource() == buttons[1]) { // un mes
			Object[] possibilities = { "ENERO", "FEBRERO", "MARZO", "ABRIL", 
					"MAYO", "JUNIO", "JULIO", "AGOSTO",
					"SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE", };
			String month = (String) JOptionPane.showInputDialog(this,
					"Seleccione el mes:", TITLE,
					JOptionPane.PLAIN_MESSAGE, null, possibilities, "ENERO");
			
			if (month == null) return;
			filter = new MFMes(Utils.getMonthByName(month));
			period = month;
		} 
		else if (e.getSource() == buttons[2]) { // dos fechas
			DatesPickerDialog dialog = new DatesPickerDialog(PanelManager.getInstance().getMainFrame(), "Seleccione una fecha");
			Calendar c1 = Calendar.getInstance(), c2 = Calendar.getInstance();
			if (dialog.getFromDate() != null  &&  dialog.getToDate() != null){
				c1.setTime(dialog.getFromDate());
				c2.setTime(dialog.getToDate());
				filter = new MF2Fechas(c1, c2);
				period = Utils.getDateAsString(c1) + " AL " + Utils.getDateAsString(c2);
			} else return;
		} 
		else return;
		
		CuentaTablePanel panel = new CuentaTablePanel(cliente, filter, period);
		panel.refresh();
		PanelManager.getInstance().setNewPanel(panel, "cuentas-panel");
	}

}
