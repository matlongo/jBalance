package org.erp.accountancy.ui.tables;

/**
 * This class represents the last row of the balance table.
 * @author mathias
 *
 */
public class TotalRow implements TableRow {

	private double totalDebe, totalHaber;
	
	public TotalRow(double debe, double haber) {
		this.totalDebe = debe;
		this.totalHaber = haber;
	}
	
	@Override
	public int compareTo(TableRow o) {
		return 0;
	}

	@Override
	public Object getValueAt(int columnIndex) {
		switch (columnIndex){
		case 0: return null;
		case 1: return "TOTAL";
		case 2: return totalDebe;
		case 3: return totalHaber;
		case 4: return totalHaber - totalDebe;
		}
		return null;
	}

}
