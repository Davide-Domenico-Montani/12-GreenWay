package it.unimib.greenway.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.carousel.CarouselLayoutManager;

import java.util.ArrayList;
import java.util.List;

import it.unimib.greenway.CarouselItem;
import it.unimib.greenway.R;
import it.unimib.greenway.adapter.CarouselAdapter;


public class WelcomeFragment extends Fragment {

    private List<CarouselItem> carouselItems;
    private RecyclerView carouselRecyclerView;
    private CarouselAdapter carouselAdapter;
    CarouselLayoutManager carouselLayoutManager;


    Button loginButton, signInButton;

    public WelcomeFragment() {
    }

    public static WelcomeFragment newInstance(String param1, String param2) {
        WelcomeFragment fragment = new WelcomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_welcome, container, false);

        CarouselItem item1 = new CarouselItem(R.drawable.image1, getString(R.string.string1Carosel));
        CarouselItem item2 = new CarouselItem(R.drawable.image2, getString(R.string.string2Carosel));
        CarouselItem item3 = new CarouselItem(R.drawable.image3, getString(R.string.string3Carosel));
        CarouselItem item4 = new CarouselItem(R.drawable.image4, getString(R.string.string4Carosel));

        carouselItems = new ArrayList<>();
        carouselItems.add(item1);
        carouselItems.add(item2);
        carouselItems.add(item3);
        carouselItems.add(item4);


        carouselRecyclerView = rootView.findViewById(R.id.carousel_recycler_view);
        carouselAdapter = new CarouselAdapter(carouselItems);
        carouselRecyclerView.setAdapter(carouselAdapter);
        carouselLayoutManager = new CarouselLayoutManager();
        carouselRecyclerView.setLayoutManager(carouselLayoutManager);

        return rootView;


    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        loginButton = view.findViewById(R.id.button_login);
        signInButton = view.findViewById(R.id.button_signin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_fragment_welcome_to_login);
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_fragment_welcome_to_sign_in);
            }
        });
    }




}