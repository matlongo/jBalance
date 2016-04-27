/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.erp.accountancy.ui.forms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import org.erp.accountancy.configuration.ConfigurationManager;
import org.erp.accountancy.controller.ClienteController;
import org.erp.accountancy.controller.TaxController;
import org.erp.accountancy.hibernate.entities.Cliente;
import org.erp.accountancy.hibernate.entities.Impuesto;
import org.erp.accountancy.ui.PanelManager;
import org.erp.accountancy.ui.dialogs.OtherFeaturesDialog;

/**
 *
 * @author mathias
 */
public class ClienteForm extends javax.swing.JPanel implements ActionListener {

	private static final long serialVersionUID = 6304979698999121428L;

	/** Swing variables */
	private javax.swing.JButton aceptarButton, cancelarButton, otrasPropButton;
	private javax.swing.JTextField comisionText, cuitText, direccionText;
	private javax.swing.JTextField localidadText, nombreText, telText, legajoText;
	private javax.swing.JLabel jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6, jLabel7, jLabel8, jLabel9;
	private javax.swing.JPanel jPanel3, jPanel4;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JSeparator jSeparator1, jSeparator2;
	private javax.swing.Box boxImpuestos;

	/**
	 * Variable used to list all the taxes to the user, each of them are
	 * associated with a jCheckBox
	 */
	private Map<JCheckBox, Impuesto> impuestos;
	/**
	 * Client that is to be modified. If it is null is beacause we are going to
	 * add a new client.
	 */
	private Cliente cliente;
	/**
	 * Hash used to add new features, if the user wishes.
	 */
	private Map<String, String> otherFeatures;

	/**
	 * Constructor used to save a NEW client.
	 */
	public ClienteForm() {
		initImpuestos();
		initComponents();
		cliente = null;
		legajoText.setEnabled(true);
		legajoText.setText(Long.toString(0));
		// set the default honorarios.
		this.comisionText.setText(
				ConfigurationManager.getInstance().getProperty(ConfigurationManager.HONORARIOS_KEY).toString());
	}

	/**
	 * Constructor used to modify an existing client.
	 */
	public ClienteForm(Cliente cliente) {
		initImpuestos();
		initComponents();
		this.cliente = cliente;
		this.fillForm();
	}

	/**
	 * Private method that carries out the initialization of taxes and jCheckBox
	 * map.
	 */
	private void initImpuestos() {
		List<Impuesto> impuestos = TaxController.findAll();
		Box box = Box.createVerticalBox();
		this.impuestos = new HashMap<>();

		for (Impuesto impuesto : impuestos) {
			JCheckBox checkBox = new JCheckBox(impuesto.getName());
			box.add(checkBox);
			this.impuestos.put(checkBox, impuesto);
		}

		boxImpuestos = box;
	}

	/**
	 * Method used to return to the previous panel.
	 */
	private void volver() {
		PanelManager.getInstance().switchPanel(PanelManager.CLIENTES_PANEL);
		PanelManager.getInstance().removePanel(this);
	}

	/**
	 * Method that parses all the information, loaded in the form, in order to
	 * create or update a client.
	 * 
	 * In cases there is a mistake in the process of loading information, it
	 * will raise a pop-up window specifying the mistakes and will return NULL.
	 * 
	 * @return Client to be saved
	 */
	private Cliente getCliente() {
		Cliente cliente = this.cliente;
		if (cliente == null) {
			cliente = new Cliente();
			cliente.setIdCliente(0);
		}

		try {
			Float honorarios = Float.parseFloat(comisionText.getText());
			cliente.setHonorarios(honorarios);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(PanelManager.getInstance().getMainFrame(),
					"El monto de la comisión ingresado es incorrecto.");
			return null;
		}

		if (legajoText.isEnabled()) {
			long id = 0;
			try {
				id = Long.parseLong(legajoText.getText());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(PanelManager.getInstance().getMainFrame(),
						"El número de legajo ingresado no es un número válido.");
				return null;
			}

			if (cliente.getIdCliente() != 0 && id != cliente.getIdCliente()
					&& ClienteController.getCliente(id) != null) {
				JOptionPane.showMessageDialog(PanelManager.getInstance().getMainFrame(),
						"El número de legajo ingresado ya se encuentra en uso.");
				return null;
			}
		}

		if (nombreText.getText().isEmpty()) {
			JOptionPane.showMessageDialog(PanelManager.getInstance().getMainFrame(), "Debe ingresar un nombre.");
			return null;
		}

		Collection<Impuesto> impuestos = new ArrayList<>();
		for (JCheckBox check : this.impuestos.keySet()) {
			if (check.isSelected()) {
				Impuesto selImpuesto = this.impuestos.get(check);
				if (!impuestos.contains(selImpuesto))
					impuestos.add(selImpuesto);
			}
		}

		if (otherFeatures != null) {
			cliente.setOtherFeatures(otherFeatures);
		}

		cliente.setIdCliente(Long.parseLong(legajoText.getText()));
		cliente.setImpuestos(impuestos);
		cliente.setNombre(nombreText.getText());
		cliente.setTelefono(telText.getText());
		cliente.setDireccion(direccionText.getText());
		cliente.setCuit(cuitText.getText());
		cliente.setCiudad(localidadText.getText());
		if (cliente.getMovimientos() == null || cliente.getMovimientos().size() == 0)
			cliente.setMovimientos(new HashSet<>());

		return cliente;
	}

