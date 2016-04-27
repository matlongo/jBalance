package org.erp.accountancy.printer;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import org.erp.accountancy.utils.Utils;

public class CPEmisionDate implements CustomPrintable {

	private Font font = new Font("SansSerif", Font.PLAIN, 11);
	private String header;
	private StringMetrics metrics;
	
	public CPEmisionDate() {
		header = "FECHA DE EMISION : " + Utils.getCurrentDateAsString();
	}

	@Override
	public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
//		Graphics2D g2d = (Graphics2D) g;
//		g2d.translate(pf.getImageableX(), pf.getImageableY());
//		g2d.setFont(font);
//		g2d.setColor(Color.black);
//		g2d.drawString("FECHA DE EMISION : "+ new Date(), 50, 200);
		int ix = (int) pf.getImageableX();
		int iy = (int) pf.getImageableY();
		
		metrics = new StringMetrics(header, font, g);
		
		g.setFont(font);
		g.drawString(header, ix, iy + metrics.getHeigth() + metrics.getHeigth());
		
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
