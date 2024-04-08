package it.unimib.greenway.util;

import android.app.Application;

import it.unimib.greenway.data.repository.user.IUserRepository;
import it.unimib.greenway.data.repository.user.UserRepository;
import it.unimib.greenway.data.service.AirQualityApiService;
import it.unimib.greenway.data.source.user.BaseUserAuthenticationRemoteDataSource;
import it.unimib.greenway.data.source.user.BaseUserDataRemoteDataSource;
import it.unimib.greenway.data.source.user.UserAuthenticationRemoteDataSource;
import it.unimib.greenway.data.source.user.UserDataRemoteDataSource;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceLocator {
    private static volatile ServiceLocator INSTANCE = null;

    private ServiceLocator() {}

    /**
     * Returns an instance of ServiceLocator class.
     * @return An instance of ServiceLocator.
     */
    public static ServiceLocator getInstance() {
        if (INSTANCE == null) {
            synchronized(ServiceLocator.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ServiceLocator();
                }
            }
        }
        return INSTANCE;
    }

    public IUserRepository getUserRepository(Application application) {
        //SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(application);

        BaseUserAuthenticationRemoteDataSource userRemoteAuthenticationDataSource =
                new UserAuthenticationRemoteDataSource();


        BaseUserDataRemoteDataSource userDataRemoteDataSource =
                new UserDataRemoteDataSource();
        /*DataEncryptionUtil dataEncryptionUtil = new DataEncryptionUtil(application);

        BaseBeerLocalDataSource beerLocalDataSource =
                new BeerLocalDataSource(getBeerDao(application), sharedPreferencesUtil, dataEncryptionUtil);

*/
        return new UserRepository(userRemoteAuthenticationDataSource, userDataRemoteDataSource//,
                /*beerLocalDataSource, userDataRemoteDataSource*/);
    }

    public AirQualityApiService getBeerApiService() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://airquality.googleapis.com/v1/mapTypes/" + "US_AQI" + "/heatmapTiles/" + "2" + "/" + "0" + "/" + "1" + "?key=" + "AIzaSyBqYE0984H0veT8WIyDLXudEnBhO1RW_MY").
                addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(AirQualityApiService.class);
    }

}
