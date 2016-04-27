package org.erp.accountancy.customqueries;

import java.util.List;

import org.erp.accountancy.hibernate.controller.Controller;
import org.erp.accountancy.hibernate.entities.Cliente;
import org.erp.accountancy.mov.filters.MovFilter;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

public abstract class CustomQuery {

	/**
	 * Returns a list of clients that matches with this custom query.
	 * 
	 * @return List of clients that matches with this custom query.
	 */
	@SuppressWarnings("unchecked")
	public List<Cliente> getClientes() {
		Session session = null;
		List<Cliente> result = null;
		try {
			session = Controller.getInstance().getSession();
			session.beginTransaction();

			Query query = this.getQuery(session);
			result = (List<Cliente>) query.list();
			for (Cliente cliente : result) {
				Hibernate.initialize(cliente.getMovimientos());
			}
		} finally {
			if (session != null) {
				if (session.getTransaction() != null)
					session.getTransaction().commit();
			}
		}

		return result;
	}

	/**
	 * Returns the custom query to be used in order to get the clients, with all
	 * the parameters set if needed.
	 * 
	 * @param session
	 *            Session needed in order to create the query.
	 * @return Custom query to be used in order to get the clients.
	 */
	public abstract Query getQuery(Session session);

	/**
	 * Returns the parameters in a String, so they can be printed.
	 * 
	 * @return Parameters in a String.
	 */
	public abstract String getParamsAsString();

	/**
	 * Returns the filter that will be used in order to filter the movements of
	 * each client.
	 * 
	 * @return Filter that will be used in order to filter the movements of each
	 *         client.
	 */
	public abstract MovFilter getFilter();
}
