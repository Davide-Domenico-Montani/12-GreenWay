package it.unimib.greenway.data.source.airQuality;

import it.unimib.greenway.model.AirQuality;

public abstract class BaseAirQualityLocalDataSource {
    public abstract void insertAirQuality(AirQuality airQuality);
    public abstract AirQuality getAirQuality(String id);

}
