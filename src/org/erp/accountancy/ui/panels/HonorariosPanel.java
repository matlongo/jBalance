package org.erp.accountancy.ui.panels;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.erp.accountancy.controller.ClienteController;
import org.erp.accountancy.controller.HonorariosManager;
import org.erp.accountancy.controller.MovementController;
import org.erp.accountancy.customqueries.CQClientes;
import org.erp.accountancy.hibernate.entities.Cliente;
import org.erp.accountancy.hibernate.entities.Movimiento;
import org.erp.accountancy.ui.PanelManager;
import org.erp.accountancy.ui.dialogs.ClientePickDialog;
import org.erp.accountancy.ui.dialogs.HonorariosDialog;
import org.erp.accountancy.utils.Utils;

public class HonorariosPanel extends ButtonsPanel {

	private static final long serialVersionUID = -8453853477207792974L;
	private static final String TITLE = "HONORARIOS";

	public HonorariosPanel() {
		super(getButtonsText(), TITLE);
		
	}
	
	private static String[] getButtonsText(){
		String[] buttonsText = {
				"Aumentar honorarios", "Modificar honorarios de cliente", 
				"Incorpora honorarios", "Volver"
		};
		return buttonsText;
	}
	
	private void updateHonorarios(List<Cliente> clients){
		HonorariosDialog dialog = new HonorariosDialog();
		if (dialog.getTipo() != null){
			HonorariosManager manager = new HonorariosManager();
			manager.updateHonorariosClientes(clients, dialog.getMonto(), dialog.getTipo());
		}
	}

	private void emitirHonorarios() {
		CQClientes query = new CQClientes();
		List<Cliente> clientes = query.getClientes();
		for (Cliente cliente: clientes){
			Movimiento movimiento = new Movimiento();
			movimiento.setCliente(cliente);
			movimiento.setDescripcion("Aviso del mes de "+Utils.getMonthByInt(Utils.getCurrentMonth()));
			movimiento.setMontoDebe(cliente.getHonorarios());
			movimiento.setFecha(new Date());
			movimiento.setMontoHaber(0);
			movimiento.setId(0);
			
			cliente.getMovimientos().add(movimiento);
						
			ClienteController.saveOrUpdateClient(cliente);
			MovementController.saveMovement(movimiento);
		}
		
		JOptionPane.showMessageDialog(PanelManager.getInstance().getMainFrame(), 
				"Honorarios incorporados exitosamente.");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttons[0]){ //Aumentar honorarios
			CQClientes query = new CQClientes();
			List<Cliente> clients = query.getClientes();
			this.updateHonorarios(clients);
		}
		if (e.getSource() == buttons[1]){ //Modificar honorarios
			Cliente cliente = ClientePickDialog.getCliente();
			if (cliente != null){
				List<Cliente> clients = new ArrayList<>();
				clients.add(cliente);
				this.updateHonorarios(clients);
			}
		}
		if (e.getSource() == buttons[2]){
			this.emitirHonorarios();
		}
		if (e.getSource() == buttons[3]){
			PanelManager.getInstance().switchPanel(PanelManager.WELCOME_PANEL);
		}
	}

}
