package org.erp.accountancy.ui.panels;

import java.awt.event.ActionEvent;
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
	
	private static String[] getButtonsText(){
		String[] buttonsText = {
				"Ingreso de nuevos movimientos", "Modificación de movimientos", 
				"Resumen de movimientos", "Volver"
		};
		return buttonsText;
	}

//	private Movimiento getMovimiento(){
//		String s = (String)JOptionPane.showInputDialog(PanelManager.getInstance().getMainFrame(),
//                "Ingrese el identificador del movimiento a modificar:",
//                "Ingrese movimiento", JOptionPane.PLAIN_MESSAGE, null, null, "");
//		
//		if (s == null  ||  s.isEmpty()) return null;
//		
//		Movimiento movimiento;
//		
//		Session session = Controller.getInstance().getSession();
//		session.beginTransaction();
//		try {
//			long id = Long.parseLong(s);
//			movimiento = (Movimiento) session.load(Movimiento.class, id);
//			Hibernate.initialize(movimiento.getCliente());
//		} catch (Exception e) {
//			movimiento = null;
//			JOptionPane.showMessageDialog(PanelManager.getInstance().getMainFrame(), "El identificador ingresado no corresponde a ningún movimiento.");
//		} finally {
//			session.getTransaction().commit();
//		}
//		return movimiento;
//	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttons[0]){ //Nuevo movimiento
			MovimientoForm form = new MovimientoForm();
			PanelManager.getInstance().setNewPanel(form, "formulario-movimiento");
		}
		else if (e.getSource() == buttons[1]){ //Modificación movimiento
			Movimiento movimiento = MovementPickDialog.getMovimiento();//this.getMovimiento();
			if (movimiento != null) {
				MovimientoForm form = new MovimientoForm(movimiento);
				PanelManager.getInstance().setNewPanel(form, "formulario-movimiento");
			}
			
		}
		else if (e.getSource() == buttons[2]){ //Resumenes
			PanelManager.getInstance().switchPanel(PanelManager.RESUMEN_MOVIMIENTOS_PANEL);
		}
		else if (e.getSource() == buttons[3]){ //Volver
			PanelManager.getInstance().switchPanel(PanelManager.WELCOME_PANEL);
		}
	}

}
