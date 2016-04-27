package org.erp.accountancy.ui.tables;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ClienteSelectTablePanel extends ClienteTablePanel {

	private static final long serialVersionUID = -3043178522573865452L;

	@Override
	public void addComponents() {
		JScrollPane tablePanel = this.initTable();
		JPanel panel = this.getFilteringPanel();
		
		this.add(tablePanel);
		this.add(panel, BorderLayout.NORTH);
	}
}
