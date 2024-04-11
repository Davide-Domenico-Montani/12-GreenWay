package it.unimib.greenway.data.source.airQuality;

import it.unimib.greenway.data.database.AirQualityDao;
import it.unimib.greenway.data.database.AirQualityDatabase;
import it.unimib.greenway.model.AirQuality;
import it.unimib.greenway.util.SharedPreferencesUtil;

public class AirQualityLocalDataSource extends BaseAirQualityLocalDataSource{
    private final AirQualityDao airQualityDao;
    private final SharedPreferencesUtil sharedPreferencesUtil;


    public AirQualityLocalDataSource(AirQualityDatabase airQualityDatabase,
                                     SharedPreferencesUtil sharedPreferencesUtil) {
        this.airQualityDao = airQualityDatabase.airQualityDao();
        this.sharedPreferencesUtil = sharedPreferencesUtil;
    }
    @Override
    public void insertAirQuality(AirQuality airQuality) {
        AirQualityDatabase.databaseWriteExecutor.execute(() -> {
            airQualityDao.insertAirQuality(airQuality);
        });
    }

    @Override
    public AirQuality getAirQuality(String id) {
        return airQualityDao.getAirQuality(id);
    }
}
