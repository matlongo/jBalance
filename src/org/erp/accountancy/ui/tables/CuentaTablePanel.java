package org.erp.accountancy.ui.tables;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.erp.accountancy.hibernate.entities.Cliente;
import org.erp.accountancy.hibernate.entities.Movimiento;
import org.erp.accountancy.mov.filters.MovFilter;
import org.erp.accountancy.printer.PDFPrinter;
import org.erp.accountancy.printer.SeparatorWrapper;
import org.erp.accountancy.printer.TableWrapper;
import org.erp.accountancy.printer.TextWrapper;
import org.erp.accountancy.ui.PanelManager;
import org.erp.accountancy.utils.Utils;

import com.lowagie.text.Font;


public class CuentaTablePanel extends TablePanel {

	private static final long serialVersionUID = 6050047031040779415L;
	private Cliente cliente;
	private MovFilter filter;
	private JTable totalTable;
	private DefaultTableModel tableModel;
	private Object[][] totalData;
	private String[] totalHeader;
	private String period;

	public CuentaTablePanel(Cliente cliente, MovFilter filter, String period) {
		super();
		this.cliente = cliente;
		this.filter = filter;
		this.period = period;
	}
	
	@Override
	public void addComponents(){
		JScrollPane tablePanel = this.initTable();
		JPanel buttons = this.initButons();
		JScrollPane totalTablePanel = this.initTotalTable();
		
		this.add(tablePanel, BorderLayout.NORTH);
		this.add(totalTablePanel, BorderLayout.CENTER);
		this.add(buttons, BorderLayout.SOUTH);
	}
	
	
	private JScrollPane initTotalTable() {
		String[] header = { "TOTALES", "DEBE", "HABER" };
		tableModel = new DefaultTableModel(totalData, header);
		this.totalHeader = header;
		totalTable = new JTable(tableModel){
			private static final long serialVersionUID = -2500406157748684311L;
			
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JScrollPane panel = new JScrollPane(totalTable);
		totalTable.setDefaultRenderer(Object.class, new LastRowBoldRenderer());
		
		return panel;
	}
	
	@Override
	public void goBack() {
		PanelManager.getInstance().switchPanel(PanelManager.CUENTAS_PANEL);
		PanelManager.getInstance().removePanel(this);
	}

	@Override
	public List<TableRow> getData() {
		Collection<Movimiento> movsColl = cliente.getMovimientos();
		List<TableRow> rows = new ArrayList<TableRow>();
		if (movsColl != null){
			List<Movimiento> movs = filter.filter(movsColl);
			rows.addAll(movs);
		}
		return rows;
	}
	
	private double[] getSubtotal(){
		List<TableRow> data = this.getData();
		double[] totales = new double[2];
		
		for (TableRow row: data){
			totales[0] += ((Movimiento)row).getMontoDebe();
			totales[1] += ((Movimiento)row).getMontoHaber();
		}
		
		return totales;
	}
	
	private double[] getSaldosAnteriores(){
		List<Movimiento> movs = filter.getMovsPrevios(cliente.getMovimientos());
		double[] totales = new double[2];		
		
		for (Movimiento mov: movs){
			totales[0] += mov.getMontoDebe();
			totales[1] += mov.getMontoHaber();
		}
		
		return totales;
	}
	
	@Override
	public void refresh() {
		super.refresh();
		totalData = this.getTotalData();
		tableModel.setDataVector(totalData, totalHeader);
		tableModel.fireTableDataChanged();		
	}
	
	public Object[][] getTotalData(){
		Object[][] result = new Object[4][3];
		result[0][0] = "SUBTOTAL";
		result[1][0] = "SALDOS ANTERIORES";
		result[2][0] = "TOTAL";
		result[3][0] = "REMANENTE";
		
		double[] subtotal = this.getSubtotal();
		double[] anteriores = this.getSaldosAnteriores();
		
		result[0][1] = subtotal[0];
		result[1][1] = anteriores[0];
		result[2][1] = subtotal[0] + anteriores[0];
		
		result[0][2] = subtotal[1];
		result[1][2] = anteriores[1];
		result[2][2] = subtotal[1] + anteriores[1];
		
		result[3][1] = Math.max((double)result[2][1] - (double)result[2][2], 0);
		result[3][2] = Math.max((double)result[2][2] - (double)result[2][1], 0);
		
		return result;
	}

	@Override
	protected String[] getHeader() {
		String[] headers = { "REG.", "CONCEPTO", "COMPR.", "FECHA", "DEBE", "HABER" };
		return headers;
	}

	@Override
	public String getTitle() {
		return "RESUMEN DE " + period;
	}

	public JTable getPrintableTable(){
		List<TableRow> rows = this.getData();
		
		Object[][] data = new Object[rows.size()+2][getHeader().length];
		int rowPos = 1;
		for (TableRow row : rows) {
			for (int j = 0; j < getHeader().length; j++)
				data[rowPos][j] = row.getValueAt(j);
			rowPos++;
		}
		
		data[0][1] = "Saldo Anterior";
		data[0][4] = totalData[1][1];
		data[0][5] = totalData[1][2];
		data[rows.size()+1][1] = "Subtotal";
		data[rows.size()+1][4] = totalData[2][1];
		data[rows.size()+1][5] = totalData[2][2];
		
		DefaultTableModel model = new DefaultTableModel(data, this.getHeader());
		return new JTable(model);
	}
	
	@Override
	public PDFPrinter getPDFPrinter() {
		PDFPrinter printer = new PDFPrinter(this.getTitle());

//		TableWrapper cpTableTotal = new TableWrapper(totalTable, null);
		TableWrapper movTable = new TableWrapper(this.getPrintableTable(), this.getWidths());//super.getTable(), this.getWidths());
		TextWrapper emissionDate = new TextWrapper("FECHA DE EMISION: " + Utils.getCurrentDateAsString());
		TextWrapper nombre = new TextWrapper("CLIENTE : " + cliente.getNombre());
		nombre.getFont().setStyle(Font.BOLD);
		TextWrapper domicilio = new TextWrapper("DOMICILIO: " + cliente.getDireccion());
		TextWrapper localidad = new TextWrapper("LOCALIDAD: " + cliente.getCiudad());
		TextWrapper cuit = new TextWrapper("CUIT: " + cliente.getCuit());
		TextWrapper movimientos = new TextWrapper("MOVIMIENTOS");
		movimientos.getFont().setStyle(Font.BOLD);
		movimientos.getFont().setStyle(Font.UNDERLINE);
		double remValue = (double)totalData[3][1] - (double)totalData[3][2];
		String remanente = "       SALDO DEUDOR DE Pesos ....: " + remValue;
		if ((double)totalData[3][1] < (double)totalData[3][2])
			remanente = "       SALDO ACREEDOR DE Pesos ....: " + (-remValue);
		TextWrapper totales = new TextWrapper(remanente);
		totales.getFont().setStyle(Font.BOLD);
		totales.getFont().setSize(12);
//		totales.getFont().setStyle(Font.UNDERLINE);
		

		printer.addElement(new SeparatorWrapper(), 4);
		printer.addElement(nombre, 1);
		printer.addElement(domicilio, 1);
		printer.addElement(localidad, 1);
		printer.addElement(cuit, 1);
		printer.addElement(emissionDate, 8);
		printer.addElement(new SeparatorWrapper(), 4);
		printer.addElement(movimientos, 6);
		printer.addElement(movTable, 8);
		printer.addElement(new SeparatorWrapper(), 8);
		printer.addElement(totales, 6);
//		printer.addElement(cpTableTotal);
		
		return printer;
	}

	@Override
	public Class<?>[] getTypes() {
		Class<?>[] types = {Long.class, String.class, Long.class, String.class, Double.class, Double.class};
		return types;
	}

	@Override
	public float[] getWidths() {
		float [] widths = {30, 170, 30, 50, 40, 40};
		return widths;
	}
	
}
