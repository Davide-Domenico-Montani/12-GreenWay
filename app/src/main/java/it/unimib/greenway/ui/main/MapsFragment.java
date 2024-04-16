package it.unimib.greenway.ui.main;

import static it.unimib.greenway.util.Constants.LAST_UPDATE;
import static it.unimib.greenway.util.Constants.SHARED_PREFERENCES_FILE_NAME;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Button;

import com.google.android.gms.fido.fido2.api.common.AuthenticatorSelectionCriteria;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import it.unimib.greenway.R;
import it.unimib.greenway.data.database.AirQualityDao;
import it.unimib.greenway.data.database.AirQualityDatabase;
import it.unimib.greenway.data.repository.airQuality.IAirQualityRepositoryWithLiveData;
import it.unimib.greenway.data.repository.user.IUserRepository;
import it.unimib.greenway.data.service.AirQualityApiService;
import it.unimib.greenway.model.AirQuality;
import it.unimib.greenway.model.AirQualityApiResponse;
import it.unimib.greenway.ui.UserViewModel;
import it.unimib.greenway.ui.UserViewModelFactory;
import it.unimib.greenway.util.ServiceLocator;
import it.unimib.greenway.util.SharedPreferencesUtil;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap gMap;
    private UserViewModel userViewModel;
    private AirQualityViewModel airQualityViewModel;
    private SharedPreferencesUtil sharedPreferencesUtil;
    Button zoom;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    private FusedLocationProviderClient fusedLocationClient;

    private int i = 0;

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
        IUserRepository userRepository = ServiceLocator.getInstance().
                getUserRepository(requireActivity().getApplication());
        sharedPreferencesUtil = new SharedPreferencesUtil(requireActivity().getApplication());

        userViewModel = new ViewModelProvider(this, new UserViewModelFactory(userRepository)).get(UserViewModel.class);


        IAirQualityRepositoryWithLiveData airQualityRepositoryWithLiveData =
                ServiceLocator.getInstance().getAirQualityRepository(
                        requireActivity().getApplication()
                );


            airQualityViewModel = new ViewModelProvider(this, new AirQualityViewModelFactory(airQualityRepositoryWithLiveData)).get(AirQualityViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;
        //TODO: SIstemare bottone che compare solo quando accetti i permessi
        if(ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        } else {
            gMap.setMyLocationEnabled(true);
        }
        //getAirQualityImage(0, 0, 1);

    }

    private LatLng getLatLngFromTile(int x, int y, float zoom) {
        double lng = x / (double)(1 << (int)zoom) * 360 - 180;
        double n = Math.PI - 2 * Math.PI * y / (double)(1 << (int)zoom);
        double lat = Math.toDegrees(Math.atan(Math.sinh(n)));
        return new LatLng(lat, lng);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String lastUpdate = "0";
        if (sharedPreferencesUtil.readStringData(
                SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE) != null) {
            lastUpdate = sharedPreferencesUtil.readStringData(
                    SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE);
        }
        airQualityViewModel.getAirQuality(Long.parseLong(lastUpdate));
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.id_map);
        mapFragment.getMapAsync(this);

    }

    /*private void getAirQualityImage(int x, int y, int i) {
        Call<ResponseBody> call = airQualityApiService.fetchAirQualityImage("US_AQI", 3, x, y, "AIzaSyBqYE0984H0veT8WIyDLXudEnBhO1RW_MY");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                        if(i <= 64) {
                            try {
                                // Ottieni i byte dall'input stream
                                byte[] imageBytes = response.body().bytes();

                                // Decodifica i byte in un'immagine Bitmap
                                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                                // Carica l'immagine scaricata come bitmap
                                Log.d("Prova", "CaricatA");

                                // Imposta le coordinate per l'overlay
                                LatLng northeast = getLatLngFromTile(x + 1, y, 3);
                                LatLng southwest = getLatLngFromTile(x, y + 1, 3);
                                LatLngBounds bounds = new LatLngBounds(southwest, northeast);

                                // Crea l'overlay
                                GroundOverlayOptions overlayOptions = new GroundOverlayOptions()
                                        .image(BitmapDescriptorFactory.fromBitmap(bitmap))
                                        .positionFromBounds(bounds);
                                overlayOptions.transparency(0.5f);

                                // Aggiungi l'overlay alla mappa
                                gMap.addGroundOverlay(overlayOptions);
                                if(i%8==0) {
                                    getAirQualityImage(0, y + 1, i+1);

                                }else{
                                    getAirQualityImage(x+1 , y, i+1);

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
    }*/





}

