package org.erp.accountancy.printer;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import org.erp.accountancy.utils.Utils;

public class FancyPrintable implements Printable {

	public static final int VERTICAL_SPACE = 20;

	/** The Printable to wrap */
	private Printable delegate;
	private String header, title;
	private Font headerFont, titleFont;

	/**
	 * Constructs a FancyPrintable to wrap the given Printable.
	 */
	public FancyPrintable(Printable delegate, String title) {
		this.delegate = delegate;
		header = "FECHA DE EMISION : " + Utils.getCurrentDateAsString();
		this.title = title;
		titleFont = new Font("SansSerif", Font.BOLD, 14);
		headerFont = new Font("SansSerif", Font.PLAIN, 12);
	}

	@Override
	public int print(Graphics graphics, PageFormat pf, int pageIndex) throws PrinterException {
		/* top left of the imageable area */
		int ix = (int) pf.getImageableX();
		int iy = (int) pf.getImageableY();

		/* width and height of the imageable area */
		int iw = (int) pf.getImageableWidth();
		int ih = (int) pf.getImageableHeight();

		StringMetrics headerMetrics = new StringMetrics(header, headerFont, graphics);
		StringMetrics titleMetrics = new StringMetrics(title, headerFont, graphics);

		/*
		 * First, calculate the shrunken area that we want the table to print
		 * into.
		 */

		/*
		 * inset the table top to leave space for the top image + 10 pixels +
		 * the final grades label + 10 pixels.
		 */
		final int tableY = iy + headerMetrics.getHeigth() + titleMetrics.getHeigth() + VERTICAL_SPACE * 2;

		/* inset the table bottom by the height of the bottom image */
		final int tableH = ih - headerMetrics.getHeigth() - titleMetrics.getHeigth() - VERTICAL_SPACE * 2;

		/*
		 * Now print the table into this smaller area.
		 */

		/*
		 * create a new page format representing the shrunken area to print the
		 * table into
		 */
		PageFormat format = new PageFormat() {
			public double getImageableX() {
				return ix;
			}

			public double getImageableY() {
				return tableY;
			}

			public double getImageableWidth() {
				return iw;
			}

			public double getImageableHeight() {
				return tableH;
			}
		};

		/*
		 * We'll use a copy of the graphics to print the table to. This protects
		 * us against changes that the delegate printable could make to the
		 * graphics object.
		 */
		Graphics gCopy = graphics.create();

		/* print the table into the shrunken area */
		int retVal = delegate.print(gCopy, format, pageIndex);

		/* if there's no pages left, return */
		if (retVal == NO_SUCH_PAGE) {
			return retVal;
		}

		/* dispose of the graphics copy */
		gCopy.dispose();

		graphics.setFont(titleFont);
		graphics.drawString(title, ix + (iw - titleMetrics.getWidth()) / 2, iy + titleMetrics.getHeigth());
		graphics.setFont(headerFont);
		graphics.drawString(header, ix, iy + headerMetrics.getHeigth() + titleMetrics.getHeigth() + VERTICAL_SPACE);

		return PAGE_EXISTS;
	}
}
