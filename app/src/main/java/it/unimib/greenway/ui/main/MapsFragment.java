package it.unimib.greenway.ui.main;

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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.VisibleRegion;


import it.unimib.greenway.R;
import it.unimib.greenway.data.repository.user.IUserRepository;
import it.unimib.greenway.ui.UserViewModel;
import it.unimib.greenway.ui.UserViewModelFactory;
import it.unimib.greenway.util.ServiceLocator;


public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap gMap;
    private UserViewModel userViewModel;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;

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
        userViewModel = new ViewModelProvider(
                this,
                new UserViewModelFactory(userRepository)).get(UserViewModel.class);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;

        googleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                // Ottieni la porzione visibile della mappa
                VisibleRegion visibleRegion = googleMap.getProjection().getVisibleRegion();
                LatLng northeast = visibleRegion.latLngBounds.northeast;
                LatLng southwest = visibleRegion.latLngBounds.southwest;

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


}