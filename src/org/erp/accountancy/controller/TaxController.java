package org.erp.accountancy.controller;

import java.util.List;

import org.erp.accountancy.hibernate.controller.Controller;
import org.erp.accountancy.hibernate.entities.Impuesto;
import org.hibernate.Session;

public class TaxController {

	public static List<Impuesto> findAll(){
		Session session = Controller.getInstance().getSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Impuesto> impuestos = session.createCriteria(Impuesto.class).list();
		session.getTransaction().commit();
		
		return impuestos;
	}
	
	public static void saveOrUpdateTax(Impuesto impuesto){
		if (impuesto != null) {
			Session session = Controller.getInstance().getSession();
			session.beginTransaction();
			session.saveOrUpdate(impuesto);
			session.getTransaction().commit();
		}
	}
	
	public static void saveTax(Impuesto impuesto){
		if (impuesto != null) {
			Session session = Controller.getInstance().getSession();
			session.beginTransaction();
			session.save(impuesto);
			session.getTransaction().commit();
		}
	}
	
	public static int removeImpuesto(Impuesto impuesto){
		int status = -1;
		Session session = null;
		try {
			session = Controller.getInstance().getSession();
			session.beginTransaction();
			session.delete(impuesto);
			session.getTransaction().commit();
			status = 0;
		}  
		catch (Exception e){
			if (session != null){
				session.getTransaction().rollback();
			}
		}
		finally {
			session.close();
		}
		
		return status;
	}
	
}
