package org.erp.accountancy.ui.dialogs;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.erp.accountancy.controller.MovementController;
import org.erp.accountancy.hibernate.entities.Movimiento;
import org.erp.accountancy.ui.PanelManager;
import org.erp.accountancy.ui.tables.MovementSelectTablePanel;
import org.erp.accountancy.ui.tables.SummaryRow;
import org.erp.accountancy.utils.Constants;

public class MovementPickDialog extends JDialog {

	private static final long serialVersionUID = 8301022829263191985L;
	private long id = -1;
	
	public MovementPickDialog(){
		super(PanelManager.getInstance().getMainFrame(), "Seleccione un cliente");
		this.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
		
		this.addTable();

		this.setSize(new Dimension(Constants.MAIN_FRAME_WIDTH, Constants.MAIN_FRAME_HEIGTH));
		this.pack();
		this.setVisible(true);
	}
	
	private void addTable(){
		MovementSelectTablePanel panel = new MovementSelectTablePanel();
		panel.refresh();
		this.add(panel);
		
		MovementPickDialog instance = this;
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
				SummaryRow movimiento;
				if (table.getRowSorter() != null)
					movimiento = (SummaryRow)(panel.getData().get(
							table.getRowSorter().convertRowIndexToModel(table.getSelectedRow())));
				else 
					movimiento = (SummaryRow)(panel.getData().get(table.getSelectedRow()));
				id = movimiento.getMovimiento().getId();
				JOptionPane.showMessageDialog(instance, "El movimiento " + id + " ha sido seleccionado.");
				instance.dispose();
			}
		});
	}
	
	public long getId(){
		return this.id;
	}
	
	public static Movimiento getMovimiento() {
		MovementPickDialog dialog = new MovementPickDialog();
		Long id = dialog.getId();
		
		if (id == -1){
			return null;
		}
		
		Movimiento movimiento = MovementController.getMovement(id);
		
		if (movimiento == null){
			JOptionPane.showMessageDialog(PanelManager.getInstance().getMainFrame(), "El movimiento ingresado no existe");
		}
		
		return movimiento;
	}
}
