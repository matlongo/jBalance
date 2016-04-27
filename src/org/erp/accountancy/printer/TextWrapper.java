package org.erp.accountancy.printer;

import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;

public class TextWrapper extends ElementWrapper {

	private String text;
	
	public TextWrapper(String text) {
		super.setFont(FontFactory.getFont(FontFactory.TIMES, 10));
		this.text = text;
	}
	
	@Override
	public Element getElement() {
		Paragraph paragraph = new Paragraph(text, super.getFont());
		
		return paragraph;
	}

}
