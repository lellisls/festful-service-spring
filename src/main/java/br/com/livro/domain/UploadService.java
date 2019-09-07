package br.com.livro.domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import com.google.api.services.storage.model.StorageObject;
import com.google.common.io.Files;

import br.com.livro.util.CloudStorageUtil;

@Component
public class UploadService {
	private static final String PROJECT_ID = "1040728160533";
	private static final String ACCOUNT_ID = "1040728160533-compute@developer.gserviceaccount.com";
	private static final String APP_NAME = "Livro Lecheta";
	private static final String BUCKET_NAME = "livro-lecheta-lellis";

	public String upload(String filename, InputStream in) throws Exception {
		File file = saveToTempDir(filename, in);
		String url = uploadToCloudStorage(file);

		return url;
	}

	private File saveToTempDir(String filename, InputStream in) throws FileNotFoundException, IOException {
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

		return file;
	}

	private String uploadToCloudStorage(File file) throws Exception {
		String p12FilePath = System.getProperty("p12File");
		if (p12FilePath == null) {
			throw new IOException("Server error");
		}

		File p12File = new File(p12FilePath);
		if (!p12File.exists()) {
			throw new IOException("Server error");
		}

		CloudStorageUtil cloudStorageUtil = new CloudStorageUtil(APP_NAME);
		cloudStorageUtil.connect(ACCOUNT_ID, p12File);

		String fileName = file.getName();
		String contentType = getContentType(fileName);
		String storageProjectId = PROJECT_ID;

		StorageObject obj = cloudStorageUtil.upload(BUCKET_NAME, file, contentType, storageProjectId);

		if (obj == null) {
			throw new IOException("Error uploading file.");
		}

		String url = String.format("https://storage.googleapis.com/%s/%s", BUCKET_NAME, obj.getName());
		return url;
	}

	private String getContentType(String fileName) {
		String ext = Files.getFileExtension(fileName);

		if ("png".equals(ext)) {
			return "image/png";
		} else if ("jpg".equals(ext)) {
			return "image/jpg";
		} else if ("gif".equals(ext)) {
			return "image/gif";
		}

		return "text/plain";
	}

}
