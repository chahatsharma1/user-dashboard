package com.chahat.gcs_image_upload.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class GoogleConfig {

    private static final String CREDENTIALS_FILE_PATH = "D:/Project/GCS Image Upload/gcs-image-upload/src/main/resources/gcp-file.json";

    @Bean
    public Storage getStorage() throws IOException {
        GoogleCredentials credentials =
                GoogleCredentials.fromStream(new FileInputStream(CREDENTIALS_FILE_PATH));
        return StorageOptions.newBuilder().setCredentials(credentials).build().getService();
    }
}
