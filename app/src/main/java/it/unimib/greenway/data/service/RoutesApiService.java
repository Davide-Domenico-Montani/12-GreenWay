package it.unimib.greenway.data.service;

import it.unimib.greenway.model.RoutesApiResponse;
import it.unimib.greenway.model.RoutesResponse;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RoutesApiService {
    @POST("/directions/v2:computeRoutes")
    Call<RoutesApiResponse> createRoute(@Body RequestBody requestBody,
                                                 @Header("X-Goog-FieldMask") String fieldMask,
                                                 @Header("X-Goog-Api-Key") String apiKey);
}
