package org.erp.accountancy.controller;

import java.util.Collection;
import java.util.HashMap;

import org.erp.accountancy.customqueries.CQBalance;
import org.erp.accountancy.customqueries.CustomQuery;
import org.erp.accountancy.hibernate.entities.Cliente;
import org.erp.accountancy.hibernate.entities.Movimiento;

public class BalanceController {

	public HashMap<Cliente, Saldo> getBalance(){
		HashMap<Cliente, Saldo> balance = new HashMap<Cliente, Saldo>();
		CustomQuery query = new CQBalance();
		
		for (Cliente c: query.getClientes()){
			balance.put(c, getSaldo(c.getMovimientos()));
		}
		
		return balance;
	}
	
	public Saldo getSaldo(Collection<Movimiento> movimientos){
		Saldo saldo = new Saldo();
		
		for (Movimiento mov: movimientos){
			saldo.addSaldoDebe(mov.getMontoDebe());
			saldo.addSaldoHaber(mov.getMontoHaber());
		}
		
		return saldo;
	}
}
