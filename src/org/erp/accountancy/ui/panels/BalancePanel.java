package org.erp.accountancy.ui.panels;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.erp.accountancy.controller.BalanceController;
import org.erp.accountancy.controller.Saldo;
import org.erp.accountancy.hibernate.entities.Cliente;
import org.erp.accountancy.ui.PanelManager;
import org.erp.accountancy.ui.tables.BalanceRow;
import org.erp.accountancy.ui.tables.LastRowBoldRenderer;
import org.erp.accountancy.ui.tables.TablePanel;
import org.erp.accountancy.ui.tables.TableRow;
import org.erp.accountancy.ui.tables.TotalRow;

public class BalancePanel extends TablePanel implements ActionListener {

	private static final long serialVersionUID = 8842818102401370660L;
	private BalanceController controller;
	
	public BalancePanel() {
		super();
		controller = new BalanceController();
		this.getTable().setDefaultRenderer(Object.class, new LastRowBoldRenderer());
	}

	@Override
	public List<TableRow> getData() {
		Map<Cliente, Saldo> clientes = controller.getBalance();
		
		double totalDebe = 0.0, totalHaber = 0.0;
		
		List<TableRow> rows = new ArrayList<>();
		for (Cliente cliente: clientes.keySet()){
			BalanceRow row = new BalanceRow(cliente, clientes.get(cliente));
			rows.add(row);
			totalDebe += (Double) row.getValueAt(2);
			totalHaber += (Double) row.getValueAt(3);
		}
		Collections.sort(rows);
		
		rows.add(new TotalRow(totalDebe, totalHaber));
		
		return rows;
	}

	@Override
	protected String[] getHeader() {
		String[] headers = { "LEG.N*", "APELLIDO Y NOMBRE", "DEBE", "HABER", "SALDO" };
		return headers;
	}

	@Override
	public void goBack() {
		PanelManager.getInstance().switchPanel(PanelManager.WELCOME_PANEL);
	}

	@Override
	public String getTitle() {
		return "BALANCE DE SALDOS DE AYACUCHO";
	}

	@Override
	public Class<?>[] getTypes() {
		Class<?>[] types = {Long.class, String.class, Double.class, Double.class, Double.class};
		return types;
	}
}
