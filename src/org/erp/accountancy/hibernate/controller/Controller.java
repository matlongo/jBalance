package org.erp.accountancy.hibernate.controller;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class Controller {

	private SessionFactory sessionFactory;
	private ServiceRegistry serviceRegistry;
	private Session session;
	private static Controller instance;
	
	private Controller(){
	}
	
	public static Controller getInstance(){
		if (instance == null){
			instance = new Controller();
		}
		return instance;
	}

	public SessionFactory getSessionFactory() {
		if (this.sessionFactory == null) {
			Configuration configuration = new Configuration();
			configuration.configure();
			serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
					.buildServiceRegistry();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		}
		return sessionFactory;
	}

	public Session getSession(){
		if (this.session == null  ||  !this.session.isOpen()){
			session = this.getSessionFactory().openSession();
		}
		return session;
	}

	public void close(){
		try {
			if (session != null  &&  session.isOpen())
				session.close();
			if (sessionFactory != null  &&  !sessionFactory.isClosed())
				sessionFactory.close();
		} catch (Exception e) {
			System.out.println("Error al cerrar las sesiones");
			e.printStackTrace();
		}
		session = null;
		sessionFactory = null;
	}
}
