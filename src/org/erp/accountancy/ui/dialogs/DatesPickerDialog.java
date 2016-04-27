package org.erp.accountancy.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DatesPickerDialog extends JDialog {

	private static final long serialVersionUID = 8865514634573490238L;
	private Date from, to;
	
	public DatesPickerDialog(JFrame parent, String title) {
		super(parent, title);
		this.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
		
		this.addComponents();
		
		this.setSize(300, 300);
		this.pack();
		this.setVisible(true);
	}
	
	private void addComponents() {
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		GridLayout grid = new GridLayout(1, 3, 5, 5); 
		mainPanel.setLayout(grid);

		DatePickerPanel panelFrom = new DatePickerPanel();
		DatePickerPanel panelTo = new DatePickerPanel();
		mainPanel.add(this.getDatesPanel(panelFrom, panelTo));
		
		//Ready Button
		JPanel buttonPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(centerPanel, BoxLayout.Y_AXIS);
		centerPanel.setLayout(boxLayout);
		JButton button = new JButton("Listo");
		button.setMaximumSize(new Dimension(100, 40));
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				from = (Date) panelFrom.getDatePickerImpl().getModel().getValue();
				to = (Date) panelTo.getDatePickerImpl().getModel().getValue();
				setVisible(false);
				dispose();
			}
		});
		centerPanel.add(button);
		buttonPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel);
		this.add(mainPanel);
	}
	
	private JPanel getDatesPanel(DatePickerPanel panelFrom, DatePickerPanel panelTo){
		JPanel panel = new JPanel(new GridLayout(4, 3, 5, 5));
		panel.add(new JLabel("Desde:"));
		panel.add(panelFrom.getDatePickerImpl());
		panel.add(new JLabel("Hasta:"));
		panel.add(panelTo.getDatePickerImpl());
		
		return panel;
	}

	public Date getFromDate(){
		return from;
	}
	
	public Date getToDate(){
		return to;
	}
}
