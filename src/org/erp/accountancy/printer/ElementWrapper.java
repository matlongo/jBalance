package org.erp.accountancy.printer;

import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;

/**
 * This class is intended to provide a wrapper for the elements used in a PdfWriter.
 * The inherited classes will provide the specific implementation for each case.
 * 
 * @author mathias
 *
 */
public abstract class ElementWrapper {
	
	private Font font = FontFactory.getFont(FontFactory.TIMES, 10);
	
	public abstract Element getElement();
	
	public void setFont(Font font){
		this.font = font;
	}
	
	public Font getFont(){
		return this.font;
	}
	
}
