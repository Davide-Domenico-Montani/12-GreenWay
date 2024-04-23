package it.unimib.greenway.util;

import android.app.Application;

import it.unimib.greenway.data.database.AirQualityDatabase;
import it.unimib.greenway.data.repository.airQuality.AirQualityRepositoryWithLiveData;
import it.unimib.greenway.data.repository.airQuality.IAirQualityRepositoryWithLiveData;
import it.unimib.greenway.data.repository.routes.IRoutesRepositoryWithLiveData;
import it.unimib.greenway.data.repository.routes.RoutesRepositoryWithLiveData;
import it.unimib.greenway.data.repository.user.IUserRepository;
import it.unimib.greenway.data.repository.user.UserRepository;
import it.unimib.greenway.data.service.AirQualityApiService;
import it.unimib.greenway.data.service.RoutesApiService;
import it.unimib.greenway.data.source.airQuality.AirQualityLocalDataSource;
import it.unimib.greenway.data.source.airQuality.AirQualityRemoteDataSource;
import it.unimib.greenway.data.source.airQuality.BaseAirQualityLocalDataSource;
import it.unimib.greenway.data.source.airQuality.BaseAirQualityRemoteDataSource;
import it.unimib.greenway.data.source.routes.BaseRoutesRemoteDataSource;
import it.unimib.greenway.data.source.routes.RoutesRemoteDataSource;
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
        BaseAirQualityLocalDataSource airQualityLocalDataSource;
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(application);

        BaseUserAuthenticationRemoteDataSource userRemoteAuthenticationDataSource =
                new UserAuthenticationRemoteDataSource();


        BaseUserDataRemoteDataSource userDataRemoteDataSource =
                new UserDataRemoteDataSource();
        /*DataEncryptionUtil dataEncryptionUtil = new DataEncryptionUtil(application);
*/
         airQualityLocalDataSource =
                new AirQualityLocalDataSource(getAirQualityDao(application), sharedPreferencesUtil);


        return new UserRepository(userRemoteAuthenticationDataSource, userDataRemoteDataSource,
                airQualityLocalDataSource /*userDataRemoteDataSource*/);
    }



    public AirQualityDatabase getAirQualityDao(Application application) {
        return AirQualityDatabase.getDatabase(application);
    }

    public IAirQualityRepositoryWithLiveData getAirQualityRepository(Application application){
        BaseAirQualityLocalDataSource airQualityLocalDataSource;
        BaseAirQualityRemoteDataSource airQualityRemoteDataSource;


        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(application);

        airQualityRemoteDataSource =
                new AirQualityRemoteDataSource();


        airQualityLocalDataSource =
                new AirQualityLocalDataSource(getAirQualityDao(application), sharedPreferencesUtil);

        return new AirQualityRepositoryWithLiveData(airQualityLocalDataSource, airQualityRemoteDataSource);

    }


    public IRoutesRepositoryWithLiveData getRoutesRepository(Application application){
        BaseRoutesRemoteDataSource routesRemoteDataSource;

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(application);

        routesRemoteDataSource =
                new RoutesRemoteDataSource();



        return new RoutesRepositoryWithLiveData(routesRemoteDataSource);

    }


    public AirQualityApiService getAirQualityApiService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://airquality.googleapis.com/")
                .build();
        return retrofit.create(AirQualityApiService.class);
    }

    public RoutesApiService getRoutesApiService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://routes.googleapis.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(RoutesApiService.class);
    }


}
