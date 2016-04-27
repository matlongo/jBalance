package org.erp.accountancy.clients.filters;

import org.erp.accountancy.hibernate.entities.Cliente;

/**
 * This class represents is a subclass of ClientFilter. This filters the clients
 * with a specific address.
 * 
 * @author mathias
 *
 */
public class AddressFilter extends ClientFilter {

	/**
	 * Address used to filter the clients.
	 */
	private String address;

	/**
	 * Constructor that gets the address to be used for filtering.
	 * 
	 * @param address
	 *            Address used for filtering
	 */
	public AddressFilter(String address) {
		this.address = address;
	}

	@Override
	public boolean filter(Cliente c) {
		String otherName = c.getDireccion().toLowerCase();

		for (String word : address.split(" ")) {
			if (otherName.contains(word.toLowerCase()))
				return true;
		}

		return false;
	}

}
