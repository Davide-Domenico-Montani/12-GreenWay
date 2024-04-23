package it.unimib.greenway.ui.main;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.search.SearchBar;

import java.util.Arrays;

import it.unimib.greenway.R;
import it.unimib.greenway.model.Result;
import it.unimib.greenway.model.User;


public class NavigatorFragment extends Fragment {

    Button btnNavRoutes;

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
        Places.initialize(requireContext(), "AIzaSyBqYE0984H0veT8WIyDLXudEnBhO1RW_MY");
        PlacesClient placesClient = Places.createClient(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_navigator, container, false);

        initAutocompleteFragment(R.id.autocomplete_fragment);

        initAutocompleteFragment(R.id.autocomplete_fragment2);

        // Inflate the layout for this fragment
        return rootView;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnNavRoutes = view.findViewById(R.id.buttonNavRoutes);

        btnNavRoutes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigatorFragment_to_navigatorRoutesFragment);
            }
        });

    }

    private void initAutocompleteFragment(int fragmentId) {
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(fragmentId);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
                Log.i("Ciao", "Place: " + place.getName() + ", " + place.getId());
            }

            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Log.i("Ciao", "An error occurred: " + status);
            }
        });
    }




}