package test;
import java.io.File;
import java.util.List;

import com.google.api.services.storage.model.Bucket;
import com.google.api.services.storage.model.StorageObject;

import br.com.livro.util.CloudStorageUtil;

public class CloudStorageUploadTest {
	
	private static final String BUCKET_NAME = "livro-lecheta-lellis";
	
	public static void main(String[] args) throws Exception {
		CloudStorageUtil cloudStorageUtil = new CloudStorageUtil("Livro lecheta");
		
		String accountId = "1040728160533-compute@developer.gserviceaccount.com";

		File p12File = new File("java-rest-livro-98fc55c374f7.p12");
		
		cloudStorageUtil.connect(accountId, p12File);
		
		System.out.println("Uploading...");
		
		File file = new File("pic.jpg");
		
		Bucket bucket = cloudStorageUtil.getBucket(BUCKET_NAME);
		
		String contentType = "image/jpeg";
		String storageProjectId = "1040728160533";
		
		StorageObject obj = cloudStorageUtil.upload(BUCKET_NAME, file, contentType, storageProjectId);
		System.out.println("File uploaded: " + obj.getName());
	}

}
