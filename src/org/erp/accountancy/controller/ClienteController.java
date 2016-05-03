package org.erp.accountancy.controller;

import java.util.ArrayList;
import java.util.List;

import org.erp.accountancy.clients.filters.ClientFilter;
import org.erp.accountancy.customqueries.CQClientes;
import org.erp.accountancy.hibernate.controller.Controller;
import org.erp.accountancy.hibernate.entities.Cliente;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

public class ClienteController {

	/**
	 * Returns a list of all the clients stored in the database
	 * 
	 * @return List of clients
	 */
	public static List<Cliente> findAll() {
		return new CQClientes().getClientes();
	}

	/**
	 * Returns a list of clients that matches with a specific ClientFilter.
	 * 
	 * @param filter
	 *            Filter to be used to filter the list of clients
	 * @return List of clients
	 */
	public static List<Cliente> filter(ClientFilter filter) {
		List<Cliente> list = ClienteController.findAll();

		return filter.getFilteredList(list);
	}

	/**
	 * Saves or updates a client into the database. It uses Hibernate's method
	 * saveOrUpdate().
	 * 
	 * @param client
	 *            Client to be saved
	 */
	public static void saveOrUpdateClient(Cliente client) {
		if (client != null) {
			Session session = Controller.getInstance().getSession();
			session.beginTransaction();
			session.saveOrUpdate(client);
			session.getTransaction().commit();
		}
	}

	/**
	 * Saves a given client into the database. It uses Hibernate's method
	 * save().
	 * 
	 * @param client
	 *            Client to be saved
	 */
	public static void saveClient(Cliente client) {
		if (client != null) {
			Session session = Controller.getInstance().getSession();
			session.beginTransaction();
			session.save(client);
			session.getTransaction().commit();
		}
	}

	/**
	 * Removes a given client from the database.
	 * 
	 * @param client
	 *            Client to be removed
	 */
	public static void removeClient(Cliente client) {
		if (client != null) {
			Session session = Controller.getInstance().getSession();
			session.beginTransaction();
			session.delete(client);
			session.getTransaction().commit();
		}
	}

	/**
	 * Given a specific id, this method returns the client that matches with
	 * that id. In case that id does not exist, it returns null
	 * 
	 * @param id
	 *            Id from the client to be loaded.
	 * @return Client that matches with the given id.
	 */
	public static Cliente getCliente(Long id) {
		Cliente cliente;

		Session session = Controller.getInstance().getSession();
		session.beginTransaction();
		try {
			cliente = (Cliente) session.load(Cliente.class, id);
			cliente.getCiudad();
			Hibernate.initialize(cliente.getMovimientos());
		} catch (Exception e) {
			cliente = null;
		} finally {
			session.getTransaction().commit();
		}

		return cliente;
	}

	/**
	 * Returns the list of different cities among all the clients stored in the
	 * database.
	 * 
	 * @return List of different cities
	 */
	public static List<String> getCities() {
		List<String> cities = new ArrayList<>();
		List<Cliente> clients = ClienteController.findAll();

		for (Cliente client : clients){
			String city = client.getCiudad().trim().toUpperCase();
			if (!cities.contains(city))
				cities.add(city);
		}

		return cities;
	}

	/**
	 * This method carries the update of the identifier of a particular Client.
	 * 
	 * @param cliente
	 *            Client to be updated
	 * @param id
	 *            New identifier to be used
	 * @return Status of the operation: 0 if everything was done correctly; 1
	 *         otherwise.
	 */
	public static int updateIdentifier(Cliente cliente, Long id) {
		if (cliente == null)
			return 1;

		try {
			String hql="update cliente set idCliente=? where idCliente=? ";
			Query query = Controller.getInstance().getSession().createQuery(hql);
			query.setLong(0,id);
			query.setLong(1,cliente.getIdCliente());
			query.executeUpdate();
			Controller.getInstance().getSession().beginTransaction().commit();
		} catch (Exception e){
			return 1;
		}
		
		return 0;
	}
}
