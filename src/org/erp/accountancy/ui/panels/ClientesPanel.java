package org.erp.accountancy.ui.panels;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JOptionPane;

import org.erp.accountancy.clients.filters.CityFilter;
import org.erp.accountancy.clients.filters.ClientFilter;
import org.erp.accountancy.controller.ClienteController;
import org.erp.accountancy.hibernate.entities.Cliente;
import org.erp.accountancy.ui.PanelManager;
import org.erp.accountancy.ui.dialogs.ClientePickDialog;
import org.erp.accountancy.ui.forms.ClienteForm;
import org.erp.accountancy.ui.tables.ClienteTablePanel;

public class ClientesPanel extends ButtonsPanel {

	private static final long serialVersionUID = 8575468424756009103L;
	private static final String TITLE = "RESUMEN DE CLIENTES";

	public ClientesPanel() {
		super(getButtonsText(), TITLE);
	}

	/**
	 * Returns the array of names to be shown in the GUI
	 * 
	 * @return Array of names to be displayed
	 */
	private static String[] getButtonsText() {
		String[] buttonsText = { "Ingresar nuevo cliente", "Modificación de cliente", "Eliminar cliente",
				"Listar clientes", "Listar clientes por ciudad", "Volver" };
		return buttonsText;
	}

	/**
	 * Shows the form to insert a new client to the database.
	 */
	private void newClient() {
		ClienteForm form = new ClienteForm();
		PanelManager.getInstance().setNewPanel(form, "formulario-cliente");
	}

	/**
	 * Shows the jTable that contains the list of clients
	 */
	private void showClients() {
		ClienteTablePanel panel = new ClienteTablePanel(PanelManager.CLIENTES_PANEL);
		panel.refresh();
		PanelManager.getInstance().setNewPanel(panel, "clientes-table-panel");
	}

	/**
	 * Provides the GUI to modify a particular client.
	 */
	private void modifyClient() {
		Cliente cliente = ClientePickDialog.getCliente();
		if (cliente != null) {
			ClienteForm form = new ClienteForm(cliente);
			PanelManager.getInstance().setNewPanel(form, "formulario-cliente");
		}
	}

	/**
	 * Provides the GUI to remove a particular client.
	 */
	private void removeClient() {
		Cliente client = ClientePickDialog.getCliente();
		if (client != null) {
			int answer = JOptionPane.showConfirmDialog(
					PanelManager.getInstance().getMainFrame(), "Esta seguro que desea eliminar el cliente "
							+ client.getNombre() + "? " + "Se eliminarán todos sus movimientos.",
					"Atención", JOptionPane.YES_NO_OPTION);
			if (answer == JOptionPane.YES_OPTION) {
				ClienteController.removeClient(client);
			}
		}
	}

	/**
	 * This method pops up a new window so that the user can choose the city
	 * they want to list the clients from. This will use all the cities used
	 * among all the clients.
	 */
	private void clientListByCities() {
		List<String> cities = ClienteController.getCities();
		String city = (String) JOptionPane.showInputDialog(this, "Seleccione la ciudad:", TITLE,
				JOptionPane.PLAIN_MESSAGE, null, cities.toArray(), "");

		ClientFilter filter = new CityFilter(city);
		String title = "LISTADO DE CLIENTES DE " + city;
		ClienteTablePanel panel = new ClienteTablePanel(filter, title);
		panel.refresh();
		panel.print();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttons[0]) { // Nuevo Cliente
			this.newClient();
		} else if (e.getSource() == buttons[1]) { // Modificación cliente
			this.modifyClient();
		} else if (e.getSource() == buttons[2]) { // Eliminar
			this.removeClient();
		} else if (e.getSource() == buttons[3]) { // Listado
			this.showClients();
		} else if (e.getSource() == buttons[4]) { // Listado por ciudad
			this.clientListByCities();
		} else if (e.getSource() == buttons[5]) { // Volver
			PanelManager.getInstance().switchPanel(PanelManager.WELCOME_PANEL);
		}
	}

}
