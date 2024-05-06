package it.unimib.greenway.data.source.routes;

import java.util.List;

import it.unimib.greenway.model.AirQuality;
import it.unimib.greenway.model.Route;

public interface RoutesCallBack {
    void onSuccessFromRemote(List<Route> Response); //Richiesta al back-end OK

    void onFailureFromRemote(String message); //Richiesta al back-end NO

}
