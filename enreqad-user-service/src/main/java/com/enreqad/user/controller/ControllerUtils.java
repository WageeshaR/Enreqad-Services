package com.enreqad.user.controller;

import com.enreqad.user.controller.payload.ApiResponse;
import com.enreqad.user.entity.profile.ProfileEntity;
import com.enreqad.user.entity.profile.UserRole;
import com.enreqad.user.entity.profile.UserRoleEnum;
import com.enreqad.user.kafka.producer.json.SerializableObject;
import com.enreqad.user.repository.UserRoleRepository;
import com.enreqad.user.util.GlobalConstants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;

public class ControllerUtils {

    public static SerializableObject populateForNewUser( ProfileEntity profileEntity, String enableProfile )
    {
        SerializableObject serializableObject = new SerializableObject();
        HashMap<String, String> data = new HashMap();
        serializableObject.setMessage( enableProfile );
        data.put( GlobalConstants.USER_PROFILE_USERID, String.valueOf(profileEntity.getUserId()) );
        data.put( GlobalConstants.USER_PROFILE_USERNAME, profileEntity.getUsername() );
        data.put( GlobalConstants.USER_PROFILE_FULLNAME, profileEntity.getFirstName() + " " + profileEntity.getLastName() );
        serializableObject.setDataMap( data );
        return serializableObject;
    }

    public static boolean validateAndSetRole(ProfileEntity profileEntity, String role, String profile, UserRoleRepository repository, UserRoleEnum userEnum, UserRoleEnum adminEnum ) throws Exception
    {
        UserRole user = repository.findByName(userEnum).orElseThrow( () -> new Exception("User role " + userEnum + " is not set"));
        UserRole admin = repository.findByName(adminEnum).orElseThrow( () -> new Exception("User role " + adminEnum + " is not set"));
        if (role.equals(profile + "-user"))
        {
            if (profileEntity.getRoles().contains(user)) return true;
            if (profileEntity.getRoles().contains(admin)) profileEntity.getRoles().remove(admin);
            profileEntity.getRoles().add(user);
        }
        if (role.equals(profile + "-admin"))
        {
            if (profileEntity.getRoles().contains(admin)) return true;
            if (profileEntity.getRoles().contains(user)) profileEntity.getRoles().remove(user);
            profileEntity.getRoles().add(admin);
        }
        return true;
    }

}
