package org.erp.accountancy.clients.comparators;

import org.erp.accountancy.hibernate.entities.Cliente;

/**
 * This ClientComparator compares two clients by their telephone numbers.
 * 
 * @author mathias
 *
 */
public class TelComparator extends ClientComparator {

	@Override
	public Comparable<String> getValue(Cliente c) {
		return c.getTelefono();
	}

}
