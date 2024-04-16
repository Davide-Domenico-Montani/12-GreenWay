package it.unimib.greenway.data.service;

import it.unimib.greenway.model.AirQualityApiResponse;
import it.unimib.greenway.model.RoutesResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RoutesApiService {
        @POST("directions/v2:computeRoutes")
        Call<RoutesResponse> computeRoutes(
                @Header("Content-Type") String contentType,
                @Header("X-Goog-Api-Key") String apiKey,
                @Header("X-Goog-FieldMask") String fieldMask,
                @Body DirectionsRequest request
        );
}