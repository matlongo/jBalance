package org.erp.accountancy.mov.filters;

import org.erp.accountancy.hibernate.entities.Movimiento;

public class MFTrue extends MovFilter {

	@Override
	public boolean apply(Movimiento mov) {
		return true;
	}

	@Override
	public boolean previo(Movimiento mov) {
		return false;
	}

}
