package it.unimib.greenway.data.source.routes;

public abstract class BaseRoutesRemoteDataSource {

    protected RoutesCallBack routesCallBack;

    public void setRoutesCallBack(RoutesCallBack routesCallBack) {
        this.routesCallBack = routesCallBack;
    }

    public abstract void getRoutes(double latStart, double lonStart, double latEnd, double lonEnd, double co2Car); //Ottiene birre da back-end
}
