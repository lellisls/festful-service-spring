package test;

import java.io.File;
import java.util.List;

import com.google.api.services.storage.model.Bucket;
import com.google.api.services.storage.model.StorageObject;

import br.com.livro.util.CloudStorageUtil;

public class CloudStorageTest {
	private static final String BUCKET_NAME = "livro-lecheta-lellis";

	public static void main(String[] args) throws Exception {
		CloudStorageUtil cloudStorageUtil = new CloudStorageUtil("Livro lecheta");

		String accountId = "1040728160533-compute@developer.gserviceaccount.com";

		File p12File = new File("java-rest-livro-98fc55c374f7.p12");

		cloudStorageUtil.connect(accountId, p12File);

		Bucket bucket = cloudStorageUtil.getBucket(BUCKET_NAME);

		System.out.println("name: " + bucket.getName());
		System.out.println("location: " + bucket.getLocation());
		System.out.println("timeCreated: " + bucket.getTimeCreated());
		System.out.println("owner: " + bucket.getOwner());

		List<StorageObject> files = cloudStorageUtil.getBucketFiles(bucket);
		for (StorageObject object : files) {
			System.out.println("> " + object.getMediaLink() + " (" + object.getSize() + " bytes)");
		}
	}

}
