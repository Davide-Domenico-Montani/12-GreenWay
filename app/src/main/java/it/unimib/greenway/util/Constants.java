package it.unimib.greenway.util;

public class Constants {

    public static final String USER_DATABASE_REFERENCE = "user";
    public static final String WEAK_PASSWORD_ERROR = "weak_password";
    public static final String INVALID_CREDENTIALS_ERROR = "invalid_credentials";
    public static final String INVALID_USER_ERROR = "invalid_user";
    public static final String USER_COLLISION_ERROR = "user_collision";
    public static final String UNEXPECTED_ERROR = "unexpected_error";
    public static final String ENCRYPTED_SHARED_PREFERENCES_FILE_NAME = "it.unimib.greenway.encrypted_preferences";
    public static final String SHARED_PREFERENCES_FIRST_LOADING = "first_loading";
    public static final String EMAIL_ADDRESS = "email";
    public static final String PASSWORD = "password";
    public static final String ID = "id";
    public static final String SHARED_PREFERENCES_FILE_NAME = "it.unimib.greenway.preferences";
    public static final String ENCRYPTED_DATA_FILE_NAME = "it.unimib.greenway.encrypted_file.txt";
    public static final boolean USE_NAVIGATION_COMPONENT = true;
    public static final int DATABASE_VERSION = 1;

    public static final String AIRQUALITY_DATABASE_NAME = "AirQDb";
    public static final String LAST_UPDATE = "last_update";

    public static final String DRIVE_CONSTANT = "DRIVE";
    public static final String TRANSIT_CONSTANT = "TRANSIT";
    public static final String WALK_CONSTANT = "WALK";

    public static final String FIELDMASK_ROUTE = "routes.duration,routes.distanceMeters,routes.polyline.encodedPolyline";

    public static final String DEPARTURE_TIME_CONSTANT = "  \"departureTime\": \"2024-10-23T15:01:23.045123456Z\",\n";
    public static final String ROUTING_PREFERENCE_CONSTANT = " \"routingPreference\": \"TRAFFIC_AWARE\",\n";


    public static final String TRANSIT_CONSTANT_PREFERENCES = "\"transitPreferences\": {\n" +
            "\"routingPreference\": \"LESS_WALKING\", \n" +
            "\"allowedTravelModes\": [\"BUS\", \"SUBWAY\", \"TRAIN\", \"LIGHT_RAIL\", \"RAIL\"]},";




}
