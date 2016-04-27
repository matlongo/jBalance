package org.erp.accountancy.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.erp.accountancy.configuration.ConfigurationManager;
import org.erp.accountancy.hibernate.controller.Controller;
import org.erp.accountancy.utils.Constants;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 7959951547944857122L;

	public MainFrame(){
		super(Constants.APP_NAME);
		
		this.setSize(Constants.MAIN_FRAME_HEIGTH, Constants.MAIN_FRAME_WIDTH);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        Controller.getInstance().close();
		        ConfigurationManager.getInstance().storeFile();
		    }
		});
	}
	
	public void setPanel(JPanel panel){
		this.add(panel, BorderLayout.CENTER);
	}
	
}
