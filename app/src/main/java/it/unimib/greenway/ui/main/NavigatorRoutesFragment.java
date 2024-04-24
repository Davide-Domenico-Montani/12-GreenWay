package it.unimib.greenway.ui.main;

import static it.unimib.greenway.util.Constants.DRIVE_CONSTANT;
import static it.unimib.greenway.util.Constants.TRANSIT_CONSTANT;
import static it.unimib.greenway.util.Constants.WALK_CONSTANT;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.ViewModelProvider;


import com.google.android.gms.maps.model.LatLng;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import it.unimib.greenway.R;
import it.unimib.greenway.data.repository.airQuality.IAirQualityRepositoryWithLiveData;
import it.unimib.greenway.data.repository.routes.IRoutesRepositoryWithLiveData;
import it.unimib.greenway.data.repository.user.IUserRepository;
import it.unimib.greenway.adapter.RoutesRecyclerViewAdapter;
import it.unimib.greenway.data.service.RoutesApiService;
import it.unimib.greenway.model.AirQuality;
import it.unimib.greenway.model.Result;
import it.unimib.greenway.model.Polyline;
import it.unimib.greenway.model.Route;
import it.unimib.greenway.model.RoutesApiResponse;
import it.unimib.greenway.model.RoutesResponse;
import it.unimib.greenway.ui.UserViewModel;
import it.unimib.greenway.ui.UserViewModelFactory;
import it.unimib.greenway.util.ServiceLocator;
import it.unimib.greenway.util.SharedPreferencesUtil;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NavigatorRoutesFragment extends Fragment {

    private UserViewModel userViewModel;
    private ProgressBar progressBar;
    private RoutesRecyclerViewAdapter routeRecyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RoutesViewModel routesViewModel;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private List<Route> routeList;
    private List<Route> driveList;
    private List<Route> transitList;
    private List<Route> walkList;
    private TabLayout tabLayout;

    private RecyclerView recyclerViewRoutes;
    private LatLng startLatLng;
    private LatLng destinationLatLng;
    private TabItem tabItemDrive, tabItemTransit, tabItemWalk;



    public static NavigatorRoutesFragment newInstance() {
        NavigatorRoutesFragment fragment = new NavigatorRoutesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        routeList = new ArrayList<>();
        driveList = new ArrayList<>();
        transitList = new ArrayList<>();
        walkList = new ArrayList<>();

        IUserRepository userRepository = ServiceLocator.getInstance().
                getUserRepository(requireActivity().getApplication());
        sharedPreferencesUtil = new SharedPreferencesUtil(requireActivity().getApplication());



        IRoutesRepositoryWithLiveData routesRepositoryWithLiveData =
                ServiceLocator.getInstance().getRoutesRepository(
                        requireActivity().getApplication()
                );

        routesViewModel = new ViewModelProvider(this, new RoutesViewModelFactory(routesRepositoryWithLiveData)).get(RoutesViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Bundle bundle = getArguments();
        if (bundle != null) {
             startLatLng = bundle.getParcelable("startLatLng");
             destinationLatLng = bundle.getParcelable("destinationLatLng");
        }
        return inflater.inflate(R.layout.fragment_navigator_routes, container, false);
    }


    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.routesProgressBar);
        recyclerViewRoutes = view.findViewById(R.id.recyclerViewRoutes);
        tabLayout = view.findViewById(R.id.tabLayout);
        tabItemDrive = view.findViewById(R.id.tabItemDrive);
        tabItemTransit = view.findViewById(R.id.tabItemTransit);
        tabItemWalk = view.findViewById(R.id.tabItemWalk);
        progressBar.setVisibility(View.VISIBLE);
        TabLayout.Tab tab0 = tabLayout.getTabAt(0);
        TabLayout.Tab tab1 = tabLayout.getTabAt(1);
        TabLayout.Tab tab2 = tabLayout.getTabAt(2);



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        updateRecyclerView(driveList);
                        break;
                    case 1:
                        updateRecyclerView(transitList);
                        break;
                    case 2:
                        updateRecyclerView(walkList);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        layoutManager =
            new LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false);


        routeRecyclerViewAdapter = new RoutesRecyclerViewAdapter(routeList,
            requireActivity().getApplication(),
                new RoutesRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onRouteItemClick(Route route) {
                        Snackbar.make(recyclerViewRoutes, route.getDistanceMeters(), Snackbar.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("route", route);
                    }
                });

        recyclerViewRoutes.setLayoutManager(layoutManager);
        recyclerViewRoutes.setAdapter(routeRecyclerViewAdapter);
        routesViewModel.getRoutes(startLatLng.latitude,
                startLatLng.longitude,
                destinationLatLng.latitude,
                destinationLatLng.longitude).observe(getViewLifecycleOwner(),
                    result -> {
                        if (result.isSuccessRoutes()) {
                            this.routeList.clear();
                            this.routeList.addAll(((Result.RouteResponseSuccess) result).getData().getRoutes());
                            progressBar.setVisibility(View.GONE);
                            divideList(routeList);
                            if(driveList.size() != 0) {
                                tab0.setText(convertSecond(Integer.parseInt(String.valueOf(driveList.get(0).getDuration().substring(0, driveList.get(0).getDuration().length() - 1)))));
                            }
                            if(transitList.size() != 0) {
                                tab1.setText(convertSecond(Integer.parseInt(String.valueOf(transitList.get(0).getDuration().substring(0, transitList.get(0).getDuration().length() - 1)))));
                            }
                            if(walkList.size() != 0) {
                                tab2.setText(convertSecond(Integer.parseInt(String.valueOf(walkList.get(0).getDuration().substring(0, walkList.get(0).getDuration().length() - 1)))));
                            }
                            switch (tabLayout.getSelectedTabPosition()) {
                                case 0:
                                    updateRecyclerView(driveList);
                                    break;
                                case 1:
                                    updateRecyclerView(transitList);
                                    break;
                                case 2:
                                    updateRecyclerView(walkList);
                                    break;
                            }

                        }
                    });

    }

    public void divideList (List <Route> routeList) {
        for (Route route : routeList) {
            if (route.getTravelMode().equals(DRIVE_CONSTANT)) {
                driveList.add(route);
            } else if (route.getTravelMode().equals(TRANSIT_CONSTANT)) {
                transitList.add(route);
            } else if (route.getTravelMode().equals(WALK_CONSTANT)) {
                walkList.add(route);
            }
        }
    }

    private void updateRecyclerView (List<Route> routeList) {
        if (routeRecyclerViewAdapter != null) {
            routeRecyclerViewAdapter.clear();
            routeRecyclerViewAdapter.addAll(routeList);
            routeRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    private String convertSecond(int totalSeconds){
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;

        if(hours != 0)
            return hours +"h " + minutes + "m";
        else
            return minutes + "m " + seconds + "s";
    }

}