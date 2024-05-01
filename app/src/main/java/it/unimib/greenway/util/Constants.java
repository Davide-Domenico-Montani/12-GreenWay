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
    public static final double CO2_PRODUCTION_ELECTRIC = 0;

    public static final double CO2_PRODUCTION_BUS = 35;
    public static final double CO2_PRODUCTION_TRAM = 15;
    public static final double CO2_PRODUCTION_TRAIN = 25;
    public static final double CO2_PRODUCTION_METRO = 20;

    public static final String URI_STRING_MAPS = "https://www.google.com/maps/dir/?api=1&travelmode=driving&origin=";

    public static final String CHANNEL_ID = "greenway_channel";
    public static final String CHANNEL_NAME = "GreenWay channel";
    public static final String CHANNEL_DESCRIPTION = "channel for GreenWay notifications about co2";



}
