package org.erp.accountancy.ui.panels;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import org.erp.accountancy.controller.MovementController;
import org.erp.accountancy.hibernate.entities.Movimiento;
import org.erp.accountancy.ui.PanelManager;
import org.erp.accountancy.ui.dialogs.MovementPickDialog;
import org.erp.accountancy.ui.forms.MovimientoForm;

public class MovimientosPanel extends ButtonsPanel {

	private static final long serialVersionUID = 1400006818601451121L;
	private static final String TITLE = "RESUMEN DE MOVIMIENTOS";

	public MovimientosPanel() {
		super(getButtonsText(), TITLE);
	}

	private static String[] getButtonsText() {
		String[] buttonsText = { "Ingreso de nuevos movimientos", "Modificación de movimientos", "Eliminar movimiento",
				"Resumen de movimientos", "Volver" };
		return buttonsText;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttons[0]) { // Nuevo movimiento
			MovimientoForm form = new MovimientoForm();
			PanelManager.getInstance().setNewPanel(form, "formulario-movimiento");
		} else if (e.getSource() == buttons[1]) { // Modificación movimiento
			Movimiento movimiento = MovementPickDialog.getMovimiento();// this.getMovimiento();
			if (movimiento != null) {
				MovimientoForm form = new MovimientoForm(movimiento);
				PanelManager.getInstance().setNewPanel(form, "formulario-movimiento");
			}

		} else if (e.getSource() == buttons[2]) { // Eliminar
			Movimiento movimiento = MovementPickDialog.getMovimiento();// this.getMovimiento();
			if (movimiento != null) {
				MovementController.removeMovement(movimiento);
				JOptionPane.showMessageDialog(PanelManager.getInstance().getMainFrame(),
						"Movimiento eliminado exitosamente.");
			}
		} else if (e.getSource() == buttons[3]) { // Resumenes
			PanelManager.getInstance().switchPanel(PanelManager.RESUMEN_MOVIMIENTOS_PANEL);
		} else if (e.getSource() == buttons[4]) { // Volver
			PanelManager.getInstance().switchPanel(PanelManager.WELCOME_PANEL);
		}
	}

}
