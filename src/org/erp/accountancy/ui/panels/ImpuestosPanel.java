package org.erp.accountancy.ui.panels;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JOptionPane;

import org.erp.accountancy.controller.TaxController;
import org.erp.accountancy.hibernate.entities.Impuesto;
import org.erp.accountancy.ui.PanelManager;
import org.erp.accountancy.ui.forms.NewImpuestoForm;

public class ImpuestosPanel extends ButtonsPanel {

	private static final long serialVersionUID = -4505928548374822772L;
	private static final String TITLE = "IMPUESTOS";

	public ImpuestosPanel() {
		super(getButtonsText(), TITLE);
	}

	private static String[] getButtonsText() {
		String[] buttonsText = { "Agregar nuevo impuesto", "Eliminar impuesto", "Volver" };
		return buttonsText;
	}

	private List<Impuesto> getImpuestos() {
		List<Impuesto> impuestos = TaxController.findAll();// session.createCriteria(Impuesto.class).list();

		return impuestos;
	}

	private Impuesto deletedImpuesto() {
		List<Impuesto> impuestos = this.getImpuestos();

		Object[] possibilities = new Object[impuestos.size()];
		int pos = 0;
		for (Impuesto impuesto : impuestos)
			possibilities[pos++] = impuesto.getName();

		String fee = (String) JOptionPane.showInputDialog(this, "Seleccione el mes:", TITLE, JOptionPane.PLAIN_MESSAGE,
				null, possibilities, "ENERO");

		if (fee == null)
			return null;

		for (Impuesto impuesto : impuestos)
			if (impuesto.getName().equals(fee))
				return impuesto;
		return null;
	}

	private void deletImpuesto(Impuesto impuesto) {
		int status = TaxController.removeImpuesto(impuesto);
		if (status != 0) {
			JOptionPane.showMessageDialog(PanelManager.getInstance().getMainFrame(),
					"Error al borrar el impuesto seleccionado, existe al menos un cliente que tiene asociado este impuesto.");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttons[0]) { // new fee
			new NewImpuestoForm();
		} else if (e.getSource() == buttons[1]) { // Eliminar impuesto
			Impuesto impuesto = this.deletedImpuesto();
			if (impuesto != null) {
				this.deletImpuesto(impuesto);
			}
		} else if (e.getSource() == buttons[2]) { // Back
			PanelManager.getInstance().switchPanel(PanelManager.WELCOME_PANEL);
		}
	}

}
