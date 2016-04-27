package org.erp.accountancy.mov.filters;

import java.util.Calendar;
import java.util.Date;

import org.erp.accountancy.hibernate.entities.Movimiento;
import org.erp.accountancy.utils.Utils;

public class MFDia extends MovFilter {

	@Override
	public boolean apply(Movimiento mov) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		
		Calendar movCalendar = Calendar.getInstance();
		calendar.setTime(mov.getFecha());
		
		return calendar.get(Calendar.DAY_OF_MONTH) == movCalendar.get(Calendar.DAY_OF_MONTH)
				&& calendar.get(Calendar.YEAR) == Utils.getCurrentYear();
	}

	@Override
	public boolean previo(Movimiento mov) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		return mov.getFecha().compareTo(calendar.getTime()) < 0;
	}

}
