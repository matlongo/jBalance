package org.erp.accountancy.controller;

import java.util.List;

import org.erp.accountancy.hibernate.controller.Controller;
import org.erp.accountancy.hibernate.entities.Cliente;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HonorariosManager {

	public void updateHonorariosClientesMonto(List<Cliente> clients, float honorarios) {
		Session session = Controller.getInstance().getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			for (Cliente c : clients) {
				c.setHonorarios(this.getRounded(honorarios));
				session.update(c);
			}
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
	}
	
	public void updateHonorariosClientesAdd(List<Cliente> clients, float montoToAdd){
		Session session = Controller.getInstance().getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			for (Cliente c : clients) {
				float montoPrevio = c.getHonorarios();
				c.setHonorarios(this.getRounded(montoPrevio + montoToAdd));
				session.update(c);
			}
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
	}
	
	public void updateHonorariosClientesPorcentaje(List<Cliente> clients, float factor){
		Session session = Controller.getInstance().getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			for (Cliente c : clients) {
				float montoPrevio = c.getHonorarios();
				c.setHonorarios(this.getRounded(montoPrevio * factor));
				session.update(c);
			}
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
	}
	
	private float getRounded(float honorario){
		float rounded = honorario;
		rounded = Math.round(rounded / 5);
		rounded = rounded * 5;
		return rounded;
	}
	
	public void updateHonorariosClientes(List<Cliente> clients, float value, String type){
		switch (type) {
		case "Aumentar en un monto":
			updateHonorariosClientesAdd(clients, value);
			break;

		case "Aumentar en un porcentaje":
			float factor = value / 100 + 1;
			updateHonorariosClientesPorcentaje(clients, factor);
			break;
			
		case "Establecer un monto general":
			updateHonorariosClientesMonto(clients, value);
			break;
		}
	}
}
