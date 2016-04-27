package org.erp.accountancy.ui.panels;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.erp.accountancy.configuration.ConfigurationManager;
import org.erp.accountancy.controller.BackupRestoreController;
import org.erp.accountancy.hibernate.controller.Controller;
import org.erp.accountancy.ui.PanelManager;
import org.erp.accountancy.utils.Utils;

public class OpcionesPanel extends ButtonsPanel {

	private static final long serialVersionUID = 3659067144121065268L;
	
	private static final String TITLE = "OPCIONES";

	public OpcionesPanel() {
		super(getButtonsText(), TITLE);
	}

	private static String[] getButtonsText(){
		String [] buttonsText = {
				"Cambiar año de análisis", "Realizar back-up", "Restaruación del sistema", "Volver"
		};
		return buttonsText;
	}
	
	private void cambiarAnio(){
		String anio = JOptionPane.showInputDialog("Introduzca el año de análisis que desea ver");
		if (anio != null  &&  !anio.isEmpty()){
			try {
				int anioInt = Integer.parseInt(anio);
				Utils.setCurrentYer(anioInt);
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(PanelManager.getInstance().getMainFrame(), "El año ingresado es inválido.");
			}
		}
	}
	
	private void restore(){
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(PanelManager.getInstance().getMainFrame());

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            if (!file.getName().endsWith(".h2.db")){
            	JOptionPane.showMessageDialog(PanelManager.getInstance().getMainFrame(), 
            			"El archivo ingresado no es una base de datos correcta.");
            	return;
            }
            Controller.getInstance().close();
            try {
				Files.delete(new File(BackupRestoreController.DATABASE_FILE_PATH).toPath());
				System.out.println("hola");
				Files.delete(new File(BackupRestoreController.TRACE_FILE_PATH).toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
            try {
				BackupRestoreController.copyFileUsingFileStreams(file.getAbsolutePath(), 
						BackupRestoreController.DATABASE_FILE_PATH);
			} catch (IOException e) {
            	JOptionPane.showMessageDialog(PanelManager.getInstance().getMainFrame(), 
            			"No se ha podido restaurar el archivo seleccionado.");
			}
            JOptionPane.showMessageDialog(PanelManager.getInstance().getMainFrame(), 
            		"Base de datos cargada con éxito.\n"
            		+ "El programa se cerrará para poder configurar la base de datos.");
            PanelManager.getInstance().getMainFrame().dispose();
        }
	}
	
	private void backUp(){
		JFileChooser fc = new JFileChooser();
		//fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fc.showSaveDialog(PanelManager.getInstance().getMainFrame());
		
		if (returnVal == JFileChooser.APPROVE_OPTION){
            File file = fc.getSelectedFile();
            try {
            	String dest = file.getAbsolutePath();
            	if (!dest.endsWith(BackupRestoreController.EXTENSION))
            		dest += BackupRestoreController.EXTENSION;
				BackupRestoreController.copyFileUsingFileStreams(BackupRestoreController.DATABASE_FILE_PATH,
						dest);
				
				ConfigurationManager.getInstance().updateProperty(ConfigurationManager.MES_KEY, 
						Utils.getCurrentMonth());
				JOptionPane.showMessageDialog(PanelManager.getInstance().getMainFrame(), 
	            		"Base de datos guardada con éxito.");
			} catch (IOException e) {
            	JOptionPane.showMessageDialog(PanelManager.getInstance().getMainFrame(), 
            			"No se ha podido crear la copia de seguridad en la carpeta seleccionada.");
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttons[0]){ //Cambiar año
			this.cambiarAnio();
		} else if (e.getSource() == buttons[1]){ //back-up
			this.backUp();
		} else if (e.getSource() == buttons[2]){ //Restaurar
			this.restore();
		} else if (e.getSource() == buttons[3]){ //Volver
			PanelManager.getInstance().switchPanel(PanelManager.WELCOME_PANEL);
		}
	}

}
