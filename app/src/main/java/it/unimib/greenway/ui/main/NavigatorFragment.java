package it.unimib.greenway.ui.main;

import static android.content.ContentValues.TAG;
import static it.unimib.greenway.BuildConfig.MAPS_API_KEY;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.search.SearchBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;

import it.unimib.greenway.R;
import it.unimib.greenway.model.Result;
import it.unimib.greenway.model.User;


public class NavigatorFragment extends Fragment {

    LatLng startLatLng;
    LatLng destinationLatLng;
    Button navigateButton;
    public NavigatorFragment() {
        // Required empty public constructor
    }

    public static NavigatorFragment newInstance() {
        NavigatorFragment fragment = new NavigatorFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Places.initialize(requireContext(), MAPS_API_KEY);
        PlacesClient placesClient = Places.createClient(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_navigator, container, false);
        navigateButton = rootView.findViewById(R.id.buttonFind);
        initAutocompleteFragment(R.id.autocomplete_fragment);

        initAutocompleteFragment(R.id.autocomplete_fragment2);


        navigateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startLatLng == null && destinationLatLng != null) {
                    Snackbar.make(rootView, getString(R.string.insertStart), Snackbar.LENGTH_SHORT).show();
                } else if (startLatLng != null && destinationLatLng == null) {
                    Snackbar.make(rootView, getString(R.string.insertDestination), Snackbar.LENGTH_SHORT).show();
                } else if (startLatLng == null && destinationLatLng == null) {
                    Snackbar.make(rootView, getString(R.string.insertStartAndDestination), Snackbar.LENGTH_SHORT).show();
                } else {
                    //TODO: Gestione per mandare LatLng a fragment per navigazione
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("startLatLng", startLatLng);
                    bundle.putParcelable("destinationLatLng", destinationLatLng);
                    Log.d("ciao", startLatLng.toString());
                    Log.d("ciao", destinationLatLng.toString());
                    Navigation.findNavController(rootView).navigate(R.id.action_navigatorFragment_to_navigatorRoutesFragment, bundle);
                }
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }

    private void initAutocompleteFragment(int fragmentId) {
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(fragmentId);
        autocompleteFragment.setActivityMode(AutocompleteActivityMode.FULLSCREEN);
        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        if(fragmentId == R.id.autocomplete_fragment){
            autocompleteFragment.setHint(getString(R.string.start));
        } else {
            autocompleteFragment.setHint(getString(R.string.destination));
        }

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                if(fragmentId == R.id.autocomplete_fragment){
                    Log.d("ciao", place.getId());
                    startLatLng = place.getLatLng();
                } else {
                    Log.d("ciao", place.toString());
                    destinationLatLng = place.getLatLng();
                }
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }




}