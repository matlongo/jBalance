package org.erp.accountancy.printer;

import com.lowagie.text.Element;
import com.lowagie.text.pdf.draw.LineSeparator;

public class SeparatorWrapper extends ElementWrapper {

	private LineSeparator separator;
	
	public SeparatorWrapper() {
		separator = new LineSeparator();
	}
	
	@Override
	public Element getElement() {
		return separator;
	}

}
