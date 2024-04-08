package it.unimib.greenway.ui.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.VisibleRegion;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import it.unimib.greenway.R;
import it.unimib.greenway.data.repository.user.IUserRepository;
import it.unimib.greenway.data.service.AirQualityApiService;
import it.unimib.greenway.model.AirQualityApiResponse;
import it.unimib.greenway.ui.UserViewModel;
import it.unimib.greenway.ui.UserViewModelFactory;
import it.unimib.greenway.util.ServiceLocator;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap gMap;
    private UserViewModel userViewModel;
    private Retrofit retrofit;
    private AirQualityApiService airQualityApiService;


    private static final int PERMISSION_REQUEST_CODE = 1;

    public MapsFragment() {
        // Required empty public constructor
    }

    public static MapsFragment newInstance() {
        MapsFragment fragment = new MapsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IUserRepository userRepository = ServiceLocator.getInstance().getUserRepository(requireActivity().getApplication());
        userViewModel = new ViewModelProvider(this, new UserViewModelFactory(userRepository)).get(UserViewModel.class);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://airquality.googleapis.com/")
                .build();
        airQualityApiService = retrofit.create(AirQualityApiService.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;
        getAirQualityImage();
        String mapType = "US_AQI";
        String apiKey = "AIzaSyBqYE0984H0veT8WIyDLXudEnBhO1RW_MY";
        int zoomLevel = 2;
        int xCoord = 0;
        int yCoord = 1;
        Log.d("MapBounds", "Coordinate X: " + xCoord);
        Log.d("MapBounds", "Coordinate Y: " + yCoord);

        googleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                // Ottieni la porzione visibile della mappa
                VisibleRegion visibleRegion = googleMap.getProjection().getVisibleRegion();
                LatLng northeast = visibleRegion.latLngBounds.northeast;
                LatLng southwest = visibleRegion.latLngBounds.southwest;

                // Ottieni il livello di zoom attuale della mappa
                float zoomLevel = googleMap.getCameraPosition().zoom;

                // Stampalo in console di log
                Log.d("MapZoom", "Zoom Level: " + zoomLevel);
                // Stampa le coordinate
                Log.d("MapBounds", "Northeast Lat: " + northeast.latitude);
                Log.d("MapBounds", "Northeast Lng: " + northeast.longitude);
                Log.d("MapBounds", "Southwest Lat: " + southwest.latitude);
                Log.d("MapBounds", "Southwest Lng: " + southwest.longitude);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.id_map);
        mapFragment.getMapAsync(this);
    }

    private void getAirQualityImage() {
        Call<ResponseBody> call = airQualityApiService.fetchAirQualityImage("US_AQI", 2, 0, 1, "AIzaSyBqYE0984H0veT8WIyDLXudEnBhO1RW_MY");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        File imageFile = new File(getActivity().getExternalFilesDir(null), "air_quality_image.png");
                        InputStream inputStream = null;
                        OutputStream outputStream = null;

                        try {
                            byte[] fileReader = new byte[4096];

                            long fileSize = response.body().contentLength();
                            long fileSizeDownloaded = 0;

                            inputStream = response.body().byteStream();
                            outputStream = new FileOutputStream(imageFile);

                            while (true) {
                                int read = inputStream.read(fileReader);

                                if (read == -1) {
                                    break;
                                }

                                outputStream.write(fileReader, 0, read);
                                fileSizeDownloaded += read;

                                Log.d("Prova", "file download: " + fileSizeDownloaded + " of " + fileSize);
                            }

                            outputStream.flush();

                            Log.d("Prova", "file saved: " + imageFile.getAbsolutePath());
                        } catch (IOException e) {
                            Log.e("Prova", "error in saving file", e);
                        } finally {
                            if (inputStream != null) {
                                inputStream.close();
                            }

                            if (outputStream != null) {
                                outputStream.close();
                            }
                        }
                    } catch (IOException e) {
                        Log.e("Prova", "error in saving file", e);
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

