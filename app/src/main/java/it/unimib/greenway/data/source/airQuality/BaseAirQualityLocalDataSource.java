package it.unimib.greenway.data.source.airQuality;

import java.util.List;

import it.unimib.greenway.model.AirQuality;

public abstract class BaseAirQualityLocalDataSource {
    protected AirQualityCallBack airQualityCallBack;

    public void setAirQualityCallBack(AirQualityCallBack airQualityCallBack) {
        this.airQualityCallBack = airQualityCallBack;
    }
    public abstract void insertAirQuality(List<AirQuality> airQuality);
    public abstract void getAirQuality();

}
