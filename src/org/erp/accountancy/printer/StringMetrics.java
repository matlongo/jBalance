package org.erp.accountancy.printer;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class StringMetrics {
	private String text;
	private Font font;
	private Graphics graphics;

	public StringMetrics(String text, Font font, Graphics graphics) {
		this.text = text;
		this.font = font;
		this.graphics = graphics;
	}

	public int getWidth() {
		FontMetrics fm = graphics.getFontMetrics(font);
		return fm.stringWidth(text) + 2;
	}

	public int getHeigth() {
		FontMetrics fm = graphics.getFontMetrics(font);
		return fm.getHeight();
	}
}
