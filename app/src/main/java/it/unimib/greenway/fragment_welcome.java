package it.unimib.greenway;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.carousel.CarouselLayoutManager;

import java.util.ArrayList;
import java.util.List;

import it.unimib.greenway.adapter.CarouselAdapter;


public class fragment_welcome extends Fragment {

    private List<CarouselItem> carouselItems;
    private RecyclerView carouselRecyclerView;
    private CarouselAdapter carouselAdapter;
    CarouselLayoutManager carouselLayoutManager;


    public fragment_welcome() {
    }

    public static fragment_welcome newInstance(String param1, String param2) {
        fragment_welcome fragment = new fragment_welcome();
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



        // Inizializza il RecyclerView
        carouselRecyclerView = rootView.findViewById(R.id.carousel_recycler_view);
        carouselAdapter = new CarouselAdapter(carouselItems);
        carouselRecyclerView.setAdapter(carouselAdapter);
        carouselLayoutManager = new CarouselLayoutManager();
        carouselRecyclerView.setLayoutManager(carouselLayoutManager);

        return rootView;
    }


}