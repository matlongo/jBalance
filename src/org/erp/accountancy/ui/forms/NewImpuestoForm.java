package org.erp.accountancy.ui.forms;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.erp.accountancy.controller.TaxController;
import org.erp.accountancy.hibernate.entities.Impuesto;
import org.erp.accountancy.ui.PanelManager;
import org.erp.accountancy.utils.Constants;

public class NewImpuestoForm extends JDialog implements ActionListener {

	private static final long serialVersionUID = -7245101170523404783L;                       
              
    private javax.swing.JButton aceptarButton;
    private javax.swing.JButton cancelarButton;
    private javax.swing.JTextArea descripcionText;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField nombreText;
    private javax.swing.JTextField porcentajeText;  

    
    public NewImpuestoForm() {
		super(PanelManager.getInstance().getMainFrame(), "Nuevo impuesto");
		this.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
		
		this.add(getMainPanel());

		this.setSize(new Dimension(Constants.MAIN_FRAME_WIDTH, Constants.MAIN_FRAME_HEIGTH));
		this.pack();
		this.setVisible(true);
    }
                        
    private JPanel getMainPanel() {
    	JPanel panel = new JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        nombreText = new javax.swing.JTextField();
        porcentajeText = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        descripcionText = new javax.swing.JTextArea();
        cancelarButton = new javax.swing.JButton();
        aceptarButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel1.setText("Agregar nuevo impuesto");

        jLabel2.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel2.setText("Nombre:");

        jLabel3.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel3.setText("Porcentaje a aplicar:");

        jLabel4.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel4.setText("Descripción:");

        descripcionText.setColumns(20);
        descripcionText.setRows(5);
        jScrollPane1.setViewportView(descripcionText);

        cancelarButton.setText("Cancelar");
        aceptarButton.setText("Aceptar");
        aceptarButton.addActionListener(this);
        cancelarButton.addActionListener(this);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel);
        panel.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cancelarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(aceptarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addGap(18, 18, 18)
                                    .addComponent(porcentajeText))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(18, 18, 18)
                                    .addComponent(nombreText, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(3, 3, 3)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nombreText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(porcentajeText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(aceptarButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cancelarButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        return panel;
    }


	private Impuesto getImpuesto() {
		Impuesto impuesto = new Impuesto();
		String message = "";
		
		String name = nombreText.getText();
		if (name.trim().isEmpty()){
			message += "No se ha ingresado un nombre para el impuesto.\n";
		}
		
		Float porcentaje = null;
		try {
			porcentaje = Float.parseFloat(porcentajeText.getText());
		} catch (Exception e){
			porcentaje = null;
		}
		if (porcentaje == null){
			message += "No se ha ingresado un porcentaje válido.\n";
		}
		
		String descripcion = descripcionText.getText();
		
		if (!message.isEmpty()){
			JOptionPane.showMessageDialog(PanelManager.getInstance().getMainFrame(), message);
			return null;
		}
		
		impuesto.setDescription(descripcion);
		impuesto.setPercentage(Float.toString(porcentaje));
		impuesto.setName(name);
		
		return impuesto;
	}      
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == aceptarButton){
			Impuesto impuesto = this.getImpuesto();
			if (impuesto != null){
				TaxController.saveTax(impuesto);
				this.dispose();
			}
		} else if (e.getSource() == cancelarButton){
			this.dispose();
		}
	} 
}
