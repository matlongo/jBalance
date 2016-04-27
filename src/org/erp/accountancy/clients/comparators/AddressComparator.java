package org.erp.accountancy.clients.comparators;

import org.erp.accountancy.hibernate.entities.Cliente;

/**
 * Comparator that compares two clients by their addresses.
 * 
 * @author mathias
 *
 */
public class AddressComparator extends ClientComparator {

	@Override
	public Comparable<String> getValue(Cliente c) {
		return c.getDireccion();
	}

}
