package org.erp.accountancy.clients.filters;

import org.erp.accountancy.hibernate.entities.Cliente;

/**
 * This class represents is a subclass of ClientFilter. This filters the clients
 * from a specific city.
 * 
 * @author mathias
 *
 */
public class CityFilter extends ClientFilter {
	
	/**
	 * City used to filter the clients.
	 */
	private String city;
	
	/**
	 * Constructor that gets the city to be used for filtering.
	 * 
	 * @param city
	 *            City used for filtering
	 */
	public CityFilter(String city){
		this.city = city;
	}
	
	@Override
	public boolean filter(Cliente c) {
		String otherName = c.getCiudad().toLowerCase();
		
		for (String word: city.split(" ")){
			if (otherName.contains(word.toLowerCase()))
				return true;
		}
		
		return false;
	}

}
