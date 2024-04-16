package it.unimib.greenway.data.repository.airQuality;

import androidx.lifecycle.MutableLiveData;

import it.unimib.greenway.model.Result;

public interface IAirQualityRepositoryWithLiveData {
    MutableLiveData<Result> fetchAllAirQUality(long lastUpdate);

}
