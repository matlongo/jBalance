package org.erp.accountancy.printer;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

public class CPTitle implements CustomPrintable {

	private Font font = new Font("SansSerif", Font.BOLD, 14);
	private String title;
	private StringMetrics metrics;
	
	public CPTitle(String title){
		this.title = title;
	}

	@Override
	public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
		if (pageIndex > 0) // we only print one page
            return Printable.NO_SUCH_PAGE;
		int ix = (int) pf.getImageableX();
		int iy = (int) pf.getImageableY();
		int iw = (int) pf.getImageableWidth();
		
		metrics = new StringMetrics(title, font, g);
		
		g.setFont(font);
		g.drawString(title, ix + (iw - metrics.getWidth()) / 2, iy + metrics.getHeigth());
		return Printable.PAGE_EXISTS;
	}

	@Override
	public int getWidth() {
		return metrics.getWidth();
	}

	@Override
	public int getHeight() {
		return metrics.getHeigth();
	}

}
