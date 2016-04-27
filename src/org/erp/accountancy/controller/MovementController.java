package org.erp.accountancy.controller;

import java.util.Collection;
import java.util.List;

import org.erp.accountancy.hibernate.controller.Controller;
import org.erp.accountancy.hibernate.entities.Movimiento;
import org.erp.accountancy.mov.filters.MovFilter;
import org.hibernate.Session;

public class MovementController {

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

	public static List<Movimiento> filter(MovFilter filter) {
		Collection<Movimiento> list = MovementController.findAll();

		return filter.filter(list);
	}

	public static void saveOrUpdateMovement(Movimiento movimiento) {
		if (movimiento != null) {
			Session session = Controller.getInstance().getSession();
			session.beginTransaction();
			session.saveOrUpdate(movimiento);
			session.getTransaction().commit();
		}
	}

	public static void saveMovement(Movimiento movimiento) {
		if (movimiento != null) {
			Session session = Controller.getInstance().getSession();
			session.beginTransaction();
			session.save(movimiento);
			session.getTransaction().commit();
		}
	}

	public static void removeImpuesto(Movimiento movimiento) {
		if (movimiento != null) {
			Session session = Controller.getInstance().getSession();
			session.beginTransaction();
			session.delete(movimiento);
			session.getTransaction().commit();
		}
	}

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
