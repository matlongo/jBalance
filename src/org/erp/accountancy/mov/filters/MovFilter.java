package org.erp.accountancy.mov.filters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.erp.accountancy.hibernate.entities.Movimiento;

public abstract class MovFilter {

	public List<Movimiento> filter(Collection<Movimiento> collection){
		List<Movimiento> result = new ArrayList<>();
		for (Movimiento mov: collection){
			if (this.apply(mov))
				result.add(mov);
		}
		
		return result;
	}
	
	public List<Movimiento> getMovsPrevios(Collection<Movimiento> collection){
		List<Movimiento> result = new ArrayList<>();
		for (Movimiento mov: collection){
			if (this.previo(mov))
				result.add(mov);
		}
		return result;
	}
	
	public abstract boolean apply(Movimiento mov);
	public abstract boolean previo(Movimiento mov);
}
