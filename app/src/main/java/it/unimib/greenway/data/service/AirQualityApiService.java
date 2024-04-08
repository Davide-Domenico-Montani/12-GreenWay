package it.unimib.greenway.data.service;

import it.unimib.greenway.model.AirQualityApiResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

    public interface AirQualityApiService {
        @GET("v1/mapTypes/{type}/heatmapTiles/{z}/{x}/{y}")
        Call<ResponseBody> fetchAirQualityImage(@Path("type") String type,
                                                @Path("z") int z,
                                                @Path("x") int x,
                                                @Path("y") int y,
                                                @Query("key") String apiKey);
    }
