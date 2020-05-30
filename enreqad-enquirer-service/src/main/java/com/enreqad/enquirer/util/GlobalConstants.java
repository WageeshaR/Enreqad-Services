package com.enreqad.enquirer.util;

public class GlobalConstants {

    public static final String ENQUIRER_SERVICE = "enq";
    public static final String REQUIRER_SERVICE = "req";
    public static final String ADVERTISER_SERVICE = "adv";
    public static final String ADMIN_SERVICE = "adm";
    public static final String USER_SERVICE = "usr";

    public static final String USER_PROFILE_USERID = "userId";
    public static final String USER_PROFILE_USERNAME = "username";
    public static final String USER_PROFILE_FIRSTNAME = "firstName";
    public static final String USER_PROFILE_LASTNAME = "lastName";
    public static final String USER_PROFILE_FULLNAME = "fullName";

    public static final String KAFKA_TOPIC_NEW_USER = "newuser";

    public static final int ENQ_POST_VISIBILITY_PUBLIC = 111;
    public static final int ENQ_POST_VISIBILITY_WITHIN = 222;
    public static final int ENQ_POST_VISIBILITY_PRIVATE = 333;

    public static final int ENQ_POST_PERSIST_SUCCESS = 2000;
    public static final int ENQ_POST_PERSIST_FAILED = 5000;
    public static final int ENQ_POST_PERSIST_FAILED_IMAGES = 3000;

    public static final String SUCCESS_MESSAGE = "Success";
    public static final String FAILED_MESSAGE = "Unsuccessful";

    public static final int SUCCESS = 100;
    public static final int FAILED = 200;
}
