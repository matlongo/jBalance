package org.erp.accountancy.clients.comparators;

import java.util.Comparator;

import org.erp.accountancy.hibernate.entities.Cliente;

/**
 * This class represents a comparator instance used to sort a list of clients.
 * The different subclasses will consider different attributes to compare.
 * 
 * @author mathias
 *
 */
public abstract class ClientComparator implements Comparator<Cliente> {

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int compare(Cliente c1, Cliente c2) {
		Comparable comp1 = this.getValue(c1);
		Comparable comp2 = this.getValue(c2);

		return comp1.compareTo(comp2);
	}

	/**
	 * Returns the value of the client's feature to be compared by this
	 * comparator.
	 * 
	 * @param c
	 *            Client who contains this value
	 * @return Value to be compared
	 */
	public abstract Comparable<?> getValue(Cliente c);

}
