package org.erp.accountancy.clients.comparators;

import org.erp.accountancy.hibernate.entities.Cliente;

/**
 * This ClientComparator compares two clients by their IDs.
 * 
 * @author mathias
 *
 */
public class IdComparator extends ClientComparator {

	@Override
	public Comparable<Long> getValue(Cliente c) {
		return c.getIdCliente();
	}

}
