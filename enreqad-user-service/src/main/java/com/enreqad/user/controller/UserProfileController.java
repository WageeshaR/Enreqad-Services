package com.enreqad.user.controller;

import com.enreqad.user.controller.payload.ApiResponse;
import com.enreqad.user.controller.payload.UserProfileRequest;
import com.enreqad.user.entity.profile.ProfileEntity;
import com.enreqad.user.entity.profile.UserRole;
import com.enreqad.user.entity.profile.UserRoleEnum;
import com.enreqad.user.repository.ProfileRepository;
import com.enreqad.user.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("user")
public class UserProfileController {

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @GetMapping("/")
    public ProfileEntity getUserProfile(@RequestHeader("user-id") long userId) throws Exception {

        return profileRepository.findById(userId).orElseThrow( () -> new Exception("Failed to fetch profile data for the user-id: " + userId) );

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
            profileRepository.save(profileEntity);
            return new ResponseEntity(new ApiResponse(true, "Profile saved successfully"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, "Error saving user Profile Data: " + e), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/profile/user-role/{role}")
    public ResponseEntity<?> setUserRole(@PathVariable String role, @RequestHeader("user-id") long userId )
    {
        try {

            ProfileEntity profileEntity = profileRepository.findById(userId).orElseThrow( () -> new Exception("Failed to fetch profile data for the user-id: " + userId) );

            if (role == "enq-user")
            {
                UserRole userRole = userRoleRepository.findByName(UserRoleEnum.ENQ_USER).orElseThrow( () -> new Exception("Failed to set user role " + role + " for the user-id: " + userId));
                profileEntity.setRoles(Collections.singleton(userRole));
            }
            else if (role == "enq-admin")
            {
                UserRole userRole = userRoleRepository.findByName(UserRoleEnum.ENQ_ADMIN).orElseThrow( () -> new Exception("Failed to set user role " + role + " for the user-id: " + userId));
                profileEntity.setRoles(Collections.singleton(userRole));
            }

            if (role == "req-user")
            {
                UserRole userRole = userRoleRepository.findByName(UserRoleEnum.REQ_USER).orElseThrow( () -> new Exception("Failed to set user role " + role + " for the user-id: " + userId));
                profileEntity.setRoles(Collections.singleton(userRole));
            }
            else if (role == "req-admin")
            {
                UserRole userRole = userRoleRepository.findByName(UserRoleEnum.REQ_ADMIN).orElseThrow( () -> new Exception("Failed to set user role " + role + " for the user-id: " + userId));
                profileEntity.setRoles(Collections.singleton(userRole));
            }

            if (role == "adv-user")
            {
                UserRole userRole = userRoleRepository.findByName(UserRoleEnum.ADV_USER).orElseThrow( () -> new Exception("Failed to set user role " + role + " for the user-id: " + userId));
                profileEntity.setRoles(Collections.singleton(userRole));
            }
            else if (role == "adv-admin")
            {
                UserRole userRole = userRoleRepository.findByName(UserRoleEnum.ADV_ADMIN).orElseThrow( () -> new Exception("Failed to set user role " + role + " for the user-id: " + userId));
                profileEntity.setRoles(Collections.singleton(userRole));
            }

            return new ResponseEntity(new ApiResponse(true, "User role set successfully"), HttpStatus.OK);

        } catch (Exception e) {

            e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, "Failed to assign user role to user with id: " + userId ), HttpStatus.BAD_REQUEST);

        }
    }
}
