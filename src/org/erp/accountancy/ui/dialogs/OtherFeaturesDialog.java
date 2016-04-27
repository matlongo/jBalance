package org.erp.accountancy.ui.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.erp.accountancy.ui.PanelManager;

public class OtherFeaturesDialog extends JDialog {
	
	private static final long serialVersionUID = 7913532189136991153L;

	private javax.swing.JButton deleteButton;
	private javax.swing.JButton cancelButton;
    private javax.swing.JTable featuresTable;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton okButton;
    private Map<String, String> features;
    
    
    public OtherFeaturesDialog(Map<String, String> features) {
		super(PanelManager.getInstance().getMainFrame(), "Seleccione un cliente");
		this.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
		
		this.features = features;
    	this.initComponents();

		this.pack();
		this.setVisible(true);
	}
    
    
    private void initFeatures(){
		this.features = new HashMap<>();
		DefaultTableModel dtm = (DefaultTableModel) featuresTable.getModel();
		int nRows = dtm.getRowCount();
		for (int i = 0; i < nRows; i++){
			if (dtm.getValueAt(i, 0) != null  &&  dtm.getValueAt(i, 0) != ""
					&& dtm.getValueAt(i, 1) != null  &&  dtm.getValueAt(i, 1) != ""){
				features.put((String)dtm.getValueAt(i, 0), (String)dtm.getValueAt(i, 1));
			}
		}
    }
    
    public Object[][] getTableData(JTable table, int from, int to) {
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		int nCol = dtm.getColumnCount();
		Object[][] tableData = new Object[to - from][nCol];
		for (int i = from; i < to; i++)
			for (int j = 0; j < nCol; j++) {
				tableData[i - from][j] = dtm.getValueAt(i, j);
			}
		return tableData;
	}
    
    
    public Map<String, String> getFeatures(){
    	return this.features;
    }
    
    public Object[][] getTableData(){
    	if (features == null)
    		return new Object [][] {{null, null},};
    		
    	Object[][] data = new Object[features.size()+1][2];
    	int pos = 0;
    	for (String key: features.keySet()){
    		data[pos][0] = key;
    		data[pos++][1] = features.get(key);
    	}
    	return data;
    }
    
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        featuresTable = new javax.swing.JTable();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        //new Object [][] {{null, null},}
        DefaultTableModel model = new DefaultTableModel(this.getTableData(),
	        new String [] {"Nombre", "Valor"})
        {
			private static final long serialVersionUID = -8278921108818619926L;

			@Override
        	public void setValueAt(Object aValue, int row, int column) {
        		super.setValueAt(aValue, row, column);
				if (row == getRowCount() - 1  &&  column == 0){
					addRow(new Object[]{null, null});
				}
        	}
        };
        featuresTable.setModel(model);
        featuresTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        jScrollPane1.setViewportView(featuresTable);

        deleteButton.setText("Borrar");
        deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (featuresTable.getSelectedRow() != -1) {
		            // remove selected row from the model
		            model.removeRow(featuresTable.getSelectedRow());
		        }
			}
		});
        
        okButton.setText("Aceptar");
        okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				initFeatures();
				dispose();
			}
		});

        cancelButton.setText("Cancelar");
        cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 27, Short.MAX_VALUE)
                .addComponent(deleteButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(okButton)
                    .addComponent(cancelButton)
                    .addComponent(deleteButton)))
        );
    }                  
}
