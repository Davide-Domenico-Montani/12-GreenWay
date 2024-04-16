package it.unimib.greenway.data.repository.airQuality;

import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import it.unimib.greenway.model.AirQuality;
import it.unimib.greenway.model.Result;

public interface IAirQualityRepositoryWithLiveData {
    MutableLiveData<Result> fetchAllAirQUality(long lastUpdate);
    List<AirQuality> getAirQualityList() throws ExecutionException, InterruptedException;

}
