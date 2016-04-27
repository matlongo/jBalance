package org.erp.accountancy.clients.comparators;

import org.erp.accountancy.hibernate.entities.Cliente;

/**
 * This ClientComparator compares two clients by their CUITs.
 * 
 * @author mathias
 *
 */
public class CuitComparator extends ClientComparator {

	@Override
	public Comparable<String> getValue(Cliente c) {
		return c.getCuit();
	}

}
