package org.erp.accountancy.clients.filters;

import org.erp.accountancy.hibernate.entities.Cliente;

/**
 * This class represents is a subclass of ClientFilter. This does not filter any
 * client.
 * 
 * @author mathias
 *
 */
public class TrueFilter extends ClientFilter {

	@Override
	public boolean filter(Cliente c) {
		return true;
	}

}
