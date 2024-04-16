package it.unimib.greenway.data.source.airQuality;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;

import it.unimib.greenway.data.service.AirQualityApiService;
import it.unimib.greenway.model.AirQuality;
import it.unimib.greenway.util.ServiceLocator;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AirQualityRemoteDataSource extends BaseAirQualityRemoteDataSource {

    private final AirQualityApiService airQualityApiService;
    private int x = 0;
    private int y = 0;
    private int i = 1;
    public AirQualityRemoteDataSource() {
        this.airQualityApiService = ServiceLocator.getInstance().getAirQualityApiService();
    }

    @Override
    public void getAirQuality() {
        List<AirQuality> airQualities = new ArrayList<>();
        Call<ResponseBody> call = airQualityApiService.fetchAirQualityImage("US_AQI", 3, x, y, "AIzaSyBqYE0984H0veT8WIyDLXudEnBhO1RW_MY");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if(i <= 64) {
                        try {
                            // Ottieni i byte dall'input stream
                            byte[] imageBytes = response.body().bytes();
                            airQualities.add(new AirQuality(imageBytes, x, y));
                            airQualityCallBack.onSuccessFromRemote(airQualities);
                            // Carica l'immagine scaricata come bitmap
                            Log.d("Prova", "CaricatA");


                            if(i%8==0) {
                                x = 0;
                                y = y+1;
                                i = i+1;
                                getAirQuality();

                            }else{
                                x = x+1;
                                i = i+1;
                                getAirQuality();

                            }
                        } catch (Exception e) {
                            Log.e("Prova", "Errore nel caricare l'immagine come overlay", e);
                        }
                    }

                } else {
                    // handle request errors
                    Log.d("Prova2","no" );

                    Log.d("Prova2", "Codice di stato HTTP: " + response.code());
                    Log.d("Prova2", "Messaggio di errore: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // handle network errors
                Log.d("Prova","no", t );
            }
        });

    }
}
