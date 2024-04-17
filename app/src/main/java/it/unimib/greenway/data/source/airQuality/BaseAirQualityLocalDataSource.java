package it.unimib.greenway.data.source.airQuality;

import java.util.List;
import java.util.concurrent.ExecutionException;

import it.unimib.greenway.model.AirQuality;

public abstract class BaseAirQualityLocalDataSource {
    protected AirQualityCallBack airQualityCallBack;

    public void setAirQualityCallBack(AirQualityCallBack airQualityCallBack) {
        this.airQualityCallBack = airQualityCallBack;
    }
    public abstract void insertAirQuality(List<AirQuality> airQuality);
    public abstract void getAirQuality();

    public abstract List<AirQuality> getAirQualityList() throws ExecutionException, InterruptedException;

}
