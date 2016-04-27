package org.erp.accountancy.ui.dialogs;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.erp.accountancy.ui.PanelManager;

public class HonorariosDialog extends JDialog implements ActionListener {

    
	private static final long serialVersionUID = 1046400754166043108L;
    
	private javax.swing.JButton aceptarButton;
	private javax.swing.JButton cancelarButton;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JTextField montoText;
	private javax.swing.JComboBox<String> tipoCombo;
	private Float monto;
	private String tipo;
		
	
	public HonorariosDialog() {
		super(PanelManager.getInstance().getMainFrame(), "Seleccione un monto");
		this.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);

        JPanel panel = getPanel();
        this.aceptarButton.addActionListener(this);
        this.cancelarButton.addActionListener(this);
        
        this.add(panel);
        this.setSize(new Dimension(411, 156));
		this.setVisible(true);
    }
                     
    private JPanel getPanel() {
    	JPanel panel = new JPanel();
    	
        tipoCombo = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        montoText = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cancelarButton = new javax.swing.JButton();
        aceptarButton = new javax.swing.JButton();

        tipoCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Aumentar en un monto", "Aumentar en un porcentaje", "Establecer un monto general" }));

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel1.setText("Seleccione el tipo de modificación:");

        jLabel2.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel2.setText("Ingrese el monto:");

        cancelarButton.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        cancelarButton.setText("Cancelar");

        aceptarButton.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        aceptarButton.setText("Aceptar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel);
        panel.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tipoCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(montoText, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancelarButton)
                .addGap(18, 18, 18)
                .addComponent(aceptarButton)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tipoCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(montoText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelarButton)
                    .addComponent(aceptarButton))
                .addContainerGap())
        );
        
        return panel;
    }

	
    public Float getMonto(){
    	return monto;
    }
    
    public String getTipo(){
    	return tipo;
    }
    
    @Override
	public void actionPerformed(ActionEvent e) {
    	if (e.getSource() == aceptarButton) {
    		try {
    			monto = Float.parseFloat(montoText.getText());
        		tipo = (String) tipoCombo.getSelectedItem();
            	this.dispose();
    		} catch (Exception ex) {
    			monto = null;
    			tipo = null;
    			JOptionPane.showMessageDialog(PanelManager.getInstance().getMainFrame(), "El monto ingresado es incorrecto");
    		}
    	}
    	else if (e.getSource() == cancelarButton){
    		monto = null;
    		tipo = null;
    		this.dispose();
    	}
	}       
}
