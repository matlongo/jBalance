package org.erp.accountancy.printer;

public class CPTable {// implements CustomPrintable {

//	// private Printable table;
//	public static int MAX_HEIGHT = 300;
//	private Font font = new Font("SansSerif", Font.BOLD, 15);
//	private int prevHeight, height, width;
//	private JTable[] tables;
//	private Printable[] printableTables;
//	private MessageFormat footer;
//
//	public CPTable(JTable table) {
//		this.footer = new MessageFormat("HOJA N° {0,number,integer}");
//		this.height = table.getHeight();
//		this.prevHeight = height;
//		// this.table = table.getPrintable(JTable.PrintMode.FIT_WIDTH, null,
//		// footer);
//		// this.tables = this.splitTable(table, MAX_HEIGHT);
//		this.toPdf(table);
//	}
//
//	// private JTable[] splitTable(JTable mainTable, int height) {
//	// double amount = (double) mainTable.getHeight() / (double) height;
//	// // int rest = (int)(mainTable.getHeight() % height);
//	// int rows = mainTable.getRowCount();
//	//
//	// JTable[] tables = new JTable[(int) amount + 1];
//	// printableTables = new Printable[(int) amount + 1];
//	// int amountOfRows = (int) (rows / amount);
//	//
//	// int from = 0, to = amountOfRows;
//	// String[] headers = getHeader(mainTable);
//	// for (int i = 0; i < ((int) amount + 1); i++) {
//	// Object[][] data = getTableData(mainTable, from, to);
//	// DefaultTableModel tableModel = new DefaultTableModel(data, headers);
//	// tables[i] = new JTable(tableModel);
//	// tables[i].setSize(tables[i].getPreferredSize());
//	// toPdf(tables[i], i);
//	// tables[i].getTableHeader().setSize(tables[i].getTableHeader().getPreferredSize());
//	// printableTables[i] = tables[i].getPrintable(JTable.PrintMode.FIT_WIDTH,
//	// null, footer);
//	// from = to;
//	// to = Math.min(to + amountOfRows, rows);
//	// }
//	//
//	// return tables;
//	// }
//
//	public void toPdf(JTable table) {
//
//		try {
//			Document doc = new Document(PageSize.A4, 36, 36, 54, 54);
//			PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("table.pdf"));
//			writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
//			doc.open();
//			PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
//			// adding table headers
//			for (int i = 0; i < table.getColumnCount(); i++) {
//				pdfTable.addCell(table.getColumnName(i));
//			}
//			// extracting data from the JTable and inserting it to PdfPTable
//			for (int rows = 0; rows < table.getRowCount() - 1; rows++) {
//				for (int cols = 0; cols < table.getColumnCount(); cols++) {
//					pdfTable.addCell(table.getModel().getValueAt(rows, cols).toString());
//
//				}
//			}
//			Chunk chunk = new Chunk("Hello world",
//					FontFactory.getFont(FontFactory.COURIER, 20, Font.ITALIC, new Color(255, 0, 0)));
//			Rectangle rect = new Rectangle(36, 54, 559, 788);
//			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER,
//					new Phrase("Title",
//							FontFactory.getFont(FontFactory.TIMES_BOLD, 20, Font.PLAIN, new Color(0, 0, 0))),
//					(rect.getLeft() + rect.getRight()) / 2, rect.getTop()+10, 0);
//			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER,
//					new Phrase(String.format("page %d", 0)), (rect.getLeft() + rect.getRight()) / 2,
//					rect.getBottom() - 18, 0);
//			// doc.add(chunk);
//			// doc.add(chunk);
//			// doc.add(chunk);
//			doc.add(pdfTable);
//			doc.add(new Paragraph(chunk));
//			doc.close();
//			System.out.println("done");
//		} catch (DocumentException ex) {
//			ex.printStackTrace();
//		} catch (FileNotFoundException ex) {
//			ex.printStackTrace();
//		}
//
//	}
//
//	public void print(JTable table) {
//		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
//		int nRow = dtm.getRowCount(), nCol = dtm.getColumnCount();
//		Object[][] tableData = new Object[nRow][nCol];
//		for (int i = 0; i < nCol; i++) {
//			System.out.print(dtm.getColumnName(i) + ",");
//		}
//		System.out.println();
//		for (int i = 0; i < nRow; i++) {
//			for (int j = 0; j < nCol; j++) {
//				tableData[i][j] = dtm.getValueAt(i, j);
//				System.out.print(tableData[i][j] + ",");
//			}
//			System.out.println();
//		}
//	}
//
//	public Object[][] getTableData(JTable table, int from, int to) {
//		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
//		int nCol = dtm.getColumnCount();
//		Object[][] tableData = new Object[to - from][nCol];
//		for (int i = from; i < to; i++)
//			for (int j = 0; j < nCol; j++) {
//				tableData[i - from][j] = dtm.getValueAt(i, j);
//			}
//		return tableData;
//	}
//
//	public String[] getHeader(JTable table) {
//		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
//		int nCol = dtm.getColumnCount();
//		String[] header = new String[nCol];
//		for (int i = 0; i < header.length; i++) {
//			header[i] = dtm.getColumnName(i);
//			System.out.println("Header: " + header[i]);
//		}
//
//		return header;
//	}
//
//	@Override
//	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
//		// height = prevHeight;
//		// if (height > pageFormat.getImageableHeight()) {
//		// height = (int) pageFormat.getImageableHeight();
//		// prevHeight -= height;
//		// }
//		// if (pageIndex >= tables.length)
//		return NO_SUCH_PAGE;
//
//		// Graphics2D g2d = (Graphics2D) graphics;
//		// g2d.translate(pageFormat.getImageableX(),
//		// pageFormat.getImageableY());
//		//
//		// height = tables[pageIndex].getHeight();
//		// System.out.println("PageIndex: " + pageIndex);
//		// print(tables[pageIndex]);
//		// int prin = printableTables[pageIndex].print(graphics, pageFormat,
//		// pageIndex);
//		// System.out.println(prin + " " + NO_SUCH_PAGE + " " + PAGE_EXISTS);
//		// return PAGE_EXISTS;
//	}
//
//	@Override
//	public int getWidth() {
//		return width;
//	}
//
//	@Override
//	public int getHeight() {
//		return height;
//	}

}
