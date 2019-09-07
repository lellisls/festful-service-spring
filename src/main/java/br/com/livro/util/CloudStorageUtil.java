package br.com.livro.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.storage.Storage;
import com.google.api.services.storage.StorageScopes;
import com.google.api.services.storage.model.Bucket;
import com.google.api.services.storage.model.ObjectAccessControl;
import com.google.api.services.storage.model.StorageObject;
import com.google.common.collect.ImmutableList;

public class CloudStorageUtil {
	private Storage client;
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private final String applicationName;
	private static HttpTransport httpTransport;

	public CloudStorageUtil(String applicationName) {
		super();

		this.applicationName = applicationName;
	}

	public void connect(String accountId, File p12File) throws Exception {
		httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		Credential credential = authorize(accountId, p12File);
		client = new Storage.Builder(httpTransport, JSON_FACTORY, credential).setApplicationName(applicationName)
				.build();
	}

	public Credential authorize(String accountId, File p12File) throws Exception, IOException {
		Set<String> scopes = new HashSet<String>();

		scopes.add(StorageScopes.DEVSTORAGE_FULL_CONTROL);
		scopes.add(StorageScopes.DEVSTORAGE_READ_ONLY);
		scopes.add(StorageScopes.DEVSTORAGE_READ_WRITE);

		JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

		HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
				.setJsonFactory(JSON_FACTORY).setServiceAccountId(accountId)
				.setServiceAccountPrivateKeyFromP12File(p12File).setServiceAccountScopes(scopes).build();

		return credential;
	}

	public Bucket getBucket(String bucketName) throws IOException {
		Storage.Buckets.Get getBucket = client.buckets().get(bucketName);
		getBucket.setProjection("full");

		Bucket bucket = getBucket.execute();
		return bucket;
	}

	public List<StorageObject> getBucketFiles(Bucket bucket) throws IOException {
		Storage.Objects.List listObjects = client.objects().list(bucket.getName());
		com.google.api.services.storage.model.Objects objects;
		ArrayList<StorageObject> all = new ArrayList<StorageObject>();

		do {
			objects = listObjects.execute();

			List<StorageObject> items = objects.getItems();
			if (null == items) {
				return all;
			}

			all.addAll(items);
			listObjects.setPageToken(objects.getNextPageToken());
		} while (null != objects.getNextPageToken());

		return all;
	}

	public StorageObject upload(String bucketName, File file, String contentType, String projectId) throws IOException {
		if (client == null) {
			throw new IOException("Clould Storage api is not connected");
		}

		if (file == null || !file.exists() || !file.isFile()) {
			throw new FileNotFoundException("File not found.");
		}

		String fileName = file.getName();

		InputStream inputStream = new FileInputStream(file);
		long byteCount = file.length();

		InputStreamContent mediaContent = new InputStreamContent(contentType, inputStream);
		mediaContent.setLength(byteCount);

		ImmutableList<ObjectAccessControl> acl = ImmutableList.of(
				new ObjectAccessControl().setEntity(String.format("project-owners-%s", projectId)).setRole("OWNER"),

				new ObjectAccessControl().setEntity(String.format("project-editors-%s", projectId)).setRole("OWNER"),

				new ObjectAccessControl().setEntity(String.format("project-viewers-%s", projectId)).setRole("READER")
						.setEntity("allUsers").setRole("READER"));

		StorageObject obj = new StorageObject();
		obj.setName(fileName);
		obj.setContentType(contentType);
		obj.setAcl(acl);

		Storage.Objects.Insert insertObject = client.objects().insert(bucketName, obj, mediaContent);

		if (mediaContent.getLength() > 0 && mediaContent.getLength() <= 2 * 1000 * 1000 /* 2MB */) {
			insertObject.getMediaHttpUploader().setDirectUploadEnabled(true);
		}

		insertObject.execute();
		return obj;
	}
}
