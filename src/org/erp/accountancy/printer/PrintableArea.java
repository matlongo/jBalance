package org.erp.accountancy.printer;

import java.awt.print.PageFormat;

public class PrintableArea extends PageFormat {
	
	private double topLeftBeginningX;
	private double topLeftBeginningY;
	private double imageWidth;
	private double imageHeight;

	public PrintableArea(PageFormat pf) {
		/* top left of the imageable area */
		topLeftBeginningX = (int) pf.getImageableX();
		topLeftBeginningY = (int) pf.getImageableY();

		/* width and height of the imageable area */
		imageWidth = (int) pf.getImageableWidth();
		imageHeight = (int) pf.getImageableHeight();
	}

	public PrintableArea(int topLeftBeginningX, int topLeftBeginningY, int imageWidth, int imageHeight) {
		super();
		this.topLeftBeginningX = topLeftBeginningX;
		this.topLeftBeginningY = topLeftBeginningY;
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
	}

	public double getImageableX() {
		return topLeftBeginningX;
	}

	public double getImageableY() {
		return topLeftBeginningY;
	}

	public double getImageableWidth() {
		return imageWidth;
	}

	public double getImageableHeight() {
		return imageHeight;
	}

	public void setTopLeftBeginningX(double topLeftBeginningX) {
		this.topLeftBeginningX = topLeftBeginningX;
	}

	public void setTopLeftBeginningY(double d) {
		this.topLeftBeginningY = d;
	}

	public void setImageWidth(double imageWidth) {
		this.imageWidth = imageWidth;
	}

	public void setImageHeight(double d) {
		this.imageHeight = d;
	}
	
	@Override
	public String toString() {
		String result = "x: " + this.getImageableX() + "\n";
		result += "y: " + this.getImageableY() + "\n";
		result += "Width: " + this.getImageableWidth() + "\n";
		result += "Height: " + this.getImageableHeight();
		
		return result;
	}
}
