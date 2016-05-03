package org.erp.accountancy.printer;

import java.awt.Color;
import java.util.Enumeration;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

public class TableWrapper extends ElementWrapper {

	/** Table to be added in the pdf document */
	private JTable table;
	/** Widths of the columns to be added */
	private float[] widths;

	public TableWrapper(JTable table, float[] widths) {
		super();
		this.table = table;
		this.widths = widths;
		super.setFont(FontFactory.getFont(FontFactory.TIMES, 10));
	}

	@Override
	public Element getElement() {
		return this.toPdf(table);
	}

	/**
	 * This method returns the JTable content in a PdfTable in order to be added
	 * to a PDF document
	 * 
	 * @param table
	 *            JTable to be translated
	 * @return PdfTable to be added to a document
	 * @throws BadElementException
	 */
	public PdfPTable toPdf(JTable table) {
		PdfPTable pdfTable = (widths == null) ? new PdfPTable(this.getWidths(table)) : new PdfPTable(widths);

		pdfTable.setWidthPercentage(98);

		// adding table headers
		for (int i = 0; i < table.getColumnCount(); i++) {
			PdfPCell cell = new PdfPCell(new Phrase(table.getColumnName(i), super.getFont()));
			if (i == 0)
				cell.setNoWrap(false);
			cell.setBackgroundColor(Color.LIGHT_GRAY);
			pdfTable.addCell(cell);
		}
		// extracting data from the JTable and inserting it to PdfPTable
		for (int rows = 0; rows < table.getRowCount(); rows++) {
			for (int cols = 0; cols < table.getColumnCount(); cols++) {
				Object value;
				if (table.getRowSorter() != null)
					value = table.getModel().getValueAt(table.getRowSorter().convertRowIndexToModel(rows), cols);
				else
					value = table.getModel().getValueAt(rows, cols);
				String valueToString = (value == null) ? "" : value.toString();

				PdfPCell cell = new PdfPCell(new Phrase(valueToString.toString(), super.getFont()));
				if (rows == 0)
					cell.setNoWrap(false);
				pdfTable.addCell(cell);
			}
		}
		return pdfTable;
	}

	/**
	 * This method returns the widths of the table showed in the jTable so that
	 * the PdfTable has the same relation of widths when printing.
	 * 
	 * @param table
	 *            jTable showed in the screen.
	 * @return Array of floats representing the widths of the table.
	 */
	public float[] getWidths(JTable table) {
		float[] widths = new float[table.getColumnCount()];
		Enumeration<TableColumn> columns = table.getColumnModel().getColumns();
		int pos = 0;

		while (columns.hasMoreElements()) {
			widths[pos++] = columns.nextElement().getWidth();
		}

		return widths;
	}

}
