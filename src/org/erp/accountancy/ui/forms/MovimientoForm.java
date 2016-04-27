package org.erp.accountancy.ui.forms;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import org.erp.accountancy.controller.MovementController;
import org.erp.accountancy.hibernate.entities.Cliente;
import org.erp.accountancy.hibernate.entities.Movimiento;
import org.erp.accountancy.ui.PanelManager;
import org.erp.accountancy.ui.dialogs.ClientePickDialog;
import org.erp.accountancy.ui.dialogs.DatePickerPanel;

/**
 *
 * @author mathias
 */
public class MovimientoForm extends javax.swing.JPanel implements ActionListener {

	private static final long serialVersionUID = -1328887076381986586L;

	/**
	 * Panel used to load the movement's date.
	 */
	private DatePickerPanel datePicker;
	/**
	 * Client that made the movement to be saved.
	 */
	private Cliente cliente;
	/**
	 * Movement to be modified. In case we are going to save a new movement,
	 * this variable will be null.
	 */
	private Movimiento movimiento;

	/** Swing components that contains useful information */
	// Buttons
	private javax.swing.JButton aceptarButton, cancelButton, cargarClienteButton;
	// Unchangeable fields
	private javax.swing.JLabel legajoText, nombreText, ciudadText, direccionText;
	// TextFields
	private javax.swing.JTextField montoText, comprobanteText, conceptoText;
	// RadioButtons
	private javax.swing.JRadioButton radioButtonDebe, radioButtonHaber;

	/**
	 * Constructor used to create a new movement and save it into the database.
	 */
	public MovimientoForm() {
		initComponents();
		radioButtonDebe.setSelected(true);
		movimiento = null;
	}

	/**
	 * Constructor used to modify an existing movement and save it into the
	 * database.
	 * 
	 * @param mov
	 *            Movement to be modified
	 */
	public MovimientoForm(Movimiento mov) {
		initComponents();
		this.movimiento = mov;
		this.cliente = mov.getCliente();

		this.fillForm();
	}

	/**
	 * This method will carry out the filling of the form. It will fill all the
	 * fields concerning to the movement's information.
	 */
	private void fillForm() {
		this.fillCliente();

		comprobanteText.setText(Long.toString(movimiento.getComprobante()));
		montoText.setText(Double.toString(movimiento.getMontoDebe() + movimiento.getMontoHaber()));
		conceptoText.setText(movimiento.getDescripcion());
		datePicker.getModel().setValue(movimiento.getFecha());
		if (movimiento.getMontoDebe() > 0.0)
			radioButtonDebe.setSelected(true);
		else
			radioButtonHaber.setSelected(true);
	}

	/**
	 * This method will carry out the filling of the form. It will fill all the
	 * fields concerning to the client's information.
	 */
	private void fillCliente() {
		legajoText.setText(Long.toString(cliente.getIdCliente()));
		nombreText.setText(cliente.getNombre());
		ciudadText.setText(cliente.getCiudad());
		direccionText.setText(cliente.getDireccion());
	}

	/**
	 * Method that parses all the information, loaded in the form, in order to
	 * create or update a movement.
	 * 
	 * In cases there is a mistake in the process of loading information, it
	 * will raise a pop-up window specifying the mistakes and will return NULL.
	 * 
	 * @return Client to be saved
	 */
	private Movimiento processMovimiento() {
		Movimiento movimiento = this.movimiento;
		if (movimiento == null)
			movimiento = new Movimiento();
		StringBuilder textToShow = new StringBuilder();

		if (cliente == null)
			textToShow.append("No se ha elegido el cliente al cual asignarle el movimiento.\n");

		if (comprobanteText.getText().length() <= 0)
			textToShow.append("No se ha ingresado el número de comprobante correspondiente.\n");
		else {
			try {
				long comp = Long.parseLong(comprobanteText.getText());
				movimiento.setComprobante(comp);
			} catch (Exception e) {
				textToShow.append(
						"El número de comprobante ingresado no es correcto, solo se permite ingresar números del 0 al 9.\n");
			}
		}

		if (montoText.getText().length() <= 0)
			textToShow.append("No se ha ingresado el monto correspondiente.\n");
		else {
			try {
				double monto = Double.parseDouble(montoText.getText());
				if (radioButtonDebe.isSelected()) {
					movimiento.setMontoDebe(monto);
					movimiento.setMontoHaber(0.00);
				} else {
					movimiento.setMontoHaber(monto);
					movimiento.setMontoDebe(0.00);
				}
			} catch (Exception e) {
				textToShow.append(
						"El monto ingresado no es correcto, solo se permite ingresar números del 0 al 9 y el punto.\n");
			}
		}

		movimiento.setDescripcion(conceptoText.getText());
		movimiento.setCliente(cliente);
		movimiento.setFecha(datePicker.getModel().getValue());

		if (textToShow.length() > 0) {
			JOptionPane.showMessageDialog(PanelManager.getInstance().getMainFrame(), textToShow.toString());
			return null;
		}
		cliente.getMovimientos().add(movimiento);
		return movimiento;
	}

