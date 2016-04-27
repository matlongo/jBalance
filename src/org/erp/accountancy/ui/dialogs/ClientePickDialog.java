package org.erp.accountancy.ui.dialogs;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.erp.accountancy.controller.ClienteController;
import org.erp.accountancy.hibernate.entities.Cliente;
import org.erp.accountancy.ui.PanelManager;
import org.erp.accountancy.ui.tables.ClienteSelectTablePanel;
import org.erp.accountancy.utils.Constants;

public class ClientePickDialog extends JDialog {

	private static final long serialVersionUID = 8301022829263191985L;
	private long id = -1;
	
	public ClientePickDialog(){
		super(PanelManager.getInstance().getMainFrame(), "Seleccione un cliente");
		this.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
		
		this.addTable();

		this.setSize(new Dimension(Constants.MAIN_FRAME_WIDTH, Constants.MAIN_FRAME_HEIGTH));
		this.pack();
		this.setVisible(true);
	}
	
	private void addTable(){
		ClienteSelectTablePanel panel = new ClienteSelectTablePanel();
		panel.refresh();
		this.add(panel);
		
		ClientePickDialog instance = this;
		JTable table = panel.getTable();
		table.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				long ide;
				if (table.getRowSorter() != null)
					ide = (long)(table.getModel().getValueAt(
							table.getRowSorter().convertRowIndexToModel(table.getSelectedRow()), 0));
				else 
					ide = (long)(table.getModel().getValueAt(table.getSelectedRow(), 0));
				Cliente cliente = ClienteController.getCliente(ide);//(Cliente)panel.getData().get(table.getSelectedRow());
				id = cliente.getIdCliente();
				JOptionPane.showMessageDialog(instance, "El cliente " + cliente.getNombre() + " ha sido seleccionado.");
				instance.dispose();
			}
		});
	}
	
	public long getId(){
		return this.id;
	}
	
	public static Cliente getCliente() {
		ClientePickDialog dialog = new ClientePickDialog();
		Long id = dialog.getId();
		
		if (id == -1){
			return null;
		}
		
		Cliente cliente = ClienteController.getCliente(id);
		
		if (cliente == null){
			JOptionPane.showMessageDialog(PanelManager.getInstance().getMainFrame(), "El cliente ingresado no existe");
		}
		
		return cliente;
	}

}
