package com.chahat.gcs_image_upload.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UserProfileDataAccessService {

    @Autowired
    public final UserProfileRepository userProfileRepository;

    public UserProfileDataAccessService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public List<UserProfile> getUserProfiles() {
        return userProfileRepository.findAll();
    }

    public void saveUserProfile(UserProfile userProfile) {
        userProfileRepository.save(userProfile);
    }

    public UserProfile getUserProfile(int id) {
        return userProfileRepository.findById(id).orElse(null);
    }
}
