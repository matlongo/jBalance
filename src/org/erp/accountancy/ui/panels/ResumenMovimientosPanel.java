package org.erp.accountancy.ui.panels;

import java.awt.event.ActionEvent;
import java.util.Calendar;
import javax.swing.JOptionPane;
import org.erp.accountancy.customqueries.CQMov2Fechas;
import org.erp.accountancy.customqueries.CQMovDia;
import org.erp.accountancy.customqueries.CQMovMes;
import org.erp.accountancy.customqueries.CustomQuery;
import org.erp.accountancy.ui.PanelManager;
import org.erp.accountancy.ui.dialogs.DatesPickerDialog;
import org.erp.accountancy.ui.tables.MovimientosTablePanel;
import org.erp.accountancy.utils.Utils;

public class ResumenMovimientosPanel extends ButtonsPanel {

	private static final long serialVersionUID = 1400006818601451121L;
	private static final String TITLE = "RESUMEN DE MOVIMIENTOS";

	public ResumenMovimientosPanel() {
		super(getButtonsText(), TITLE);
	}
	
	private static String[] getButtonsText(){
		String[] buttonsText = { "Resumen de los movimientos del dia",
				"Resumen de los movimientos de esta quincena", "Resumen de los movimientos de este mes",
				"Resumen de los movimientos de un mes", "Resumen de los movimientos entre dos fechas", "Volver" };
		return buttonsText;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttons[5]) { // Volver
			PanelManager.getInstance().switchPanel(PanelManager.MOVIMIENTOS_PANEL);
			return;
		}
		
		CustomQuery query = null;

		if (e.getSource() == buttons[0]) { // Dia
			query = new CQMovDia();
		} else if (e.getSource() == buttons[1]) { // quincena
			Calendar c1 = Calendar.getInstance(), c2 = Calendar.getInstance();
			Calendar current = Utils.getCurrentDate();
			if (current.get(Calendar.DAY_OF_MONTH) > 14){
				c1.setTime(current.getTime());
				c1.set(Calendar.DAY_OF_MONTH, 15);
				c2.setTime(current.getTime());
				c2.set(Calendar.DAY_OF_MONTH, c2.getActualMaximum(Calendar.DAY_OF_MONTH));
			} else {
				c1.setTime(current.getTime());
				c1.set(Calendar.DAY_OF_MONTH, 1);
				c2.setTime(current.getTime());
				c2.set(Calendar.DAY_OF_MONTH, 14);
			}
			query = new CQMov2Fechas(c1, c2);
		} else if (e.getSource() == buttons[2]) { // este mes
			query = new CQMovMes(Utils.getCurrentMonth());
		} else if (e.getSource() == buttons[3]) { // un mes
			Object[] possibilities = { "ENERO", "FEBRERO", "MARZO", "ABRIL", 
					"MAYO", "JUNIO", "JULIO", "AGOSTO",
					"SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE", };
			String month = (String) JOptionPane.showInputDialog(this,
					"Seleccione el mes:", TITLE,
					JOptionPane.PLAIN_MESSAGE, null, possibilities, "ENERO");
			
			if (month == null) return;
			query = new CQMovMes(Utils.getMonthByName(month));
		} else if (e.getSource() == buttons[4]) { // dos fechas
			DatesPickerDialog dialog = new DatesPickerDialog(PanelManager.getInstance().getMainFrame(), "Seleccione una fecha");
			Calendar c1 = Calendar.getInstance(), c2 = Calendar.getInstance();
			if (dialog.getFromDate() != null  &&  dialog.getToDate() != null){
				c1.setTime(dialog.getFromDate());
				c2.setTime(dialog.getToDate());
				query = new CQMov2Fechas(c1, c2);
			}
		}

		if (query == null) return;
		
		MovimientosTablePanel panel = new MovimientosTablePanel(query, PanelManager.RESUMEN_MOVIMIENTOS_PANEL);
		panel.refresh();
		PanelManager.getInstance().setNewPanel(panel, "resumen-movimientos-panel-table");
	}

}
