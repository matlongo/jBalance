package org.erp.accountancy.printer;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

public class CPText implements CustomPrintable {

	private String text;
	private Font font;
	private StringMetrics metrics;
	
	public CPText(String text) {
		this.text = text;
		this.font = new Font("SansSerif", Font.PLAIN, 10);
	}
	
	@Override
	public int print(Graphics graphics, PageFormat pf, int pageIndex) throws PrinterException {
		int ix = (int) pf.getImageableX();
		int iy = (int) pf.getImageableY();
		
		metrics = new StringMetrics(text, font, graphics);
		
		graphics.setFont(font);

		graphics.drawString(text, ix, iy + metrics.getHeigth() + metrics.getHeigth());
		
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
