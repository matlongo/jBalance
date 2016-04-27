package org.erp.accountancy.clients.comparators;

import org.erp.accountancy.hibernate.entities.Cliente;

/**
 * This ClientComparator compares two clients by their cities.
 * 
 * @author mathias
 *
 */
public class CityComparator extends ClientComparator {

	@Override
	public Comparable<String> getValue(Cliente c) {
		return c.getCiudad();
	}

}
