package org.erp.accountancy.printer;

import java.awt.print.Printable;
import java.text.MessageFormat;

import javax.swing.JTable;
import javax.swing.table.TableModel;

public class FancyPrintingTable extends JTable {

	private static final long serialVersionUID = 3006911245908522600L;
	
	public FancyPrintingTable(TableModel model) {
        super(model);
    }

	/**
	 * Overridden to return a fancier printable, that wraps the default. Ignores
	 * the given header and footer. Renders its own header. Always uses the page
	 * number as the footer.
	 */
	public Printable getPrintable(PrintMode printMode, MessageFormat headerFormat, MessageFormat footerFormat, String title) {
		/* Fetch the default printable */
		Printable delegate = super.getPrintable(printMode, headerFormat, footerFormat);

		/* Return a fancy printable that wraps the default */
		return new FancyPrintable(delegate, title);
	}

}
