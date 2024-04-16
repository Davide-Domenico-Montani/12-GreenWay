package it.unimib.greenway.data.source.airQuality;

import java.util.List;

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
            //airQualityCallBack.onSuccessFromLocal(response);
        });
    }

    @Override
    public AirQuality getAirQuality(String id) {
        return airQualityDao.getAirQuality(id);
    }
}
