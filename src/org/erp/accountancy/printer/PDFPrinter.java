package org.erp.accountancy.printer;

import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;

/**
 * This class represents a group of elements that form a document to be printed.
 * It has a collection of elements, which will be added using the addElement
 * Method, that will represent the whole document.
 * 
 * In order to print a PDF two libraries were used: PDFBox and iText.
 * 
 * @author mathias
 *
 */
public class PDFPrinter {

	/** This constant represents the printable area of the document */
	public static final String RECTANGLE_NAME = "rectangle";

	/** This constant defines the gap between two elements by default */
	public static final int GAP = 2;

	/** List of elements to be printed */
	private List<ElementWrapper> elements;

	/** Basic constructor */
	public PDFPrinter(String title) {
		this.elements = new ArrayList<>();
		//We add the title as the first element to be added to the document.
		ElementWrapper wrapper = new TextWrapper(title);
		wrapper.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 16));
		this.addElement(wrapper, 15);
	}

	/**
	 * Adds an element at the bottom of the list.
	 * 
	 * @param element
	 *            Element to be added.
	 */
	public void addElement(ElementWrapper element) {
		this.addElement(element, GAP);
	}

	/**
	 * Adds an element at the bottom of the list.The gapSize determines the size
	 * of the gap between this element and the next one.
	 * 
	 * @param element
	 *            Element to be added.
	 */
	public void addElement(ElementWrapper element, int gapSize) {
		this.elements.add(element);
		// We add a space between each object.
		TextWrapper gap = new TextWrapper(" ");
		gap.setFont(FontFactory.getFont(FontFactory.COURIER, gapSize));
		elements.add(gap);
	}

	/**
	 * This method will prompt a dialog to ask the user the configuration of the
	 * printer that will be used to print the document.
	 */
	public void printDocument() {
		// To avoid a file construction we keep the document in-memory
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);

		// We print the document into the ByteArrayOutputStream
		this.printDocument(ps);

		// We print it asking the user for the configurations
		try {
			this.printPDF(baos.toByteArray());
		} catch (IOException | PrinterException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method will export the document to a pdfFile instead of printing it
	 * using the printer.
	 * 
	 * @param fileName
	 *            Name of the file to be exported.
	 */
	public void exportToPdf(String fileName) {
		try {

			FileOutputStream stream = new FileOutputStream(fileName);
			this.printDocument(stream);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method carries the document creation, setting the necessary
	 * configuration. It will iterate over all the previously added elements and
	 * will add them to a new Document. After that, it will print the whole
	 * document into the stream.
	 * 
	 * @param stream
	 *            Stream where the document will be printed.
	 */
	private void printDocument(OutputStream stream) {
		Rectangle rectangle = new Rectangle(PageSize.A4.getLeft(), PageSize.A4.getBottom() + 40, PageSize.A4.getRight(),
				PageSize.A4.getTop() - 40);
		Document doc = new Document(rectangle, 36, 36, 54, 54);
		PdfWriter writer = null;
		try {
			writer = PdfWriter.getInstance(doc, stream);

			HeaderFooter event = new HeaderFooter();
			writer.setPageEvent(event);
			writer.setBoxSize(RECTANGLE_NAME, rectangle);// new Rectangle(36,
															// 54, 559, 388));

			doc.open();

			
			for (ElementWrapper element : elements) {
				doc.add(element.getElement());
			}

			doc.close();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method prompts a dialog asking the user for the printer
	 * configuration, in order to print a pdf file given by parameter
	 * 
	 * @param fileName
	 *            File name to be printed.
	 * @throws IOException
	 * @throws PrinterException
	 */
	public void printPDF(String fileName) throws IOException, PrinterException {
		PrinterJob job = PrinterJob.getPrinterJob();

		if (job.printDialog()) {
			PDDocument doc = PDDocument.load(new File(fileName));
			PDFPageable pageable = new PDFPageable(doc);
			job.setPageable(pageable);
			job.print();

			doc.close();
		}
	}

	/**
	 * This method prompts a dialog asking the user for the printer
	 * configuration, in order to print an array of bytes. It also returns the
	 * printer job, as it can be reused by other files.
	 * 
	 * @param data
	 * @throws IOException
	 * @throws PrinterException
	 */
	public PrinterJob printPDF(byte[] data) throws IOException, PrinterException {
		PrinterJob job = PrinterJob.getPrinterJob();

		if (job.printDialog()) {
			this.printPDF(data, job);
		}

		return job;
	}

	/**
	 * Given a printer job, all the data of a particular pdf is printed.
	 * 
	 * @param data
	 *            PDF to be printed
	 * @param job
	 *            Printer job used to print the pdf.
	 * @throws IOException
	 * @throws PrinterException
	 */
	public void printPDF(byte[] data, PrinterJob job) throws IOException, PrinterException {
		PDDocument doc = PDDocument.load(data);
		Pageable pageable = this.getPageableDoc(job, doc);

		job.setPageable(pageable);
		job.print();

		doc.close();
	}

	/**
	 * This method returns a custom Pageable where margins can be customized.
	 * 
	 * @param job
	 *            PrinterJob.
	 * @param doc
	 *            Document to be printed.
	 * @return
	 */
	private Pageable getPageableDoc(PrinterJob job, PDDocument doc) {
		PDFPageable pageable = new PDFPageable(doc);

		PageFormat pf = job.defaultPage();
		Paper paper = new Paper();
		pf.setPaper(paper);

		Pageable myPageable = new Pageable() {
			@Override
			public Printable getPrintable(int pageIndex) throws IndexOutOfBoundsException {
				return pageable.getPrintable(pageIndex);
			}

			@Override
			public PageFormat getPageFormat(int pageIndex) throws IndexOutOfBoundsException {
				return pf;
			}

			@Override
			public int getNumberOfPages() {
				return pageable.getNumberOfPages();
			}
		};
		return myPageable;
	}
}
