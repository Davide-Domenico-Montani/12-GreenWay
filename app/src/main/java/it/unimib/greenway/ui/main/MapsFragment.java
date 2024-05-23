package it.unimib.greenway.ui.main;

import static it.unimib.greenway.util.Constants.LAST_UPDATE;
import static it.unimib.greenway.util.Constants.SHARED_PREFERENCES_FILE_NAME;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import it.unimib.greenway.R;
import it.unimib.greenway.data.repository.airQuality.IAirQualityRepositoryWithLiveData;
import it.unimib.greenway.data.repository.user.IUserRepository;
import it.unimib.greenway.model.AirQuality;
import it.unimib.greenway.model.Route;
import it.unimib.greenway.ui.UserViewModel;
import it.unimib.greenway.ui.UserViewModelFactory;
import it.unimib.greenway.util.ServiceLocator;
import it.unimib.greenway.util.SharedPreferencesUtil;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap gMap;
    private UserViewModel userViewModel;
    private AirQualityViewModel airQualityViewModel;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private Button zoom;
    private ImageButton backButton;

    private FusedLocationProviderClient fusedLocationClient;

    private int i = 0;
    private Route route;
    private List<LatLng> decodedPoint;
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
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        backButton = requireActivity().findViewById(R.id.backButton);
        backButton.setVisibility(View.INVISIBLE);

        Bundle bundle = getArguments();
        if(bundle != null) {
            route = bundle.getParcelable("polyline");
        }
        // Inflate the layout for this fragment
        return view;

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;

        //TODO: Togliere commento quando si riattiva chiamata per mappa
        try {
            List<AirQuality> listAirQuality = airQualityViewModel.getAirQualityList();
            Log.d("prova", String.valueOf(listAirQuality.size()));
            if(listAirQuality.size() == 64){
                printimage(listAirQuality);
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(route != null){

                List<LatLng> decodedPoint = PolyUtil.decode(route.getPolyline().getEncodedPolyline());
                Log.d("prova", decodedPoint.toString());
                PolylineOptions polylineOptions = new PolylineOptions()
                        .addAll(decodedPoint)
                        .width(12)
                        .color(Color.BLUE);

                gMap.addPolyline(polylineOptions);

            float zoomLevel = 10; // Imposta il livello di zoom desiderato, un valore tra 1 e 20

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(route.getStart()) // Imposta le coordinate su cui zoomare
                    .zoom(zoomLevel) // Imposta il livello di zoom
                    .build();

            gMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            backButton.setVisibility(View.VISIBLE);


        }else {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
            } else {
                gMap.setMyLocationEnabled(true);
            }

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                // Get the user's current location
                                LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());

                                // Zoom the map to the user's location
                                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
                            }
                        }
                    });
        }
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
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.id_map);
        mapFragment.getMapAsync(this);
        String lastUpdate = "0";
        if (sharedPreferencesUtil.readStringData(
                SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE) != null) {
            lastUpdate = sharedPreferencesUtil.readStringData(
                    SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE);
        }
         airQualityViewModel.getAirQuality(Long.parseLong(lastUpdate), view);


    }
    private void printimage(List <AirQuality> listAirQuality){
        for(int i = 1; i <= listAirQuality.size(); i++){
            AirQuality airQuality = listAirQuality.get(i-1);
            byte[] imageBytes = airQuality.getImage();
            int x = airQuality.getX();
            int y = airQuality.getY();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
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

        }
    }


}

