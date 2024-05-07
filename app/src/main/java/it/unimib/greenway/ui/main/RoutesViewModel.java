package it.unimib.greenway.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import it.unimib.greenway.data.repository.routes.IRoutesRepositoryWithLiveData;
import it.unimib.greenway.model.Result;

public class RoutesViewModel extends ViewModel {
    private static final String TAG = AirQualityViewModel.class.getSimpleName();

    private final IRoutesRepositoryWithLiveData routesRepositoryWithLiveData;
    private MutableLiveData<Result> routesListLiveData;

    public RoutesViewModel(IRoutesRepositoryWithLiveData routesRepositoryWithLiveData) {
        this.routesRepositoryWithLiveData = routesRepositoryWithLiveData;
    }

    public MutableLiveData<Result> getRoutes(double latSta, double lonSta, double latEnd, double lonEnd, double co2Car) {
            fetchRoutes(latSta, lonSta, latEnd, lonEnd, co2Car);
        return routesListLiveData;
    }

    public void fetchRoutes(double latSta, double lonSta, double latEnd, double lonEnd, double co2Car) {
        routesListLiveData = routesRepositoryWithLiveData.fetchRoutes(latSta, lonSta, latEnd, lonEnd, co2Car);
    }


}
