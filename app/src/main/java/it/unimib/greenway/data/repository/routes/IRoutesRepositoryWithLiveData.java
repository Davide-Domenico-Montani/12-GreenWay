package it.unimib.greenway.data.repository.routes;

import androidx.lifecycle.MutableLiveData;

import it.unimib.greenway.model.Result;

public interface IRoutesRepositoryWithLiveData {
    MutableLiveData<Result> fetchRoutes(double latStart, double lonStart, double latEnd, double lonEnd);

}
