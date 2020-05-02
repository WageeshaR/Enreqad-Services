package com.enreqad.user.controller;

import com.enreqad.user.entity.profile.ProfileEntity;
import com.enreqad.user.kafka.producer.json.SerializableObject;
import com.enreqad.user.util.GlobalConstants;

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

}
