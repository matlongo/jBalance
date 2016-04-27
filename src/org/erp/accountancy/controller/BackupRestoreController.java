package org.erp.accountancy.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BackupRestoreController {

	public static final String DATABASE_FILE_PATH = "database/test.h2.db";
	public static final String TRACE_FILE_PATH = "database/test.trace.db";
	public static final String EXTENSION = ".h2.db";
	
	public static void copyFileUsingFileStreams(String source, String dest) throws IOException {
		InputStream input = null;
		OutputStream output = null;
		try {
			input = new FileInputStream(new File(source));
			output = new FileOutputStream(new File(dest));
			byte[] buf = new byte[1024];
			int bytesRead;
			while ((bytesRead = input.read(buf)) > 0) {
				output.write(buf, 0, bytesRead);
			}
		} finally {
			input.close();
			output.close();
		}
	}

}
