package com.chahat.gcs_image_upload.profile;

import com.chahat.gcs_image_upload.filestore.FileStore;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.*;

@Service
public class UserProfileService {

    private final String bucketName = "chahat_bucket";

    @Autowired
    private final UserProfileDataAccessService userProfileDataAccessService;
    private final FileStore fileStore;

    public UserProfileService(final UserProfileDataAccessService userProfileDataAccessService, FileStore fileStore) {
        this.userProfileDataAccessService = userProfileDataAccessService;
        this.fileStore = fileStore;
    }

    public List<UserProfile> getUserProfiles() {
        return userProfileDataAccessService.getUserProfiles();
    }

    public void uploadUserProfileImage(int userProfileID, MultipartFile file) {
        isFileEmpty(file);

        isEmpty(file);

        UserProfile user = getUserProfile(userProfileID);

        Map<String, String> metadata = extractMetadata(file);

        String fileName = String.format("%s/%s",user.getUsername(), file.getOriginalFilename());

        try {
            fileStore.save(bucketName, fileName, Optional.of(metadata), file.getInputStream());
            user.setUserProfileImageLink(fileName);
            userProfileDataAccessService.saveUserProfile(user);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public byte[] downloadUserProfileImage(int userProfileID) {
        UserProfile user = getUserProfile(userProfileID);
        return fileStore.download(bucketName, user.getUserProfileImageLink());
    }

    private Map<String, String> extractMetadata(MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Size", String.valueOf(file.getSize()));
        return metadata;
    }

    private UserProfile getUserProfile(int userProfileID) {
        return userProfileDataAccessService.getUserProfile(userProfileID);
    }

    private void isEmpty(MultipartFile file) {
        if (!Arrays.asList(ContentType.IMAGE_JPEG.getMimeType(), ContentType.IMAGE_GIF.getMimeType())
                .contains(file.getContentType())) {
            throw new IllegalStateException("Invalid image type [" + file.getContentType() + "]");
        }
    }

    private void isFileEmpty(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file [ " + file.getSize() + " ] ");
        }
    }
}
