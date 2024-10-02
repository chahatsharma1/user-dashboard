package com.chahat.gcs_image_upload.profile;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userProfileID;

    private String username;
    private String userProfileImageLink;

    public UserProfile() {}

    public UserProfile(int userProfileID, String username, String userProfileImageLink) {
        this.userProfileID = userProfileID;
        this.username = username;
        this.userProfileImageLink = userProfileImageLink;
    }

    public int getuserProfileID() {
        return userProfileID;
    }

    public String getUsername() {
        return username;
    }

    public String getUserProfileImageLink() {
        return userProfileImageLink;
    }

    public void setUserProfileImageLink(String userProfileImageLink) {
        this.userProfileImageLink = userProfileImageLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserProfile that = (UserProfile) o;
        return  Objects.equals(userProfileID, that.userProfileID) &&
                Objects.equals(username, that.username) &&
                Objects.equals(userProfileImageLink, that.userProfileImageLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userProfileID, username, userProfileImageLink);
    }
}
