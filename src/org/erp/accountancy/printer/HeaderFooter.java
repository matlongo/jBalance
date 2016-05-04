package org.erp.accountancy.printer;

import java.io.IOException;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

public class HeaderFooter extends PdfPageEventHelper {
	/** Current page number (will be reset for every chapter). */
	private int pagenumber;

	private Image img;

	public HeaderFooter() {
		try {
			img = Image.getInstance("images/membrete-2k.png");
			img.setAlignment(Image.MIDDLE);
			img.scalePercent(26);
		} catch (BadElementException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize one of the headers.
	 * 
	 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onOpenDocument(
	 *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
	 */
	// public void onOpenDocument(PdfWriter writer, Document document) {
	// }

	/**
	 * Initialize one of the headers, based on the chapter title; reset the page
	 * number.
	 * 
	 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onChapter(
	 *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document, float,
	 *      com.itextpdf.text.Paragraph)
	 */
	public void onChapter(PdfWriter writer, Document document, float paragraphPosition, Paragraph title) {
		pagenumber = 1;
	}

	/**
	 * Increase the page number.
	 * 
	 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onStartPage(
	 *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
	 */
	public void onStartPage(PdfWriter writer, Document document) {
		pagenumber++;
	}

	/**
	 * Adds the header and the footer.
	 * 
	 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(
	 *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
	 */
	public void onEndPage(PdfWriter writer, Document document) {
		PdfContentByte cb = writer.getDirectContent();

		// add header image
		try {
			img.setAbsolutePosition(document.left(), document.top() + 10);
			writer.getDirectContent().addImage(img);
		} catch (Exception x) {
			x.printStackTrace();
		}
//		ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, header,
//				(document.right() - document.left()) / 2 + document.leftMargin(), document.top() + 10, 0);

		ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, new Phrase(String.format("Hoja %d", pagenumber)),
				(document.left() + document.right()) / 2, document.bottom() - 18, 0);
	}
}
