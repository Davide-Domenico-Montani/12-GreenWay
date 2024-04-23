package it.unimib.greenway.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.ViewModelProvider;


import com.google.android.gms.maps.model.LatLng;

import com.google.android.material.snackbar.Snackbar;

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

    private RoutesRecyclerViewAdapter routeRecyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RoutesViewModel routesViewModel;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private List<Route> routeList;
    private RecyclerView recyclerViewRoutes;



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
        if (getArguments() != null) {

        }

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



        return inflater.inflate(R.layout.fragment_navigator_routes, container, false);
    }


    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewRoutes = view.findViewById(R.id.recyclerViewRoutes);

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

        routesViewModel.getRoutes(45.498830,
                9.196702,
                45.506355,
                9.222862).observe(getViewLifecycleOwner(),
                result -> {
                    if(result.isSuccessRoutes()){
                        this.routeList.addAll(((Result.RouteResponseSuccess) result).getData().getRoutes());
                        routeRecyclerViewAdapter.notifyDataSetChanged();
                    }
                });

    }


    }