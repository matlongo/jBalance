package org.erp.accountancy.mov.filters;

import java.util.Calendar;
import java.util.Date;

import org.erp.accountancy.hibernate.entities.Movimiento;

public class MF2Fechas extends MovFilter {

	private Calendar f1, f2;
	
	public MF2Fechas(Calendar f1, Calendar f2) {
		this.f1 = f1;
		this.f2 = f2;
	}
	
	public MF2Fechas(Date f1, Date f2) {
		this.f1 = Calendar.getInstance();
		this.f1.setTime(f1);
		this.f2 = Calendar.getInstance();
		this.f2.setTime(f2);
	}
	
	@Override
	public boolean apply(Movimiento mov) {
		return f1.getTime().compareTo(mov.getFecha()) <= 0  &&  
				mov.getFecha().compareTo(f2.getTime()) <= 0;
	}
	
	@Override
	public boolean previo(Movimiento mov) {
		return mov.getFecha().compareTo(f1.getTime()) < 0;
	}

}
