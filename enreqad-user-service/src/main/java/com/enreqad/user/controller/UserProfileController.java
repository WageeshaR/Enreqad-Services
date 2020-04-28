package com.enreqad.user.controller;

import com.enreqad.user.controller.payload.ApiResponse;
import com.enreqad.user.controller.payload.UserProfileRequest;
import com.enreqad.user.entity.ProfileEntity;
import com.enreqad.user.entity.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserProfileController {

    @Autowired
    ProfileRepository userRepository;

    @GetMapping("/")
    public ProfileEntity getUserProfile(@RequestHeader("user-id") long userId) throws Exception {
        return userRepository.findById(userId).orElseThrow( () -> new Exception("Failed to retrieve User Profile data") );
    }

    @PostMapping("/profile")
    public ResponseEntity<?> createUserProfile(@RequestHeader("user-id") long userId, @RequestBody UserProfileRequest userProfileRequest)
    {
        try {
            ProfileEntity profileEntity = new ProfileEntity(
                    userId,
                    userProfileRequest.getFirstName(),
                    userProfileRequest.getLastName(),
                    userProfileRequest.getUsername(),
                    userProfileRequest.getBirthday(),
                    userProfileRequest.getGender(),
                    userProfileRequest.getResCountry(),
                    userProfileRequest.getOprCountry(),
                    userProfileRequest.getDisplay_picture()
            );
            userRepository.save(profileEntity);
            return new ResponseEntity(new ApiResponse(true, "Profile saved successfully"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, "Error saving user Profile Data: " + e), HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