	/**
	 * In case a client is provided to be modified, this method will fill all
	 * the form's fields so that the user can see the previously loaded
	 * information of that particular client.
	 */
	private void fillForm() {
		telText.setText(cliente.getTelefono());
		direccionText.setText(cliente.getDireccion());
		cuitText.setText(cliente.getCuit());
		localidadText.setText(cliente.getCiudad());
		nombreText.setText(cliente.getNombre());
		comisionText.setText(Float.toString(cliente.getHonorarios()));
		for (JCheckBox check : this.impuestos.keySet()) {
			if (cliente.getImpuestos().contains(this.impuestos.get(check)))
				check.setSelected(true);
		}
		otherFeatures = cliente.getOtherFeatures();
		legajoText.setText(Long.toString(cliente.getIdCliente()));
	}

	/**
	 * This method carries out the act of saving a client.
	 */
	private void addClient() {
		Cliente aux = this.getCliente();
		if (aux != null) {
			cliente = aux;
			ClienteController.saveOrUpdateClient(cliente);
			JOptionPane.showMessageDialog(PanelManager.getInstance().getMainFrame(), "Cliente cargado exitosamente");
			this.volver();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == aceptarButton) {
			this.addClient();
		} else if (e.getSource() == cancelarButton) {
			this.volver();
		}
	}

