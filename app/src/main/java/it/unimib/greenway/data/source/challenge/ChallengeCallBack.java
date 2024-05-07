package it.unimib.greenway.data.source.challenge;

import java.util.List;

import it.unimib.greenway.model.AirQuality;
import it.unimib.greenway.model.Challenge;

public interface ChallengeCallBack {
    void onSuccessFromRemote(List<Challenge> Response); //Richiesta al back-end OK

    void onFailureFromRemote(String message); //Richiesta al back-end NO

}
