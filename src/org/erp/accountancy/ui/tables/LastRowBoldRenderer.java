package org.erp.accountancy.ui.tables;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


/**
 * This class will be used in order to render the last row of a table in a differente way.
 * The last column's font will be Bold, and its size will be 14.
 * @author mathias
 *
 */
public class LastRowBoldRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 7789862633550175919L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		JLabel parent = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (row == table.getRowCount() - 1)
			parent.setFont(parent.getFont().deriveFont(Font.BOLD, 14));
		return parent;
	}
}
