package com.chahat.gcs_image_upload.filestore;

import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@Service
public class FileStore {

    @Autowired
    private final Storage storage;

    public FileStore() {
        this.storage = StorageOptions.getDefaultInstance().getService();
    }

    public void save(String bucketName, String fileName, Optional<Map<String, String>> optionalMetadata, InputStream inputStream) throws StorageException {
        BlobId blobID = BlobId.of(bucketName, fileName);
        BlobInfo.Builder blobInfoBuilder = BlobInfo.newBuilder(blobID);

        optionalMetadata.ifPresent(map -> {
            if (!map.isEmpty()){
                blobInfoBuilder.setMetadata(map);
        }
        });

        BlobInfo blobInfo = blobInfoBuilder.build();

        try {
            storage.create(blobInfo, inputStream);
        } catch (StorageException e) {
            throw new IllegalStateException("Failed to store file in the storage", e);
        }
    }

    public byte[] download(String bucketName, String fileName) throws StorageException {
        System.out.println(bucketName + "/" + fileName);
        Blob blob = storage.get(BlobId.of(bucketName, fileName));

        if (blob == null) {
            throw new IllegalStateException("File not found");
        }
        try {
            return blob.getContent();
        } catch (StorageException e){
            throw new IllegalStateException("Failed to download file", e);
        }
    }
}
