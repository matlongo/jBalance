package org.erp.accountancy.clients.filters;

import org.erp.accountancy.hibernate.entities.Cliente;

/**
 * This class represents is a subclass of ClientFilter. This filters the clients
 * with a specific name.
 * 
 * @author mathias
 *
 */
public class NameFilter extends ClientFilter {

	/**
	 * Name used for filtering.
	 */
	private String name;
	
	/**
	 * Constructor that receives the name that will be used when filtering.
	 * 
	 * @param name
	 *            Name used for filtering
	 */
	public NameFilter(String name){
		this.name = name;
	}
	
	@Override
	public boolean filter(Cliente c) {
		String otherName = c.getNombre().toLowerCase();
		
		for (String word: name.split(" ")){
			if (otherName.contains(word.toLowerCase()))
				return true;
		}
		
		return false;
	}

}
