package com.chahat.gcs_image_upload.profile;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("gcp/user-profile")
@CrossOrigin("*")
public class UserProfileController {

    private final UserProfileService userProfileService;
    @Autowired
    private final UserProfileRepository userProfileRepository;

    public UserProfileController(final UserProfileService userProfileService, final UserProfileRepository userProfileRepository) {
        this.userProfileService = userProfileService;
        this.userProfileRepository = userProfileRepository;
    }

    @GetMapping
    public List<UserProfile> getUserProfile(){
        return userProfileService.getUserProfiles();
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path = "{userProfileID}/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)

    public void uploadUserProfileImage(@PathVariable("userProfileID") int userProfileID, @RequestParam("file") MultipartFile file){
        userProfileService.uploadUserProfileImage(userProfileID, file);
    }

    @GetMapping("{userProfileID}/image/download")
    public byte[] downloadUserProfileImage(@PathVariable("userProfileID") int userProfileID){
        return userProfileService.downloadUserProfileImage(userProfileID);
    }

    @PostMapping("addUser")
    public String addUser(@RequestBody UserProfile userProfile){
        userProfileRepository.save(userProfile);
        return "Successfully added user profile";
    }
}
