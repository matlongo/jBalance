package org.erp.accountancy.ui.tables;

import javax.swing.JScrollPane;

public class MovementSelectTablePanel extends MovimientosTablePanel {

	private static final long serialVersionUID = 6399839010623610335L;
	
	@Override
	public void addComponents() {
		JScrollPane tablePanel = this.initTable();
		
		this.add(tablePanel);
	}

}
