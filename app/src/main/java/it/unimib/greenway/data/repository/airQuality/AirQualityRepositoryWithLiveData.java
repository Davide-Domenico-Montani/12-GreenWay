package it.unimib.greenway.data.repository.airQuality;

import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import it.unimib.greenway.data.source.airQuality.AirQualityCallBack;
import it.unimib.greenway.data.source.airQuality.BaseAirQualityLocalDataSource;
import it.unimib.greenway.data.source.airQuality.BaseAirQualityRemoteDataSource;
import it.unimib.greenway.model.AirQuality;
import it.unimib.greenway.model.AirQualityResponse;
import it.unimib.greenway.model.Result;

public class AirQualityRepositoryWithLiveData implements IAirQualityRepositoryWithLiveData, AirQualityCallBack {

    private static final String TAG = AirQualityRepositoryWithLiveData.class.getSimpleName();
    private BaseAirQualityLocalDataSource airQualityLocalDataSource;
    private BaseAirQualityRemoteDataSource airQualityRemoteDataSource;
    private final MutableLiveData<Result> airQualityListLiveData;


    public AirQualityRepositoryWithLiveData(BaseAirQualityLocalDataSource airQualityLocalDataSource,
                                            BaseAirQualityRemoteDataSource airQualityRemoteDataSource) {
        airQualityListLiveData = new MutableLiveData<>();
        this.airQualityLocalDataSource = airQualityLocalDataSource;
        this.airQualityLocalDataSource.setAirQualityCallBack(this);
        this.airQualityRemoteDataSource = airQualityRemoteDataSource;
        this.airQualityRemoteDataSource.setAirQualityCallBack(this);
    }

    @Override
    public MutableLiveData<Result> fetchAllAirQUality(long lastUpdate) {
        if (lastUpdate == 0 || System.currentTimeMillis() - lastUpdate >= 2 * 60 * 60 * 1000){
            airQualityRemoteDataSource.getAirQuality();
        }else{
            airQualityLocalDataSource.getAirQuality();
        }
        return airQualityListLiveData;
    }

    @Override
    public List<AirQuality> getAirQualityList() throws ExecutionException, InterruptedException {
        return airQualityLocalDataSource.getAirQualityList();
    }


    @Override
    public void onSuccessFromLocal(List<AirQuality> response) {
            Result.AirQualityResponseSuccess result = new Result.AirQualityResponseSuccess(new AirQualityResponse(response));
            airQualityListLiveData.postValue(result);
    }

    @Override
    public void onFailureFromLocal(Exception exception) {

    }

    @Override
    public void onSuccessFromRemote(List<AirQuality> Response) {
        airQualityLocalDataSource.insertAirQuality(Response);
    }

    @Override
    public void onFailureFromRemote(Exception exception) {

    }
}
