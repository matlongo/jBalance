package org.erp.accountancy.clients.comparators;

import org.erp.accountancy.hibernate.entities.Cliente;

/**
 * This ClientComparator compares two clients by their names.
 * 
 * @author mathias
 *
 */
public class NameComparator extends ClientComparator {

	@Override
	public Comparable<String> getValue(Cliente c) {
		return c.getNombre();
	}
	
	

}
