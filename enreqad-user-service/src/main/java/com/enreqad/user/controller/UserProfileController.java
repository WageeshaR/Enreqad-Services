package com.enreqad.user.controller;

import com.enreqad.user.controller.payload.ApiResponse;
import com.enreqad.user.controller.payload.UserProfileRequest;
import com.enreqad.user.entity.profile.ProfileEntity;
import com.enreqad.user.entity.profile.UserRole;
import com.enreqad.user.entity.profile.UserRoleEnum;
import com.enreqad.user.kafka.producer.Producer;
import com.enreqad.user.repository.ProfileRepository;
import com.enreqad.user.repository.UserRoleRepository;
import com.enreqad.user.util.GlobalConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserProfileController {

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    Producer producer;

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

            if (role.contains(GlobalConstants.ENQUIRER_SERVICE)){
                UserRole enqUser = userRoleRepository.findByName(UserRoleEnum.ENQ_USER).orElseThrow( () -> new Exception("User role " + UserRoleEnum.ENQ_USER + " is not set"));
                UserRole enqAdmin = userRoleRepository.findByName(UserRoleEnum.ENQ_ADMIN).orElseThrow( () -> new Exception("User role " + UserRoleEnum.ENQ_ADMIN + " is not set"));
                if (role.equals("enq-user"))
                {
                    if (profileEntity.getRoles().contains(enqAdmin)) profileEntity.getRoles().remove(enqAdmin);
                    profileEntity.getRoles().add(enqUser);
                }
                if (role.equals("enq-admin"))
                {
                    if (profileEntity.getRoles().contains(enqUser)) profileEntity.getRoles().remove(enqUser);
                    profileEntity.getRoles().add(enqAdmin);
                }
            }

            if (role.contains(GlobalConstants.REQUIRER_SERVICE)){
                UserRole reqUser = userRoleRepository.findByName(UserRoleEnum.REQ_USER).orElseThrow( () -> new Exception("User role " + UserRoleEnum.REQ_USER + " is not set"));
                UserRole reqAdmin = userRoleRepository.findByName(UserRoleEnum.REQ_ADMIN).orElseThrow( () -> new Exception("User role " + UserRoleEnum.REQ_ADMIN + " is not set"));
                if (role.equals("req-user"))
                {
                    if (profileEntity.getRoles().contains(reqAdmin)) profileEntity.getRoles().remove(reqAdmin);
                    profileEntity.getRoles().add(reqUser);
                }
                if (role.equals("req-admin"))
                {
                    if (profileEntity.getRoles().contains(reqUser)) profileEntity.getRoles().remove(reqUser);
                    profileEntity.getRoles().add(reqAdmin);
                }
            }

            if (role.contains(GlobalConstants.ADVERTISER_SERVICE)){
                UserRole advUser = userRoleRepository.findByName(UserRoleEnum.ADV_USER).orElseThrow( () -> new Exception("User role " + UserRoleEnum.ADV_USER + " is not set"));
                UserRole advAdmin = userRoleRepository.findByName(UserRoleEnum.ADV_ADMIN).orElseThrow( () -> new Exception("User role " + UserRoleEnum.ADV_ADMIN + " is not set"));
                if (role.equals("adv-user"))
                {
                    if (profileEntity.getRoles().contains(advAdmin)) profileEntity.getRoles().remove(advAdmin);
                    profileEntity.getRoles().add(advUser);
                }
                if (role.equals("adv-admin"))
                {
                    if (profileEntity.getRoles().contains(advUser)) profileEntity.getRoles().remove(advUser);
                    profileEntity.getRoles().add(advAdmin);
                }
            }

            profileRepository.flush();

            return new ResponseEntity(new ApiResponse(true, "User role set successfully"), HttpStatus.OK);

        } catch (Exception e) {

            e.printStackTrace();
            return new ResponseEntity(new ApiResponse(false, "Failed to assign user role to user with id: " + userId ), HttpStatus.BAD_REQUEST);

        }
    }

    @PostMapping("/profile/enable")
    public ResponseEntity<?> enableProfile( @RequestHeader("user-id") long userId, @RequestParam("profile") String enableProfile, @RequestParam("val") boolean val )
    {
        try {

            ProfileEntity profileEntity = profileRepository.findById(userId).orElseThrow( () -> new Exception("Failed to fetch profile data for the user-id: " + userId));

            if (enableProfile.equals(GlobalConstants.ENQUIRER_SERVICE)) profileEntity.setEnableEnq( val ? true : false );
            else if (enableProfile.equals(GlobalConstants.REQUIRER_SERVICE)) profileEntity.setEnableReq( val ? true : false );
            else if (enableProfile.equals(GlobalConstants.ADVERTISER_SERVICE)) profileEntity.setEnableAdv( val ? true : false );
            else return new ResponseEntity( new ApiResponse(false, "Wrong profile type"), HttpStatus.BAD_REQUEST);
            producer.sendMessage( ControllerUtils.populateForNewUser(profileEntity, enableProfile) );
            profileRepository.flush();

            return new ResponseEntity(new ApiResponse(true, "Profile enabled successfully"), HttpStatus.OK);

        } catch ( Exception e )
        {
            e.printStackTrace();
            return new ResponseEntity( new ApiResponse(false, "Failed to enable profile for user-id: " + userId), HttpStatus.BAD_REQUEST );
        }
    }
}
