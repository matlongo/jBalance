package org.erp.accountancy.mov.filters;

import java.util.Calendar;

import org.erp.accountancy.hibernate.entities.Movimiento;
import org.erp.accountancy.utils.Utils;

public class MFMes extends MovFilter {

	private int month;
	
	public MFMes(int month) {
		this.month = month;
	}

	@Override
	public boolean apply(Movimiento mov) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(mov.getFecha());
		
		return calendar.get(Calendar.MONTH) == month
				&& calendar.get(Calendar.YEAR) == Utils.getCurrentYear(); 
	}
	
	@Override
	public boolean previo(Movimiento mov) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(mov.getFecha());
		
		return (calendar.get(Calendar.MONTH) < month
				&& calendar.get(Calendar.YEAR) == Utils.getCurrentYear()) ||
				(calendar.get(Calendar.YEAR) < Utils.getCurrentYear());
	}
}
