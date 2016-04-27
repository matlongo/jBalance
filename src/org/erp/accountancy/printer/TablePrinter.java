package org.erp.accountancy.printer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class TablePrinter implements Printable {

	private PrinterJob printerJob;
	private CPTitle title;
	private CPEmisionDate ed;
	private Printable table;
//	private Book book;
	
	public TablePrinter(Printable table, String titleString) {
		this.printerJob = PrinterJob.getPrinterJob();
//		this.book = new Book();
		
		this.table = table;
		title = new CPTitle(titleString);
		ed = new CPEmisionDate();
//		
//		this.book.append(title, printerJob.defaultPage(), 1);
//		this.book.append(ed, printerJob.defaultPage(), 1);
//		this.book.append(table, printerJob.defaultPage());
	}
	
	public void print(){
		printerJob.setPrintable(this);
		if (printerJob.printDialog()){
			try {
				printerJob.print();
			} catch (PrinterException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		Graphics2D g2d = (Graphics2D) graphics;
		g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
		g2d.setColor(Color.black);
		title.print(graphics, pageFormat, pageIndex);
		ed.print(graphics, pageFormat, pageIndex);
		table.print(graphics, pageFormat, pageIndex);
		return Printable.PAGE_EXISTS;
	}
	
	
}
