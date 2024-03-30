package it.unimib.greenway.util;

import android.app.Application;

import it.unimib.greenway.data.repository.user.IUserRepository;
import it.unimib.greenway.data.repository.user.UserRepository;
import it.unimib.greenway.data.source.user.BaseUserAuthenticationRemoteDataSource;
import it.unimib.greenway.data.source.user.UserAuthenticationRemoteDataSource;

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

        /*BaseUserDataRemoteDataSource userDataRemoteDataSource =
                new UserDataRemoteDataSource(sharedPreferencesUtil);
        DataEncryptionUtil dataEncryptionUtil = new DataEncryptionUtil(application);

        BaseBeerLocalDataSource beerLocalDataSource =
                new BeerLocalDataSource(getBeerDao(application), sharedPreferencesUtil, dataEncryptionUtil);

*/
        return new UserRepository(userRemoteAuthenticationDataSource//,
                /*beerLocalDataSource, userDataRemoteDataSource*/);
    }

}
