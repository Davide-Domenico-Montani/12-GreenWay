package it.unimib.greenway.data.source.airQuality;

import android.view.View;

public abstract class BaseAirQualityRemoteDataSource {
    protected AirQualityCallBack airQualityCallBack;

    public void setAirQualityCallBack(AirQualityCallBack airQualityCallBack) {
        this.airQualityCallBack = airQualityCallBack;
    }

    public abstract void getAirQuality(View view);

}
