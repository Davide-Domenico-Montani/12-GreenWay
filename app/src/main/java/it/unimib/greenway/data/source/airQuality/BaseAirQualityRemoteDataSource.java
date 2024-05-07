package it.unimib.greenway.data.source.airQuality;

public abstract class BaseAirQualityRemoteDataSource {
    protected AirQualityCallBack airQualityCallBack;

    public void setAirQualityCallBack(AirQualityCallBack airQualityCallBack) {
        this.airQualityCallBack = airQualityCallBack;
    }

    public abstract void getAirQuality();
}
