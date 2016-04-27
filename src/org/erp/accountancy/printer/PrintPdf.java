package org.erp.accountancy.printer;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.FileInputStream;
import java.io.IOException;
import javax.print.PrintService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import com.lowagie.tools.Executable;

public class PrintPdf {

	// public static void main(String[] args) throws IOException,
	// PrinterException {
	//
	// String fileName = "";
	// String printerName = "";
	// String jobName = "PDF Print Job";
	//
	// new PrintPdf().printPdf(fileName,printerName ,jobName);
	//
	// }

	public static PrintService choosePrinter() {
		PrinterJob printJob = PrinterJob.getPrinterJob();
		if (printJob.printDialog()) {
			return printJob.getPrintService();
		} else {
			return null;
		}
	}

	public static void printPDF(String fileName)// , PrintService printer)
			throws IOException, PrinterException {
		PrinterJob job = PrinterJob.getPrinterJob();
		// job.setPrintService(printer);
		if (!job.printDialog())
			return;
		PDDocument doc = PDDocument.load(new FileInputStream(fileName));
		PDFPageable pageable = new PDFPageable(doc);
		job.setPageable(pageable);
		job.print();
		doc.close();
		// doc.silentPrint(job);
	}

	public void printPdf()// byte[] pdfContent)
			throws IOException, PrinterException {

		// FileInputStream fis = new FileInputStream("HelloWorld.pdf");

		System.out.println("HOLA");
		// Executable ex = new Executable();
		// ex.openDocument("HelloWorld.pdf");
		Executable.printDocument("HelloWorld.pdf");

		// FileInputStream fileInputStream = new
		// FileInputStream("HelloWorld.pdf");
		//
		// byte[] pdfContent = new byte[fileInputStream.available()];
		//
		// fileInputStream.read(pdfContent, 0, fileInputStream.available());
		//
		// ByteBuffer buffer = ByteBuffer.wrap(pdfContent);
		// final PDFFile pdfFile = new PDFFile(buffer);
		// PDFPrintPage pages = new PDFPrintPage(pdfFile);
		//
		// PrinterJob printJob = PrinterJob.getPrinterJob();
		//
		// if (printJob.printDialog()) {
		// PageFormat pageFormat = PrinterJob.getPrinterJob().defaultPage();
		//
		// Book book = new Book();
		// System.out.println(pdfFile.getNumPages());
		// book.append(pages, pageFormat, pdfFile.getNumPages());
		// printJob.setPageable(book);

		// Paper paper = new Paper();
		// paper.setImageableArea(0, 0, paper.getWidth(), paper.getHeight());
		// pageFormat.setPaper(paper);

		// PrintService[] printServices = PrinterJob.lookupPrintServices();
		//
		// for (int count = 0; count < printServices.length; ++count) {
		//
		// if (printerName.equalsIgnoreCase(printServices[count].getName())) {
		//
		// printJob.setPrintService(printServices[count]);
		//
		// break;
		//
		// }
		//
		// }

		// printJob.print();
		// }
		//
		// fileInputStream.close();
	}

}
