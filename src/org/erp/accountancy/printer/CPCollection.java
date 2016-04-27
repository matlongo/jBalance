package org.erp.accountancy.printer;

import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.util.List;

public class CPCollection implements CustomPrintable {

	private List<CustomPrintable> printables;
	private int gap = 5;
	private boolean vertical = true;

	public CPCollection(List<CustomPrintable> printables) {
		this.printables = printables;
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		PrintableArea area = new PrintableArea(pageFormat);

		for (CustomPrintable printable : printables) {
			// We'll use a copy of the graphics to print the table to. This
			// protects
			// us against changes that the printable could make to the graphics
			// object.
			Graphics gCopy = graphics.create();

			/* print the table into the shrunken area */
			int retVal = printable.print(gCopy, area, pageIndex);

			/* if there's no pages left, return */
			if (retVal == NO_SUCH_PAGE) {
				return retVal;
			}

			gCopy.dispose();

			if (vertical) {
				area.setImageHeight(area.getImageableHeight() - (printable.getHeight() + this.gap));
				area.setTopLeftBeginningY(area.getImageableY() + printable.getHeight() + this.gap);
			} else {
				area.setImageWidth(area.getImageableWidth() - (printable.getWidth() + this.gap));
				area.setTopLeftBeginningX(area.getImageableX() + printable.getWidth() + this.gap);
			}
		}
		return PAGE_EXISTS;
	}

	@Override
	public int getWidth() {
		if (!vertical) {
			int width = 0;
			for (CustomPrintable cp : printables) {
				width += cp.getWidth();
			}
			return width;
		} else {
			int max = 0;
			for (CustomPrintable cp : printables) {
				if (max < cp.getWidth()) {
					max = cp.getWidth();
				}
			}
			return max;
		}
	}

	@Override
	public int getHeight() {
		if (vertical) {
			int height = 0;
			for (CustomPrintable cp : printables) {
				height += cp.getHeight();
			}
			return height;
		} else {
			int max = 0;
			for (CustomPrintable cp : printables) {
				if (max < cp.getHeight()) {
					max = cp.getHeight();
				}
			}
			return max;
		}
	}

	public void setGap(int gap) {
		this.gap = gap;
	}

	public void add(CustomPrintable printable) {
		this.printables.add(printable);
	}

}
