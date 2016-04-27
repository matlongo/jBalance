package org.erp.accountancy.ui.tables;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.erp.accountancy.printer.PDFPrinter;
import org.erp.accountancy.printer.SeparatorWrapper;
import org.erp.accountancy.printer.TableWrapper;
import org.erp.accountancy.printer.TextWrapper;
import org.erp.accountancy.utils.Utils;

/**
 * This is a template that represents a panel containing a table and two buttons
 * (print and return). The headers and the data are provided by the inherited
 * classes.
 * 
 * @author mathias
 *
 */
public abstract class TablePanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 8842818102401370660L;

	private JTable table;
	private JButton volver, imprimir;
	private DefaultTableModel tableModel;
	private String[] headers;
	private Object[][] data;

	public TablePanel() {
		this.setLayout(new BorderLayout());

		this.addComponents();

		table.setAutoCreateRowSorter(true);
		table.setAutoCreateColumnsFromModel(false);
	}

	/**
	 * Add all the components to the panel in the UI.
	 */
	public void addComponents() {
		JScrollPane tablePanel = this.initTable();
		JPanel buttons = this.initButons();

		this.add(tablePanel);
		this.add(buttons, BorderLayout.SOUTH);
	}

	/**
	 * Sets the headers and the table model of the table. It also adds the table
	 * to the panel.
	 */
	protected JScrollPane initTable() {
		headers = this.getHeader();
		tableModel = new DefaultTableModel(data, headers) {
			private static final long serialVersionUID = -2500406157748684311L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				Class<?>[] types = TablePanel.this.getTypes();
				if (columnIndex < types.length && columnIndex >= 0)
					return types[columnIndex];
				return String.class;
			}
		};

		table = new JTable(tableModel);
		JScrollPane panel = new JScrollPane(table);
		return panel;
	}

	/**
	 * Initializes all the buttons, setting their names and adding them to the
	 * panel
	 */
	protected JPanel initButons() {
		JPanel southPanel = new JPanel();

		imprimir = new JButton("Imprimir");
		imprimir.addActionListener(this);
		southPanel.add(imprimir, BorderLayout.CENTER);

		volver = new JButton("Volver");
		volver.addActionListener(this);
		southPanel.add(volver, BorderLayout.CENTER);

		return southPanel;
	}

	/**
	 * Refreshes the data and updates the changes in the UI.
	 */
	public void refresh() {
		this.updateData();

		// setting the widths
		for (int i = 0; i < headers.length; i++)
			if (headers[i].toLowerCase().contains("nombre"))
				table.getColumnModel().getColumn(i).setPreferredWidth(150);
			else if (headers[i].toLowerCase().contains("leg") || headers[i].toLowerCase().contains("reg")
					|| headers[i].toLowerCase().contains("id"))
				table.getColumnModel().getColumn(i).setPreferredWidth(20);

		tableModel.setDataVector(data, headers);
		tableModel.fireTableDataChanged();
	}

	/**
	 * This method updates the data provided by the inherited classes, and sets
	 * the data in the table
	 */
	public void updateData() {
		List<TableRow> rows = this.getData();

		data = new Object[rows.size()][headers.length];
		int rowPos = 0;
		for (TableRow row : rows) {
			for (int j = 0; j < headers.length; j++)
				data[rowPos][j] = row.getValueAt(j);
			rowPos++;
		}
	}

	/**
	 * Prints the table using the PrinterJob class.
	 */
	public void print() {
		PDFPrinter printer = this.getPDFPrinter();

		printer.printDocument();// .exportToPdf("file");
	}

	/**
	 * Returns the PDFPrinter object that should be printed.
	 * 
	 * @return PDFPrinter to print
	 */
	public PDFPrinter getPDFPrinter() {
		PDFPrinter printer = new PDFPrinter(this.getTitle());
		printer.addElement(new SeparatorWrapper(), 2);
		printer.addElement(new TextWrapper("FECHA DE EMISION: " + Utils.getCurrentDateAsString()), 6);
		printer.addElement(new SeparatorWrapper(), 6);
		printer.addElement(new TableWrapper(table));

		return printer;
	}

	/**
	 * This method implements the action to be done when a button is pressed.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == volver) {
			this.goBack();
		} else if (e.getSource() == imprimir) {
			this.print();
		}
	}

	/**
	 * Getter method to return the jTable that is used in this panel.
	 * 
	 * @return JTable shown
	 */
	public JTable getTable() {
		return table;
	}

	/**
	 * Return the types of data that each columns has (i.e., Double, String,
	 * Long, or Integer)
	 * 
	 * @return Array of types
	 */
	public abstract Class<?>[] getTypes();

	/**
	 * This method is used to return to the previous screen in the UI.
	 */
	public abstract void goBack();

	/**
	 * Returns a list containing all the data that should be shown in the table.
	 * Each row is represented by a TableRow object.
	 * 
	 * @return List of data
	 */
	public abstract List<TableRow> getData();

	/**
	 * This method returns the headers of the table.
	 * 
	 * @return Array of headers
	 */
	protected abstract String[] getHeader();

	/**
	 * Returns the title to be shown when printing the table.
	 * 
	 * @return Title to be printed
	 */
	public abstract String getTitle();
}