	/**
	 * This method initialize the swing components.
	 */
	private void initComponents() {

		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		jLabel6 = new javax.swing.JLabel();
		jLabel7 = new javax.swing.JLabel();
		jLabel8 = new javax.swing.JLabel();
		jLabel9 = new javax.swing.JLabel();
		javax.swing.JLabel jLabel10 = new javax.swing.JLabel();
		jSeparator1 = new javax.swing.JSeparator();
		jPanel3 = new javax.swing.JPanel();
		cuitText = new javax.swing.JTextField();
		telText = new javax.swing.JTextField();
		comisionText = new javax.swing.JTextField();
		nombreText = new javax.swing.JTextField();
		localidadText = new javax.swing.JTextField();
		direccionText = new javax.swing.JTextField();
		legajoText = new javax.swing.JTextField();
		jPanel4 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		cancelarButton = new javax.swing.JButton();
		aceptarButton = new javax.swing.JButton();
		jSeparator2 = new javax.swing.JSeparator();
		otrasPropButton = new javax.swing.JButton();
		otrasPropButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				OtherFeaturesDialog dialog = new OtherFeaturesDialog(otherFeatures);
				otherFeatures = dialog.getFeatures();
			}
		});

		setMinimumSize(new java.awt.Dimension(800, 600));
		setPreferredSize(new java.awt.Dimension(800, 600));

		legajoText.setEnabled(false);

		jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
		jLabel1.setText("Ingrese los datos del cliente");

		jLabel6.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
		jLabel6.setText("Telefono");

		jLabel7.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
		jLabel7.setText("Comisión");

		jLabel2.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
		jLabel2.setText("Nombre y Apellido:");

		jLabel3.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
		jLabel3.setText("Localidad");

		jLabel4.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
		jLabel4.setText("Dirección");

		jLabel5.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
		jLabel5.setText("CUIT");

		jLabel9.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
		jLabel9.setText("Legajo");

		jLabel10.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
		jLabel10.setText("(Para asignación automática, deje '0')");

		this.aceptarButton.addActionListener(this);
		this.cancelarButton.addActionListener(this);

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
						.addContainerGap(92, Short.MAX_VALUE)
						.addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addGroup(jPanel3Layout.createSequentialGroup().addComponent(jLabel9).addGap(18, 18, 18)
										.addComponent(legajoText, javax.swing.GroupLayout.PREFERRED_SIZE, 66,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 450,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGroup(javax.swing.GroupLayout.Alignment.LEADING, // .TRAILING,
										jPanel3Layout.createSequentialGroup()
												.addGroup(jPanel3Layout.createParallelGroup(
														javax.swing.GroupLayout.Alignment.LEADING/* .TRAILING */, false)
												.addGroup(jPanel3Layout.createSequentialGroup().addComponent(jLabel3)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(localidadText,
																javax.swing.GroupLayout.PREFERRED_SIZE, 224,
																javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGroup(jPanel3Layout.createSequentialGroup().addComponent(jLabel2)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(nombreText,
																javax.swing.GroupLayout.PREFERRED_SIZE, 224,
																javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGroup(jPanel3Layout.createSequentialGroup().addComponent(jLabel4)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(direccionText,
																javax.swing.GroupLayout.PREFERRED_SIZE, 224,
																javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGroup(jPanel3Layout.createSequentialGroup().addComponent(jLabel5)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(cuitText, javax.swing.GroupLayout.PREFERRED_SIZE,
																224, javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGroup(jPanel3Layout.createSequentialGroup().addComponent(jLabel6)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(telText, javax.swing.GroupLayout.PREFERRED_SIZE,
																224, javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGroup(jPanel3Layout.createSequentialGroup().addComponent(jLabel7)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED, 133,
																Short.MAX_VALUE)
														.addComponent(comisionText,
																javax.swing.GroupLayout.PREFERRED_SIZE, 224,
																javax.swing.GroupLayout.PREFERRED_SIZE)))
												.addGap(57, 57, 57)))));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel3Layout.createSequentialGroup()
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel9)
								.addComponent(legajoText, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel2).addComponent(nombreText, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel3).addComponent(localidadText,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel4).addComponent(direccionText,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel5).addComponent(cuitText, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel6).addComponent(telText, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel7).addComponent(comisionText,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))));

		jScrollPane1.setViewportView(boxImpuestos);

		jLabel8.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
		jLabel8.setText("Impuestos a pagar");

		javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
		jPanel4.setLayout(jPanel4Layout);
		jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						jPanel4Layout.createSequentialGroup().addGap(78, 78, 78)
								.addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
										.addComponent(jScrollPane1, 300, 300, 300).addGroup(jPanel4Layout
												.createSequentialGroup().addComponent(jLabel8).addGap(112, 112, 112)))
						.addGap(80, 80, 80)));
		jPanel4Layout
				.setVerticalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jLabel8))
								.addGap(18, 18, 18).addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE,
										150, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(21, 21, 21)));

		cancelarButton.setFont(new java.awt.Font("Ubuntu", 0, 15)); // NOI18N
		cancelarButton.setText("Cancelar");

		aceptarButton.setFont(new java.awt.Font("Ubuntu", 0, 15)); // NOI18N
		aceptarButton.setText("Aceptar");

		otrasPropButton.setFont(new java.awt.Font("Ubuntu", 0, 15)); // NOI18N
		String buttonText = "Agregar otras\npropiedades";
		otrasPropButton.setText("<html>" + buttonText.replaceAll("\\n", "<br>") + "</html>");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(80, 80, 80))
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jSeparator1)
								.addGroup(layout.createSequentialGroup().addGap(229, 229, 229)
										.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 318,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(0, 0, Short.MAX_VALUE))
						.addGroup(layout.createSequentialGroup().addContainerGap()
								.addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(cancelarButton, javax.swing.GroupLayout.Alignment.TRAILING,
												javax.swing.GroupLayout.PREFERRED_SIZE, 160,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(aceptarButton, javax.swing.GroupLayout.Alignment.TRAILING,
												javax.swing.GroupLayout.PREFERRED_SIZE, 160,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(otrasPropButton, javax.swing.GroupLayout.Alignment.TRAILING,
												javax.swing.GroupLayout.PREFERRED_SIZE, 160,
												javax.swing.GroupLayout.PREFERRED_SIZE))))

						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(6, 6, 6).addComponent(jLabel1)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
						javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
				.addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
				.addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
						javax.swing.GroupLayout.PREFERRED_SIZE)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addGap(97, 97, 97)
								.addComponent(otrasPropButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42,
										javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(cancelarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(aceptarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42,
								javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGroup(layout.createSequentialGroup()
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 204,
										javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addGap(11, 11, 11)));
	}
}
