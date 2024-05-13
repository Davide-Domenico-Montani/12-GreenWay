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

    public static final String FIELDMASK_ROUTE = "routes.legs.steps.transitDetails.transitLine.vehicle.type," +
            "routes.staticDuration,routes.legs.steps.distanceMeters,routes.distanceMeters," +
            "routes.polyline,routes.legs.steps.staticDuration";

    //valori produzione co2 per passeggero
    public static final double CO2_PRODUCTION_CAR_GASOLINE = 150;
    public static final double CO2_PRODUCTION_CAR_DIESEL = 120;
    public static final double CO2_PRODUCTION_CAR_GPL = 100;
    public static final double CO2_PRODUCTION_CAR_METHANE = 80;
    public static final double CO2_PRODUCTION_CAR_ELETTRIC= 0;

    public static final double CO2_PRODUCTION_BUS = 35;
    public static final double CO2_PRODUCTION_TRAM = 15;
    public static final double CO2_PRODUCTION_TRAIN = 25;
    public static final double CO2_PRODUCTION_METRO = 20;

    public static final String URI_STRING_MAPS = "https://www.google.com/maps/dir/?api=1&travelmode=driving&origin=";

    public static final String CHANNEL_ID = "greenway_channel";
    public static final String CHANNEL_NAME = "GreenWay channel";
    public static final String CHANNEL_DESCRIPTION = "channel for GreenWay notifications about co2";
    public static final String CAR_PARAMETER_DATABASE = "co2SavedCar";
    public static final String TRANSIT_PARAMETER_DATABASE = "co2SavedTransit";
    public static final String WALK_PARAMETER_DATABASE = "co2SavedWalk";
    public static final String DATABASE_URL = "https://greenway-f23bb-default-rtdb.firebaseio.com/";

    public static final String CARKM_PARAMETER_DATABASE = "kmCar";
    public static final String TRANSITKM_PARAMETER_DATABASE = "kmTransit";
    public static final String WALKKM_PARAMETER_DATABASE = "kmWalk";
    public static final String PASSWORD_DATABASE_REFERENCE = "password";
    public static final String CO2_CAR_PARAMETER_DATABASE = "co2Car";

    public static final String NEW_PASSWORD_ERROR = "new_password_error";

    public static final String OLD_PASSWORD_ERROR = "old_password_error";

    public static final String PASSWORD_ERROR_GOOGLE = "google_password_error";
    public static final String PHOTOURL_DATABASE_REFERENCE = "photoUrl";

    public static final String ERROR_RETRIEVING_USER_INFO = "error_retrieving_user_info";
    public static final String ERROR_RETRIEVING_ROUTES = "error_retrieving_routes";
    public static final String ERROR_RETRIEVING_CHALLENGE = "error_retrieving_challenge";
    public static final String CHALLENGE_DATABASE_REFERENCE = "challenge";
    public static final String STATUS_CHALLENGE_DATABASE_REFERENCE = "statusChallengeList";
    public static final String POINT_DATABASE_REFERENCE = "point";
    public static final String ERROR_LOGIN = "login_error";
    public static final String ADDING_FRIEND_ERROR = "adding_friend_error";
    public static final String FRIEND_DATABASE_REFERENCE = "idFriends";


}
