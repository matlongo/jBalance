package org.erp.accountancy.mov.filters;

import java.util.Calendar;

import org.erp.accountancy.hibernate.entities.Movimiento;
import org.erp.accountancy.utils.Utils;

public class MFYear extends MovFilter {

	@Override
	public boolean apply(Movimiento mov) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(mov.getFecha());
		
		return calendar.get(Calendar.YEAR) == Utils.getCurrentYear();
	}

	@Override
	public boolean previo(Movimiento mov) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(mov.getFecha());
		
		return calendar.get(Calendar.YEAR) < Utils.getCurrentYear();
	}

}
