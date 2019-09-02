package br.com.livro.domain;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

@Component
public class UploadService {
	public String upload(String filename, InputStream in) throws IOException {
		if (filename == null || in == null) {
			throw new IllegalArgumentException("Invalid parameters");
		}
		// JVM's temporary folder
		File tmpDir = new File(System.getProperty("java.io.tmpdir"), "carros");
		if (!tmpDir.exists()) {
			tmpDir.mkdir();
		}
		// Creates the file
		File file = new File(tmpDir, filename);
		FileOutputStream out = new FileOutputStream(file);

		IOUtils.copy(in, out);
		IOUtils.closeQuietly(out);

		String path = file.getAbsolutePath();
		return path;
	}

}
