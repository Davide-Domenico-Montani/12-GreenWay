package it.unimib.greenway.data.source.airQuality;

import static it.unimib.greenway.util.Constants.LAST_UPDATE;
import static it.unimib.greenway.util.Constants.SHARED_PREFERENCES_FILE_NAME;

import java.util.List;
import java.util.concurrent.ExecutionException;

import it.unimib.greenway.data.database.AirQualityDao;
import it.unimib.greenway.data.database.AirQualityDatabase;
import it.unimib.greenway.model.AirQuality;
import it.unimib.greenway.util.SharedPreferencesUtil;

public class AirQualityLocalDataSource extends BaseAirQualityLocalDataSource{
    private final AirQualityDao airQualityDao;
    private final SharedPreferencesUtil sharedPreferencesUtil;
    protected AirQualityCallBack airQualityCallBack;


    public AirQualityLocalDataSource(AirQualityDatabase airQualityDatabase,
                                     SharedPreferencesUtil sharedPreferencesUtil) {
        this.airQualityDao = airQualityDatabase.airQualityDao();
        this.sharedPreferencesUtil = sharedPreferencesUtil;
    }
    @Override
    public void insertAirQuality(List<AirQuality> response) {
        AirQualityDatabase.databaseWriteExecutor.execute(() -> {
            for(AirQuality airQuality : response) {
                airQualityDao.insertAirQuality(airQuality);
            }
            sharedPreferencesUtil.writeStringData(SHARED_PREFERENCES_FILE_NAME,
                    LAST_UPDATE, String.valueOf(System.currentTimeMillis()));
        });
    }

    @Override
    public void getAirQuality() {
        AirQualityDatabase.databaseWriteExecutor.execute(() -> {
            List<AirQuality> airQualityList = airQualityDao.getAll();
        });
    }

    @Override
    public List<AirQuality> getAirQualityList() throws ExecutionException, InterruptedException {
        return AirQualityDatabase.databaseWriteExecutor.submit(() -> {
            return airQualityDao.getAll();
        }).get();
    }
}