	/**
	 * Method used to return to the previous panel.
	 */
	public void volver() {
		PanelManager.getInstance().switchPanel(PanelManager.MOVIMIENTOS_PANEL);
		PanelManager.getInstance().removePanel(this);
	}
	
	/**
	 * This method carries out the act of saving a movement.
	 */
	private void saveMovement(){
		Movimiento movimiento = this.processMovimiento();
		if (movimiento != null) {
			this.movimiento = movimiento;

			JOptionPane.showMessageDialog(PanelManager.getInstance().getMainFrame(),
					"Movimiento cargado exitosamente");
			MovementController.saveMovement(this.movimiento);
			volver();
		}
	}

	/**
	 * This method initialize the swing components.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cancelButton) {
			volver();
		} else if (e.getSource() == aceptarButton) {
			this.saveMovement();
		} else if (e.getSource() == radioButtonDebe) {
			radioButtonHaber.setSelected(false);
		} else if (e.getSource() == radioButtonHaber) {
			radioButtonDebe.setSelected(false);
		} else if (e.getSource() == cargarClienteButton) {
			cliente = ClientePickDialog.getCliente();
			if (cliente != null) {
				this.fillCliente();
			}
		}
	}

	private void initComponents() {

		javax.swing.JLabel jLabel1, jLabel10, jLabel11, jLabel12, jLabel13, jLabel14, jLabel8, jLabel9;
		javax.swing.JLabel jLabel15, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6, jLabel7;
		javax.swing.JPanel jPanel1, jPanel2;
		javax.swing.JSeparator jSeparator1, jSeparator2, jSeparator3, jSeparator4;

		jLabel1 = new javax.swing.JLabel();
		jSeparator1 = new javax.swing.JSeparator();
		jLabel2 = new javax.swing.JLabel();
		jSeparator2 = new javax.swing.JSeparator();
		jPanel1 = new javax.swing.JPanel();
		jLabel4 = new javax.swing.JLabel();
		legajoText = new javax.swing.JLabel();
		cargarClienteButton = new javax.swing.JButton();
		jLabel3 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		jLabel6 = new javax.swing.JLabel();
		jLabel7 = new javax.swing.JLabel();
		jLabel8 = new javax.swing.JLabel();
		jLabel9 = new javax.swing.JLabel();
		nombreText = new javax.swing.JLabel();
		direccionText = new javax.swing.JLabel();
		ciudadText = new javax.swing.JLabel();
		jSeparator3 = new javax.swing.JSeparator();
		jLabel10 = new javax.swing.JLabel();
		jSeparator4 = new javax.swing.JSeparator();
		jPanel2 = new javax.swing.JPanel();
		jLabel11 = new javax.swing.JLabel();
		jLabel12 = new javax.swing.JLabel();
		jLabel13 = new javax.swing.JLabel();
		jLabel14 = new javax.swing.JLabel();
		comprobanteText = new javax.swing.JTextField();
		montoText = new javax.swing.JTextField();
		conceptoText = new javax.swing.JTextField();
		jLabel15 = new javax.swing.JLabel();
		radioButtonDebe = new javax.swing.JRadioButton();
		radioButtonHaber = new javax.swing.JRadioButton();
		cancelButton = new javax.swing.JButton();
		aceptarButton = new javax.swing.JButton();
		datePicker = new DatePickerPanel();

		cancelButton.addActionListener(this);
		aceptarButton.addActionListener(this);
		radioButtonDebe.addActionListener(this);
		radioButtonHaber.addActionListener(this);
		cargarClienteButton.addActionListener(this);

		setMinimumSize(new java.awt.Dimension(800, 600));
		setPreferredSize(new java.awt.Dimension(800, 600));

		jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
		jLabel1.setText("Ingrese los datos del movimiento");

		jLabel2.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
		jLabel2.setText("Datos del cliente");

		jLabel4.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
		jLabel4.setText("Legajo N°:");

		cargarClienteButton.setPreferredSize(new Dimension(600, 50));
		cargarClienteButton.setText("Seleccionar cliente");

		jLabel3.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
		jLabel3.setText("Nombre:");

		jLabel5.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
		jLabel5.setText("Ciudad:");

		jLabel6.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
		jLabel6.setText("Dirección:");

		jLabel7.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N

		jLabel8.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N

		jLabel9.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N

		nombreText.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
		nombreText.setPreferredSize(new java.awt.Dimension(200, 21));
		legajoText.setFont(new java.awt.Font("Ubuntu", 1, 18));

		direccionText.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
		direccionText.setPreferredSize(new java.awt.Dimension(200, 21));

		ciudadText.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
		ciudadText.setPreferredSize(new java.awt.Dimension(200, 21));

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jPanel1Layout.createSequentialGroup().addComponent(jLabel4).addGap(18, 18, 18)
										.addComponent(legajoText, javax.swing.GroupLayout.PREFERRED_SIZE, 145,
												javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(41, 41, 41)
								.addComponent(cargarClienteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
						.addGroup(jPanel1Layout.createSequentialGroup()
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jLabel3).addComponent(jLabel5).addComponent(jLabel6))
								.addGap(27, 27, 27)
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(nombreText, javax.swing.GroupLayout.PREFERRED_SIZE, 240,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(ciudadText, javax.swing.GroupLayout.PREFERRED_SIZE, 240,
														javax.swing.GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addGroup(jPanel1Layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
																false)
														.addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE,
																261, Short.MAX_VALUE)
														.addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
										.addComponent(direccionText, javax.swing.GroupLayout.PREFERRED_SIZE, 240,
												javax.swing.GroupLayout.PREFERRED_SIZE))))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel4)
								.addComponent(legajoText, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(cargarClienteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
								javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel3).addComponent(jLabel7).addComponent(nombreText,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jLabel5).addComponent(jLabel8))
								.addComponent(ciudadText, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jLabel6).addComponent(jLabel9))
								.addComponent(direccionText, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jLabel10.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
		jLabel10.setText("Datos del movimiento");

		jLabel11.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
		jLabel11.setText("Comprobante:");

		jLabel12.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
		jLabel12.setText("Monto:");

		jLabel13.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
		jLabel13.setText("Fecha:");

		jLabel14.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
		jLabel14.setText("Concepto:");

		jLabel15.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
		jLabel15.setText("Tipo:");

		radioButtonDebe.setText("Debe");

		radioButtonHaber.setText("Haber");

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout
				.setHorizontalGroup(
						jPanel2Layout
								.createParallelGroup(
										javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(
										jPanel2Layout.createSequentialGroup().addContainerGap()
												.addGroup(
														jPanel2Layout
																.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.LEADING)
																.addComponent(jLabel11).addComponent(jLabel12)
																.addComponent(jLabel13).addComponent(jLabel14))
												.addGap(18, 18,
														18)
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(datePicker.getDatePickerImpl(), javax.swing.GroupLayout.PREFERRED_SIZE,
										150, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(conceptoText, javax.swing.GroupLayout.PREFERRED_SIZE, 277,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGroup(jPanel2Layout.createSequentialGroup().addGroup(jPanel2Layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(jPanel2Layout.createSequentialGroup()
												.addComponent(comprobanteText, javax.swing.GroupLayout.PREFERRED_SIZE,
														150, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGap(39, 39, 39).addComponent(jLabel15))
										.addComponent(montoText, javax.swing.GroupLayout.PREFERRED_SIZE, 150,
												javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(jPanel2Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(radioButtonHaber).addComponent(radioButtonDebe))))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(jPanel2Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanel2Layout.createSequentialGroup().addGroup(jPanel2Layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel11)
								.addComponent(comprobanteText, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel15))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jLabel12).addComponent(montoText,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addGroup(jPanel2Layout.createSequentialGroup().addComponent(radioButtonHaber)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(radioButtonDebe)))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel13).addComponent(datePicker.getDatePickerImpl(),
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel14).addComponent(conceptoText,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(31, Short.MAX_VALUE)));

		cancelButton.setFont(new java.awt.Font("Ubuntu", 0, 15)); // NOI18N
		cancelButton.setText("Cancelar");

		aceptarButton.setFont(new java.awt.Font("Ubuntu", 0, 15)); // NOI18N
		aceptarButton.setText("Aceptar");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jSeparator1)
				.addGroup(layout.createSequentialGroup().addGroup(layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addContainerGap()
								.addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 146,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
						.addComponent(jSeparator3)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup().addGap(219, 219, 219).addComponent(
												jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 404,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jLabel2))
								.addGroup(layout.createSequentialGroup().addContainerGap()
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jLabel10).addComponent(jSeparator4,
														javax.swing.GroupLayout.PREFERRED_SIZE, 187,
														javax.swing.GroupLayout.PREFERRED_SIZE))))
								.addGap(0, 0, Short.MAX_VALUE))
						.addGroup(layout.createSequentialGroup().addGap(137, 137, 137)
								.addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23,
										Short.MAX_VALUE)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(cancelButton, javax.swing.GroupLayout.Alignment.TRAILING,
												javax.swing.GroupLayout.PREFERRED_SIZE, 160,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(aceptarButton, javax.swing.GroupLayout.Alignment.TRAILING,
												javax.swing.GroupLayout.PREFERRED_SIZE, 160,
												javax.swing.GroupLayout.PREFERRED_SIZE))))
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jLabel1)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
						javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jLabel2)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addGap(13, 13, 13)
				.addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
						javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addComponent(jLabel10)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
										javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(48, 48, 48))
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
								layout.createSequentialGroup()
										.addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(aceptarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap()))));
	}
}