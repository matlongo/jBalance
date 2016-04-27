package org.erp.accountancy.ui.tables;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.erp.accountancy.customqueries.CQMovimientos;
import org.erp.accountancy.customqueries.CustomQuery;
import org.erp.accountancy.hibernate.entities.Cliente;
import org.erp.accountancy.hibernate.entities.Movimiento;
import org.erp.accountancy.ui.PanelManager;

public class MovimientosTablePanel extends TablePanel {

	private static final long serialVersionUID = 1400006818601451121L;
	private CustomQuery customQuery;
	private String parentToSwitch;

	public MovimientosTablePanel(CustomQuery query, String parent) {
		super();
		this.customQuery = query;
		this.parentToSwitch = parent;
	}
	
	public MovimientosTablePanel() {
		super();
		this.customQuery = new CQMovimientos();
	}

	@Override
	public List<TableRow> getData() {
		List<Cliente> clientes = customQuery.getClientes();

		List<TableRow> rows = new ArrayList<>();
		for (Cliente c : clientes)
			for (Movimiento mov : customQuery.getFilter().filter(c.getMovimientos())){
				rows.add(new SummaryRow(c.getIdCliente(), c.getNombre(), mov));
			}
		
		Collections.sort(rows);
		
		return rows;
	}

	@Override
	protected String[] getHeader() {
		String[] headers = { "REG.", "LEG.", "APELLIDO Y NOMBRE", "CONCEPTO", "FECHA", "DEBE", "HABER"}; 
		return headers;
	}

	@Override
	public void goBack() {
		PanelManager.getInstance().switchPanel(parentToSwitch);
		PanelManager.getInstance().removePanel(this);
	}

	@Override
	public String getTitle() {		
		return "MOVIMIENTOS DE "+customQuery.getParamsAsString();
	}

	@Override
	public Class<?>[] getTypes() {
		Class<?>[] types = {Long.class, Long.class, String.class, String.class, String.class,
				Double.class, Double.class};
		return types;
	}
}
