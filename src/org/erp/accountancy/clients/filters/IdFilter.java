package org.erp.accountancy.clients.filters;

import org.erp.accountancy.hibernate.entities.Cliente;

/**
 * This class represents is a subclass of ClientFilter. This filters the clients
 * with a specific id.
 * 
 * @author mathias
 *
 */
public class IdFilter extends ClientFilter {

	/**
	 * Id used for filtering.
	 */
	private long id;

	/**
	 * Constructor that receives the id that will be used when filtering.
	 * 
	 * @param id
	 *            Id used for filtering
	 */
	public IdFilter(long id) {
		this.id = id;
	}

	@Override
	public boolean filter(Cliente c) {
		return c.getIdCliente() == id;
	}

}
