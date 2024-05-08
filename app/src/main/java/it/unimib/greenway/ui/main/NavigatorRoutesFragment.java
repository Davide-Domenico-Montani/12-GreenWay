package it.unimib.greenway.ui.main;

import static it.unimib.greenway.util.Constants.DRIVE_CONSTANT;
import static it.unimib.greenway.util.Constants.ERROR_RETRIEVING_ROUTES;
import static it.unimib.greenway.util.Constants.ERROR_RETRIEVING_USER_INFO;
import static it.unimib.greenway.util.Constants.NEW_PASSWORD_ERROR;
import static it.unimib.greenway.util.Constants.OLD_PASSWORD_ERROR;
import static it.unimib.greenway.util.Constants.PASSWORD_ERROR_GOOGLE;
import static it.unimib.greenway.util.Constants.TRANSIT_CONSTANT;
import static it.unimib.greenway.util.Constants.WALK_CONSTANT;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.ViewModelProvider;


import com.google.android.gms.maps.model.LatLng;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import it.unimib.greenway.R;
import it.unimib.greenway.adapter.RecylclerViewClickListener;
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
import it.unimib.greenway.model.User;
import it.unimib.greenway.ui.UserViewModel;
import it.unimib.greenway.ui.UserViewModelFactory;
import it.unimib.greenway.util.ConverterUtil;
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


public class NavigatorRoutesFragment extends Fragment implements RecylclerViewClickListener {

    private UserViewModel userViewModel;
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
    private ConverterUtil converterUtil;
    private RecylclerViewClickListener listener;
    private FragmentManager fragmentManager;



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
        listener= this;

        fragmentManager = requireActivity().getSupportFragmentManager();
        IUserRepository userRepository = ServiceLocator.getInstance().
                getUserRepository(requireActivity().getApplication());
        userViewModel = new ViewModelProvider(
                this,
                new UserViewModelFactory(userRepository)).get(UserViewModel.class);

        sharedPreferencesUtil = new SharedPreferencesUtil(requireActivity().getApplication());

        converterUtil = new ConverterUtil();

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
        recyclerViewRoutes = view.findViewById(R.id.recyclerViewRoutes);
        tabLayout = view.findViewById(R.id.tabLayout);

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
                },listener);
       recyclerViewRoutes.setLayoutManager(layoutManager);
       recyclerViewRoutes.setAdapter(routeRecyclerViewAdapter);

        userViewModel.getUserDataMutableLiveData(userViewModel.getLoggedUser().getUserId()).observe(
                getViewLifecycleOwner(), result -> {
                    if (result.isSuccessUser()) {
                        User user = ((Result.UserResponseSuccess) result).getData();

                        routesViewModel.getRoutes(startLatLng.latitude,
                                startLatLng.longitude,
                                destinationLatLng.latitude,
                                destinationLatLng.longitude, user.getCo2Car()).observe(getViewLifecycleOwner(),
                                    result2 -> {
                                        if (result2.isSuccessRoutes()) {
                                            this.routeList.clear();
                                            this.routeList.addAll(((Result.RouteResponseSuccess) result2).getData().getRoutes());
                                             divideList(routeList);
                                                if(driveList.size() != 0) {
                                                    tab0.setText(converterUtil.convertSecond(Integer.parseInt(String.valueOf(driveList.get(0).getDuration().substring(0, driveList.get(0).getDuration().length() - 1)))));
                                                }
                                                if(transitList.size() != 0) {
                                                    tab1.setText(converterUtil.convertSecond(Integer.parseInt(String.valueOf(transitList.get(0).getDuration().substring(0, transitList.get(0).getDuration().length() - 1)))));
                                                }
                                                if(walkList.size() != 0) {
                                                    tab2.setText(converterUtil.convertSecond(Integer.parseInt(String.valueOf(walkList.get(0).getDuration().substring(0, walkList.get(0).getDuration().length() - 1)))));
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

                                            }else{
                                                Snackbar.make(requireActivity().findViewById(android.R.id.content),
                                                        getErrorMessage(((Result.Error) result2).getMessage()),
                                                        Snackbar.LENGTH_SHORT).show();
                                            }
                                    });
                    } else {
                        Snackbar.make(requireActivity().findViewById(android.R.id.content),
                            getErrorMessage(((Result.Error) result).getMessage()),
                            Snackbar.LENGTH_SHORT).show();
                    }
                });

    }

    public void divideList (List <Route> routeList) {
        for (Route route : routeList) {
            if (route.getTravelMode().equals(DRIVE_CONSTANT)) {
                driveList.add(route);
                reorderList(driveList);

            } else if (route.getTravelMode().equals(TRANSIT_CONSTANT)) {
                transitList.add(route);
                reorderList(transitList);
            } else if (route.getTravelMode().equals(WALK_CONSTANT)) {
                walkList.add(route);
            }
        }
    }
    public void reorderList(List<Route> routeList) {
        Comparator<Route> comparator = new Comparator<Route>() {
            @Override
            public int compare(Route route1, Route route2) {
                return Double.compare(route1.getCo2(), route2.getCo2());
            }
        };

        //Ordina lista per campo co2
        Collections.sort(routeList, comparator);
    }
    private void updateRecyclerView (List<Route> routeList) {
        if (routeRecyclerViewAdapter != null) {
            routeRecyclerViewAdapter.clear();
            routeRecyclerViewAdapter.addAll(routeList);
            routeRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(Route route) {
        double co2Saved;
        double kmTravel = (double) route.getDistanceMeters() / 1000;
        String transportType = route.getTravelMode();

        if(transportType.equals(DRIVE_CONSTANT) || transportType.equals(TRANSIT_CONSTANT)) {
            co2Saved = driveList.get(driveList.size()-1).getCo2() - route.getCo2();
        }else{
            co2Saved = driveList.get(driveList.size()-1).getCo2();
        }
        userViewModel.updateCo2SavedMutableLiveData(userViewModel.getLoggedUser().getUserId(), transportType, co2Saved, kmTravel);
        Snackbar.make(recyclerViewRoutes, "Hai risparmiato: " + co2Saved + " kg!", Snackbar.LENGTH_SHORT).show();
        fragmentManager.popBackStack();
    }

    private String getErrorMessage(String errorType) {
        switch (errorType) {
            case ERROR_RETRIEVING_USER_INFO:
                return requireActivity().getString(R.string.error_retrieving_user_info);
            case ERROR_RETRIEVING_ROUTES:
                return requireActivity().getString(R.string.error_retrieving_routes);

            default:
                return requireActivity().getString(R.string.unexpected_error);
        }
    }
}