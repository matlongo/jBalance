package org.erp.accountancy.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.erp.accountancy.configuration.ConfigurationManager;
import org.erp.accountancy.controller.ClienteController;
import org.erp.accountancy.controller.MovementController;
import org.erp.accountancy.hibernate.entities.Cliente;
import org.erp.accountancy.hibernate.entities.Impuesto;
import org.erp.accountancy.hibernate.entities.Movimiento;
import org.erp.accountancy.utils.Utils;

public class Mock {

	public static final String[] FIRST_NAMES = {"Juan", "Roberto", "Alberto", "Nicolas", "Hernan", "Carlos",
			"Alfonso", "Pedro", "Jorge", "José", "Antonio", "Pablo", "Ramiro", "Gastón", "Manuel",
			"Analía", "Inés", "Rosana", "Virginia", "Lourdes", "Carmen", "Yamila", "Elisabet",
			"Mariana", "Alejandra", "Rosario", "Belén", "Dora", "Ester", "Gabriela"};
	public static final String[] LAST_NAMES = {"Ramirez", "Gonzalez", "Pereyra", "Ferraro", "Rodriguez",
			"Ceferino", "Rios", "Corbellini", "Pérez", "García", "Sánchez", "Zamudio", "Rago", "Villavicencio",
			"Berdún", "Longo", "Mateos", "Diaz"};
	public static final String[] CITIES = {"Tandil", "Ayacucho", "Rauch"};
	public static final String[] ADRESSES = {"Rivadavia 534", "Miguens 1323", "9 de Julio 1583",
			"25 de Mayo 4563", "Irigoyen 609", "Alem 1539", "Maipu 3453", "Maipu 2342", "Rodriguez 694",
			"Alvear 890"};
	
	public static Movimiento getMovimiento(){
		List<Cliente> clients = ClienteController.findAll();
		int id = (int)(Math.random() * clients.size());
		Cliente cliente = clients.get((int)Math.floor(id));
		Movimiento movimiento = new Movimiento();
		movimiento.setCliente(cliente);
		movimiento.setComprobante((int)Math.random() * 5000);
		movimiento.setDescripcion("");
		movimiento.setFecha(getRandomDate());
		
		if (Math.random() > 0.5){
			movimiento.setMontoDebe(Math.round(Math.random() * 5000));
			movimiento.setMontoHaber(0);
		} else {
			movimiento.setMontoHaber(Math.round(Math.random() * 5000));
			movimiento.setMontoDebe(0);
		}

		cliente.getMovimientos().add(movimiento);
		
		return movimiento;
	}
	
	public static Date getRandomDate(){
		Calendar calendar = Calendar.getInstance();
		int day = (int)(Math.random() * 28) + 1;
		int month = (int)(Math.random() * 12);
		int year = Utils.getCurrentYear();
		
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);
		
		return calendar.getTime();
	}
	
	public static Cliente getCliente(){
		Cliente cliente = new Cliente();
		int idCiudad = (int)Math.floor((int)(Math.random() * CITIES.length));
		int idName = (int)Math.floor((int)(Math.random() * FIRST_NAMES.length));
		int idLastName = (int)Math.floor((int)(Math.random() * LAST_NAMES.length));
		int idDireccion = (int)Math.floor((int)(Math.random() * ADRESSES.length));
		
		cliente.setCiudad(CITIES[idCiudad]);
		cliente.setCuit("");
		cliente.setDireccion(ADRESSES[idDireccion]);
		cliente.setHonorarios(ConfigurationManager.getInstance().getProperty(ConfigurationManager.HONORARIOS_KEY));
		cliente.setImpuestos(new ArrayList<Impuesto>());
		cliente.setMovimientos(new ArrayList<Movimiento>());
		cliente.setNombre(FIRST_NAMES[idName] + " " + LAST_NAMES[idLastName]);
		cliente.setTelefono("");
		cliente.setOtherFeatures(new HashMap<>());
		
		return cliente;
	}
	
	public static void saveData(int nClients, int nMovements){
		for (int i = 0; i < nClients; i++){
			Cliente cliente = getCliente();
			ClienteController.saveClient(cliente);
		}
		for (int i = 0; i < nMovements; i++){
			Movimiento movement = getMovimiento();
			MovementController.saveMovement(movement);
		}
	}
	
}
