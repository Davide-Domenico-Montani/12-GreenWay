package it.unimib.greenway.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


import java.util.List;

import it.unimib.greenway.R;
import it.unimib.greenway.data.service.RoutesApiService;
import it.unimib.greenway.model.AirQuality;
import it.unimib.greenway.model.Route;
import it.unimib.greenway.model.RoutesApiResponse;
import it.unimib.greenway.model.RoutesResponse;
import it.unimib.greenway.util.ServiceLocator;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NavigatorRoutesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavigatorRoutesFragment extends Fragment {


    public static NavigatorRoutesFragment newInstance() {
        NavigatorRoutesFragment fragment = new NavigatorRoutesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://routes.googleapis.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RoutesApiService routesApiService = retrofit.create(RoutesApiService.class);

        String body = "{\n" +
                "  \"origin\":{\n" +
                "    \"location\":{\n" +
                "      \"latLng\":{\n" +
                "        \"latitude\": 45.498830,\n" +
                "        \"longitude\": 9.196702\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"destination\":{\n" +
                "    \"location\":{\n" +
                "      \"latLng\":{\n" +
                "        \"latitude\": 45.506355,\n" +
                "        \"longitude\": 9.222862\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"travelMode\": \"DRIVE\",\n" +
                "  \"routingPreference\": \"TRAFFIC_AWARE\",\n" +
                "  \"departureTime\": \"2024-10-23T15:01:23.045123456Z\",\n" +
                "  \"computeAlternativeRoutes\": false,\n" +
                "  \"routeModifiers\": {\n" +
                "    \"avoidTolls\": false,\n" +
                "    \"avoidHighways\": false,\n" +
                "    \"avoidFerries\": false\n" +
                "  },\n" +
                "  \"languageCode\": \"en-US\",\n" +
                "  \"units\": \"IMPERIAL\"\n" +
                "}";

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), body);

        Call<RoutesApiResponse> call = routesApiService.createRoute(requestBody, "routes.duration,routes.distanceMeters,routes.polyline.encodedPolyline", "AIzaSyBqYE0984H0veT8WIyDLXudEnBhO1RW_MY");
        call.enqueue(new Callback<RoutesApiResponse>() {
            @Override
            public void onResponse(Call<RoutesApiResponse> call, Response<RoutesApiResponse> response) {
                if (response.isSuccessful()) {

                    RoutesApiResponse route = response.body();
                    List<Route> routeList = route.getRoutes();

                    Log.d("routeProva", "" + routeList.get(0).toString());
                } else {
                    // handle request errors
                    Log.d("routeProva","errore" );

                }
            }

            @Override
            public void onFailure(Call<RoutesApiResponse> call, Throwable t) {
                // handle network errors
                Log.d("routeProva","onFailure", t );
            }
        });
        return inflater.inflate(R.layout.fragment_navigator_routes, container, false);
    }


}