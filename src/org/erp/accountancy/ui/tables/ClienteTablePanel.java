package org.erp.accountancy.ui.tables;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import org.erp.accountancy.clients.filters.CityFilter;
import org.erp.accountancy.clients.filters.ClientFilter;
import org.erp.accountancy.clients.filters.IdFilter;
import org.erp.accountancy.clients.filters.NameFilter;
import org.erp.accountancy.clients.filters.TrueFilter;
import org.erp.accountancy.controller.ClienteController;
import org.erp.accountancy.hibernate.entities.Cliente;
import org.erp.accountancy.ui.PanelManager;
import org.erp.accountancy.ui.forms.ClienteShowingForm;

public class ClienteTablePanel extends TablePanel {

	private static final long serialVersionUID = 3597806259564046489L;
	private String parentToSwitch;
	private ClientFilter filter;
    private List<Cliente> data;
    private String title;
	
	public ClienteTablePanel(String parentToSwitch) {
		super();
		this.parentToSwitch = parentToSwitch;
		filter = new TrueFilter();
		title = "LISTADO DE CLIENTES";
		
		this.addTableListener();
	}
	
	public ClienteTablePanel() {
		super();
		filter = new TrueFilter();
		title = "LISTADO DE CLIENTES";
	}
	
	public ClienteTablePanel(ClientFilter filter, String title) {
		super();
		this.filter = filter;
		this.title = title;
	}
	
	private void addTableListener(){
		JTable originalTable = this.getTable();
		originalTable.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable table = getTable();
				long idCliente = (long)(table.getModel().getValueAt(
						table.getRowSorter().convertRowIndexToModel(table.getSelectedRow()), 0));
				Cliente cliente = ClienteController.getCliente(idCliente);
				ClienteShowingForm form = new ClienteShowingForm(cliente);
				form.setVisible(true);
			}
		});
	}
	
	@Override
	public void addComponents() {
		JScrollPane tablePanel = this.initTable();
		JPanel panel = this.getFilteringPanel();
		JPanel buttons = this.initButons();
		
		this.add(tablePanel);
		this.add(panel, BorderLayout.NORTH);
		this.add(buttons, BorderLayout.SOUTH);
	}
	
	@Override
	public void goBack() {
		PanelManager.getInstance().switchPanel(parentToSwitch);
		PanelManager.getInstance().removePanel(this);
	}

	@Override
	public List<TableRow> getData() {
		List<Cliente> clientes = ClienteController.filter(filter);
		data = clientes;

		List<TableRow> rows = new ArrayList<>();
		rows.addAll(clientes);
		
		return rows;
	}

	@Override
	protected String[] getHeader() {
		String [] headers = {"LEG.", "APELLIDO Y NOMBRE", "DIRECCION", "CIUDAD", "CUIT"};//, "TELEFONO"};
		return headers;
	}

	@Override
	public String getTitle() {
		return title;
	}
	
	protected JPanel getFilteringPanel(){
		JPanel panel = new JPanel();
		
		JLabel label = new JLabel("Ingrese el texto y campo para filtrar: ");
		panel.add(label, BorderLayout.CENTER);
		
		JTextArea textArea = new JTextArea();
		textArea.setPreferredSize(new Dimension(150, 20));
		panel.add(textArea, BorderLayout.CENTER);
		
		String [] items = { "Id", "Ciudad", "Nombre" };
		JComboBox<String> combo = new JComboBox<>(items);
		panel.add(combo, BorderLayout.CENTER);
		
		JButton filtrar = new JButton("Filtrar");
		filtrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String option = (String)combo.getSelectedItem();
				switch (option){
				case "Id":
					try {
						long value = Long.parseLong(textArea.getText());
						filter = new IdFilter(value);
						break;
					} catch (Exception e1) {
						return;
					}
				case "Ciudad": 
					filter = new CityFilter(textArea.getText());
					break;
				case "Nombre": 
					filter = new NameFilter(textArea.getText());
					break;
				default: return;
				}
				
				refresh();
			}
		});
		panel.add(filtrar, BorderLayout.CENTER);
		
		JButton quitarFiltro = new JButton("Quitar Filtro");
		quitarFiltro.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				filter = new TrueFilter();
				refresh();
			}
		});
		panel.add(quitarFiltro, BorderLayout.CENTER);
		
		return panel;
	}
	
	public List<Cliente> getListData(){
		return this.data;
	}

	@Override
	public Class<?>[] getTypes() {
		Class<?>[] types = {Long.class, String.class, String.class, String.class, String.class, String.class};
		return types;
	}

	@Override
	public float[] getWidths() {
		float[] widths = {30, 170, 100, 110, 50};
		return widths;
	}
}
