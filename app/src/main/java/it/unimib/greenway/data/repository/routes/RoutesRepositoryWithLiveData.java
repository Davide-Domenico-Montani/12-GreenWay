package it.unimib.greenway.data.repository.routes;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import it.unimib.greenway.data.source.routes.BaseRoutesRemoteDataSource;
import it.unimib.greenway.data.source.routes.RoutesCallBack;
import it.unimib.greenway.model.Result;
import it.unimib.greenway.model.Route;
import it.unimib.greenway.model.RoutesResponse;

public class RoutesRepositoryWithLiveData implements IRoutesRepositoryWithLiveData, RoutesCallBack {
    private BaseRoutesRemoteDataSource routesRemoteDataSource;
    private final MutableLiveData<Result> routesListLiveData;

    public RoutesRepositoryWithLiveData(BaseRoutesRemoteDataSource routesRemoteDataSource) {
        routesListLiveData = new MutableLiveData<>();
        this.routesRemoteDataSource = routesRemoteDataSource;
        routesRemoteDataSource.setRoutesCallBack(this);
    }

    @Override
    public MutableLiveData<Result> fetchRoutes(double latStart, double lonStart, double latEnd, double lonEnd) {
        routesRemoteDataSource.getRoutes(latStart, lonStart, latEnd, lonEnd);
        return routesListLiveData;
    }

    @Override
    public void onSuccessFromRemote(List<Route> Response) {
        Result.RouteResponseSuccess result = new Result.RouteResponseSuccess(new RoutesResponse(Response));
        routesListLiveData.postValue(result);
    }

    @Override
    public void onFailureFromRemote(String message) {

    }
}
