package it.unimib.greenway.data.source.airQuality;

import java.util.List;

import it.unimib.greenway.model.AirQuality;

public interface AirQualityCallBack {
    void onSuccessFromLocal(List<AirQuality> Response); //Lettura/scrittura DB OK

    void onFailureFromLocal(Exception exception); //Lettura/scrittura DB NO

    void onSuccessFromRemote(List<AirQuality> Response); //Richiesta al back-end OK

    void onFailureFromRemote(Exception exception); //Richiesta al back-end NO

}
