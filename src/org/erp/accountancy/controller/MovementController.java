package org.erp.accountancy.controller;

import java.util.Collection;
import java.util.List;

import org.erp.accountancy.hibernate.controller.Controller;
import org.erp.accountancy.hibernate.entities.Movimiento;
import org.erp.accountancy.mov.filters.MovFilter;
import org.hibernate.Session;

public class MovementController {

	/**
	 * Returns all the movements found in the database.
	 * 
	 * @return List of movements in the database
	 */
	@SuppressWarnings("unchecked")
	public static List<Movimiento> findAll() {
		Session session = null;
		List<Movimiento> movimientos = null;

		try {
			session = Controller.getInstance().getSession();
			session.beginTransaction();
			movimientos = session.createCriteria(Movimiento.class).list();
		} finally {
			if (session != null) {
				if (session.getTransaction() != null)
					session.getTransaction().commit();
			}
		}

		return movimientos;
	}

	/**
	 * Returns a list of movements filtered by a MovFilter filter given by
	 * parameter.
	 * 
	 * @param filter
	 *            Filter to be applied
	 * @return List of filtered movements
	 */
	public static List<Movimiento> filter(MovFilter filter) {
		Collection<Movimiento> list = MovementController.findAll();

		return filter.filter(list);
	}

	/**
	 * Saves an instance of Movimiento into the database. If it is currently in
	 * the database, this method updates it.
	 * 
	 * @param movimiento
	 *            Movement to be added or updated
	 */
	public static void saveOrUpdateMovement(Movimiento movimiento) {
		if (movimiento != null) {
			Session session = Controller.getInstance().getSession();
			session.beginTransaction();
			session.saveOrUpdate(movimiento);
			session.getTransaction().commit();
		}
	}

	/**
	 * This method saves a given movement into the database.
	 * 
	 * @param movimiento
	 *            Movement to be saved
	 */
	public static void saveMovement(Movimiento movimiento) {
		if (movimiento != null) {
			Session session = Controller.getInstance().getSession();
			session.beginTransaction();
			session.save(movimiento);
			session.getTransaction().commit();
		}
	}

	/**
	 * Carries out the removal of a movement present in the database.
	 * 
	 * @param movimiento
	 *            Movement to be removed
	 */
	public static void removeMovement(Movimiento movimiento) {
		if (movimiento != null) {
			Session session = Controller.getInstance().getSession();
			session.beginTransaction();
			session.delete(movimiento);
			session.getTransaction().commit();
		}
	}

	/**
	 * Given an specific id, this method returns the movement that corresponds
	 * with that id.
	 * 
	 * @param id
	 *            Movement's id to be retrieved
	 * @return Movement with the given id
	 */
	public static Movimiento getMovement(Long id) {
		Movimiento movimiento;

		Session session = Controller.getInstance().getSession();
		session.beginTransaction();
		try {
			movimiento = (Movimiento) session.load(Movimiento.class, id);
			movimiento.getFecha();
		} catch (Exception e) {
			movimiento = null;
		} finally {
			session.getTransaction().commit();
		}

		return movimiento;
	}

}
