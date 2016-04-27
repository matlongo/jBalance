package org.erp.accountancy.ui.tables;

import org.erp.accountancy.hibernate.entities.Movimiento;
import org.erp.accountancy.utils.Utils;

public class SummaryRow implements Comparable<TableRow>, TableRow {
	private long idCliente;
	private String name;
	private Movimiento movimiento;

	public SummaryRow(long idCliente, String name, Movimiento mov){
		this.idCliente = idCliente;
		this.name = name;
		this.movimiento = mov;
	}
	
	public Object getValueAt(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return this.movimiento.getId();
		case 1:
			return this.idCliente;
		case 2:
			return this.name;
		case 3:
			return this.movimiento.getDescripcion();
		case 4:
			return Utils.getDateAsString(this.movimiento.getFecha());
		case 5:
			return this.movimiento.getMontoDebe();
		case 6:
			return this.movimiento.getMontoHaber();
		}
		return null;
	}
	
	public long getIdCliente() {
		return idCliente;
	}

	public String getName() {
		return name;
	}

	public Movimiento getMovimiento() {
		return movimiento;
	}

	@Override
	public int compareTo(TableRow o) {
		SummaryRow sm = (SummaryRow) o;
		return movimiento.getFecha().compareTo(sm.movimiento.getFecha());
	}
}
