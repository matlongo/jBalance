package org.erp.accountancy.ui.tables;

/**
 * This interface represents the rows of the different TablePanel instantiations.
 * @author mathias
 *
 */
public interface TableRow extends Comparable<TableRow> {

	/**
	 * This method returns the row's object in a particular position/column.
	 * @param columnIndex Index of the column.
	 * @return Object located in the column index position.
	 */
	public Object getValueAt(int columnIndex);
	
}
