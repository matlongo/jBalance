package org.erp.accountancy.controller;

public class Saldo {

	private double saldoDebe, saldoHaber;
	
	public Saldo(){
		saldoDebe = 0;
		saldoHaber = 0;
	}

	public double getSaldoDebe() {
		return saldoDebe;
	}

	public double getSaldoHaber() {
		return saldoHaber;
	}
	
	public void addSaldoDebe(double saldo){
		saldoDebe += saldo;
	}
	
	public void addSaldoHaber(double saldo){
		saldoHaber += saldo;
	}
}
