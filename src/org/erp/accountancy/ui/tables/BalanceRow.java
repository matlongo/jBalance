package org.erp.accountancy.ui.tables;

import org.erp.accountancy.controller.Saldo;
import org.erp.accountancy.hibernate.entities.Cliente;

public class BalanceRow implements TableRow {

	private Cliente cliente;
	private Saldo saldo;
	
	public BalanceRow(Cliente cliente, Saldo saldo) {
		this.cliente = cliente;
		this.saldo = saldo;
	}
	
	public Cliente getCliente(){
		return cliente;
	}
	
	@Override
	public int compareTo(TableRow o) {
		Cliente otherCliente = ((BalanceRow) o).getCliente();
		return cliente.getNombre().compareTo(otherCliente.getNombre());
	}

	@Override
	public Object getValueAt(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return cliente.getIdCliente();
		case 1:
			return cliente.getNombre();
		case 2:
			return saldo.getSaldoDebe();
		case 3:
			return saldo.getSaldoHaber();
		case 4:
			return saldo.getSaldoHaber() - saldo.getSaldoDebe();
		}
		return null;
	}

}
