package it.unimib.greenway.data.service;

import it.unimib.greenway.model.AirQualityApiResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

    public interface AirQualityApiService {
        @GET("v1/mapTypes/US_AQI/heatmapTiles/2/0/1?")
        Call<AirQualityApiResponse> fetchAirQualityImage(@Query("key=") String apiKey);
    }
