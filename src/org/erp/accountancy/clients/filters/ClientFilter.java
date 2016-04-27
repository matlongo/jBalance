package org.erp.accountancy.clients.filters;

import java.util.ArrayList;
import java.util.List;

import org.erp.accountancy.hibernate.entities.Cliente;

/**
 * This class represents a filter for the different clients. The different
 * subclasses will filter different attributes of each client.
 * 
 * @author mathias
 *
 */
public abstract class ClientFilter {

	/**
	 * This method filters all the clients of the input list, and returns all
	 * the clients that were filtered.
	 * 
	 * @param list
	 *            List of clients to be filtered.
	 * @return Filtered list of clients.
	 */
	public List<Cliente> getFilteredList(List<Cliente> list) {
		List<Cliente> result = new ArrayList<>();

		for (Cliente c : list) {
			if (this.filter(c))
				result.add(c);
		}

		return result;
	}

	/**
	 * Returns whether the input client is going to be filtered or not.
	 * 
	 * @param Client
	 *            to be analyzed.
	 * @return True if the input client is going to be filtered, otherwise
	 *         returns False.
	 */
	public abstract boolean filter(Cliente c);

}
